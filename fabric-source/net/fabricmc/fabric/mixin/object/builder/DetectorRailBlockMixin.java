package net.fabricmc.fabric.mixin.object.builder;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.object.builder.v1.entity.MinecartComparatorLogicRegistry;

@Mixin(DetectorRailBlock.class)
public abstract class DetectorRailBlockMixin {
	@Shadow protected abstract <T extends AbstractMinecartEntity> List<T> getCarts(World world, BlockPos pos, Class<T> entityClass, @Nullable Predicate<Entity> entityPredicate);

	@Inject(at = @At("HEAD"), method = "getComparatorOutput", cancellable = true)
	private void getCustomComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir) {
		if (state.get(DetectorRailBlock.POWERED)) {
			List<AbstractMinecartEntity> carts = getCarts(world, pos, AbstractMinecartEntity.class,
					cart -> MinecartComparatorLogicRegistry.getCustomComparatorLogic(cart.getType()) != null);
			for (AbstractMinecartEntity cart : carts) {
				int comparatorValue = MinecartComparatorLogicRegistry.getCustomComparatorLogic(cart.getType())
						.getComparatorValue(cart, state, pos);
				if (comparatorValue >= 0) {
					cir.setReturnValue(comparatorValue);
					break;
				}
			}
		}
	}
}
