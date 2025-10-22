package net.fabricmc.fabric.mixin.event.lifecycle;

import java.util.function.BooleanSupplier;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.world.ServerWorld;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
	// Make sure "insideBlockTick" is true before we call the start tick, so inject after it is set
	@Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/server/world/ServerWorld;inBlockTick:Z", opcode = Opcodes.PUTFIELD, ordinal = 0, shift = At.Shift.AFTER))
	private void startWorldTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ServerTickEvents.START_WORLD_TICK.invoker().onStartTick((ServerWorld) (Object) this);
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void endWorldTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ServerTickEvents.END_WORLD_TICK.invoker().onEndTick((ServerWorld) (Object) this);
	}
}
