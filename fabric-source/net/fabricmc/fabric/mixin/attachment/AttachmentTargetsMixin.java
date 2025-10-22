package net.fabricmc.fabric.mixin.attachment;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.impl.attachment.AttachmentSerializingImpl;
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
import net.fabricmc.fabric.impl.attachment.AttachmentTypeImpl;
import net.fabricmc.fabric.impl.attachment.sync.AttachmentChange;
import net.fabricmc.fabric.impl.attachment.sync.s2c.AttachmentSyncPayloadS2C;

@Mixin({BlockEntity.class, Entity.class, World.class, Chunk.class})
abstract class AttachmentTargetsMixin implements AttachmentTargetImpl {
	@Unique
	@Nullable
	private IdentityHashMap<AttachmentType<?>, Object> dataAttachments = null;
	@Unique
	@Nullable
	private IdentityHashMap<AttachmentType<?>, AttachmentChange> syncedAttachments = null;
	@Unique
	@Nullable
	private IdentityHashMap<AttachmentType<?>, Event<OnAttachedSet<?>>> attachedChangedListeners = null;

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getAttached(AttachmentType<T> type) {
		return dataAttachments == null ? null : (T) dataAttachments.get(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T setAttached(AttachmentType<T> type, @Nullable T value) {
		T oldValue;

		if (value == null) {
			oldValue = dataAttachments == null ? null : (T) dataAttachments.remove(type);
		} else {
			if (dataAttachments == null) {
				dataAttachments = new IdentityHashMap<>();
			}

			oldValue = (T) dataAttachments.put(type, value);
		}

		if (attachedChangedListeners != null) {
			Event<OnAttachedSet<T>> event = (Event<OnAttachedSet<T>>) (Event<?>) attachedChangedListeners.get(type);

			if (event != null) {
				event.invoker().onAttachedSet(oldValue, value);
			}
		}

		if (!Objects.equals(oldValue, value)) {
			this.fabric_markChanged(type);

			if (this.fabric_shouldTryToSync() && type.isSynced()) {
				AttachmentChange change = AttachmentChange.create(fabric_getSyncTargetInfo(), type, value, fabric_getDynamicRegistryManager());
				acknowledgeSyncedEntry(type, change);
				this.fabric_syncChange(type, new AttachmentSyncPayloadS2C(List.of(change)));
			}
		}

		return oldValue;
	}

	@Override
	public boolean hasAttached(AttachmentType<?> type) {
		return dataAttachments != null && dataAttachments.containsKey(type);
	}

	@Override
	public <A> Event<OnAttachedSet<A>> onAttachedSet(AttachmentType<A> type) {
		if (attachedChangedListeners == null) {
			attachedChangedListeners = new IdentityHashMap<>();
		}

		return (Event<OnAttachedSet<A>>) (Event<?>) attachedChangedListeners.computeIfAbsent(type, t -> {
			return (Event<OnAttachedSet<?>>) (Event<?>) EventFactory.createArrayBacked(OnAttachedSet.class, (Function<OnAttachedSet<A>[], OnAttachedSet<A>>) listeners -> (oldValue, newValue) -> {
				for (OnAttachedSet<A> listener : listeners) {
					listener.onAttachedSet(oldValue, newValue);
				}
			});
		});
	}

	@Override
	public void fabric_writeAttachmentsToNbt(WriteView view) {
		AttachmentSerializingImpl.serializeAttachmentData(view, dataAttachments);
	}

	@Override
	public void fabric_readAttachmentsFromNbt(ReadView view) {
		// Note on player targets: no syncing can happen here as the networkHandler is still null
		// Instead it is done on player join (see AttachmentSync)
		IdentityHashMap<AttachmentType<?>, Object> fromNbt = AttachmentSerializingImpl.deserializeAttachmentData(view);

		// If the NBT is devoid of data attachments, treat it as a no-op, rather than wiping them out.
		// Any changes to data attachments (including removals) post-load are done independently of this
		// code path, so we don't need to blindly overwrite it every time if Vanilla MC sends updates
		// (i.e. block entity updates) sans data attachments. See https://github.com/FabricMC/fabric/issues/4638
		if (fromNbt == null) {
			return;
		}

		this.dataAttachments = fromNbt;

		if (this.fabric_shouldTryToSync() && this.dataAttachments != null) {
			this.dataAttachments.forEach((type, value) -> {
				if (type.isSynced()) {
					acknowledgeSynced(type, value, view.getRegistries());
				}
			});
		}
	}

	@Override
	public boolean fabric_hasPersistentAttachments() {
		return AttachmentSerializingImpl.hasPersistentAttachments(dataAttachments);
	}

	@Override
	public Map<AttachmentType<?>, ?> fabric_getAttachments() {
		return dataAttachments;
	}

	@Unique
	private void acknowledgeSynced(AttachmentType<?> type, Object value, RegistryWrapper.WrapperLookup wrapperLookup) {
		DynamicRegistryManager dynamicRegistryManager = (wrapperLookup instanceof DynamicRegistryManager drm) ? drm : fabric_getDynamicRegistryManager();
		acknowledgeSyncedEntry(type, AttachmentChange.create(fabric_getSyncTargetInfo(), type, value, dynamicRegistryManager));
	}

	@Unique
	private void acknowledgeSyncedEntry(AttachmentType<?> type, @Nullable AttachmentChange change) {
		if (change == null) {
			if (syncedAttachments == null) {
				return;
			}

			syncedAttachments.remove(type);
		} else {
			if (syncedAttachments == null) {
				syncedAttachments = new IdentityHashMap<>();
			}

			syncedAttachments.put(type, change);
		}
	}

	@Override
	public void fabric_computeInitialSyncChanges(ServerPlayerEntity player, Consumer<AttachmentChange> changeOutput) {
		if (syncedAttachments == null) {
			return;
		}

		for (Map.Entry<AttachmentType<?>, AttachmentChange> entry : syncedAttachments.entrySet()) {
			if (((AttachmentTypeImpl<?>) entry.getKey()).syncPredicate().test(this, player)) {
				changeOutput.accept(entry.getValue());
			}
		}
	}
}
