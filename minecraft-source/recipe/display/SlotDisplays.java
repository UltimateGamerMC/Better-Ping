/*
 * External method calls:
 *   Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
 */
package net.minecraft.recipe.display;

import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.Registry;

public class SlotDisplays {
    public static SlotDisplay.Serializer<?> registerAndGetDefault(Registry<SlotDisplay.Serializer<?>> registries) {
        Registry.register(registries, "empty", SlotDisplay.EmptySlotDisplay.SERIALIZER);
        Registry.register(registries, "any_fuel", SlotDisplay.AnyFuelSlotDisplay.SERIALIZER);
        Registry.register(registries, "item", SlotDisplay.ItemSlotDisplay.SERIALIZER);
        Registry.register(registries, "item_stack", SlotDisplay.StackSlotDisplay.SERIALIZER);
        Registry.register(registries, "tag", SlotDisplay.TagSlotDisplay.SERIALIZER);
        Registry.register(registries, "smithing_trim", SlotDisplay.SmithingTrimSlotDisplay.SERIALIZER);
        Registry.register(registries, "with_remainder", SlotDisplay.WithRemainderSlotDisplay.SERIALIZER);
        return Registry.register(registries, "composite", SlotDisplay.CompositeSlotDisplay.SERIALIZER);
    }
}

