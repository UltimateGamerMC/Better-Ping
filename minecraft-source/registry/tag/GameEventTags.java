/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 *   Lnet/minecraft/registry/tag/TagKey;of(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/util/Identifier;)Lnet/minecraft/registry/tag/TagKey;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/registry/tag/GameEventTags;of(Ljava/lang/String;)Lnet/minecraft/registry/tag/TagKey;
 */
package net.minecraft.registry.tag;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

public class GameEventTags {
    public static final TagKey<GameEvent> VIBRATIONS = GameEventTags.of("vibrations");
    public static final TagKey<GameEvent> WARDEN_CAN_LISTEN = GameEventTags.of("warden_can_listen");
    public static final TagKey<GameEvent> SHRIEKER_CAN_LISTEN = GameEventTags.of("shrieker_can_listen");
    public static final TagKey<GameEvent> IGNORE_VIBRATIONS_SNEAKING = GameEventTags.of("ignore_vibrations_sneaking");
    public static final TagKey<GameEvent> ALLAY_CAN_LISTEN = GameEventTags.of("allay_can_listen");

    private static TagKey<GameEvent> of(String id) {
        return TagKey.of(RegistryKeys.GAME_EVENT, Identifier.ofVanilla(id));
    }
}

