/*
 * External method calls:
 *   Lnet/minecraft/server/world/ServerWorld;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V
 */
package net.minecraft.block.dispenser;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.WorldEvents;

public abstract class FallibleItemDispenserBehavior
extends ItemDispenserBehavior {
    private boolean success = true;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.world().syncWorldEvent(this.isSuccess() ? WorldEvents.DISPENSER_DISPENSES : WorldEvents.DISPENSER_FAILS, pointer.pos(), 0);
    }
}

