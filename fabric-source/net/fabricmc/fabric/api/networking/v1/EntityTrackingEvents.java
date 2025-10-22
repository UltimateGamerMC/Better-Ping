package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Events related to a tracking entities within a player's view distance.
 */
public final class EntityTrackingEvents {
	/**
	 * An event that is called after a player has started tracking an entity.
	 * Typically, this occurs when an entity enters a client's view distance.
	 * This event is called after the entity's {@linkplain Entity#createSpawnPacket spawn packet} is sent to the player.
	 */
	public static final Event<StartTracking> START_TRACKING = EventFactory.createArrayBacked(StartTracking.class, callbacks -> (trackedEntity, player) -> {
		for (StartTracking callback : callbacks) {
			callback.onStartTracking(trackedEntity, player);
		}
	});

	/**
	 * An event that is called before a player stops tracking an entity.
	 * The entity still exists on the server and on the player's client.
	 */
	public static final Event<StopTracking> STOP_TRACKING = EventFactory.createArrayBacked(StopTracking.class, callbacks -> (trackedEntity, player) -> {
		for (StopTracking callback : callbacks) {
			callback.onStopTracking(trackedEntity, player);
		}
	});

	@FunctionalInterface
	public interface StartTracking {
		/**
		 * Called after a player has started tracking an entity.
		 *
		 * @param trackedEntity the entity that will be tracked
		 * @param player the player that will track the entity
		 */
		void onStartTracking(Entity trackedEntity, ServerPlayerEntity player);
	}

	@FunctionalInterface
	public interface StopTracking {
		/**
		 * Called before an entity stops getting tracked by a player.
		 *
		 * @param trackedEntity the entity that is about to stop being tracked
		 * @param player the player that is about to stop tracking the entity
		 */
		void onStopTracking(Entity trackedEntity, ServerPlayerEntity player);
	}

	private EntityTrackingEvents() {
	}
}
