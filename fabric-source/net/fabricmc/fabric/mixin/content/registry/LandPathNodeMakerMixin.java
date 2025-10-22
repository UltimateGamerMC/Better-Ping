package net.fabricmc.fabric.mixin.content.registry;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;

// Applied a bit earlier than other mods to ensure changes and optimizations to default vanilla behavior
@Mixin(value = LandPathNodeMaker.class, priority = 999)
public class LandPathNodeMakerMixin {
	/**
	 * Overrides the node type for the specified position, if the position is a direct target in a path.
	 */
	@Inject(method = "getCommonNodeType", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
	private static void getCommonNodeType(BlockView world, BlockPos pos, CallbackInfoReturnable<PathNodeType> cir, @Local BlockState state) {
		PathNodeType nodeType = LandPathNodeTypesRegistry.getPathNodeType(state, world, pos, false);

		if (nodeType != null) {
			cir.setReturnValue(nodeType);
		}
	}
}
