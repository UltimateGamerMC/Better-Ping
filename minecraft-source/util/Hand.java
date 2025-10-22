/*
 * Internal private/static methods:
 *   Lnet/minecraft/util/Hand;method_36598()[Lnet/minecraft/util/Hand;
 */
package net.minecraft.util;

import net.minecraft.entity.EquipmentSlot;

public enum Hand {
    MAIN_HAND,
    OFF_HAND;


    public EquipmentSlot getEquipmentSlot() {
        return this == MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }
}

