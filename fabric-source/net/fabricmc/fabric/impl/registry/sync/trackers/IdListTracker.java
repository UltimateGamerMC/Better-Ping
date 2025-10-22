package net.fabricmc.fabric.impl.registry.sync.trackers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.IdList;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.fabricmc.fabric.impl.registry.sync.RemovableIdList;

public class IdListTracker<V, OV> implements RegistryEntryAddedCallback<V>, RegistryIdRemapCallback<V> {
	private final String name;
	private final IdList<OV> mappers;
	private Map<Identifier, OV> removedMapperCache = new HashMap<>();

	private IdListTracker(String name, IdList<OV> mappers) {
		this.name = name;
		this.mappers = mappers;
	}

	public static <V, OV> void register(Registry<V> registry, String name, IdList<OV> mappers) {
		IdListTracker<V, OV> updater = new IdListTracker<>(name, mappers);
		RegistryEntryAddedCallback.event(registry).register(updater);
		RegistryIdRemapCallback.event(registry).register(updater);
	}

	@Override
	public void onEntryAdded(int rawId, Identifier id, V object) {
		if (removedMapperCache.containsKey(id)) {
			mappers.set(removedMapperCache.get(id), rawId);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRemap(RemapState<V> state) {
		((RemovableIdList<OV>) mappers).fabric_remapIds(state.getRawIdChangeMap());
	}
}
