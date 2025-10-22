package net.fabricmc.fabric.mixin.entity.event.elytra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;

@SuppressWarnings("unused")
@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
	LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
		throw new AssertionError();
	}

	/**
	 * Handle ALLOW and CUSTOM {@link EntityElytraEvents} when an entity is fall flying.
	 */
	@SuppressWarnings("ConstantConditions")
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getRandom(Ljava/util/List;Lnet/minecraft/util/math/random/Random;)Ljava/lang/Object;"), method = "tickGliding()V", allow = 1, cancellable = true)
	void injectElytraTick(CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!EntityElytraEvents.ALLOW.invoker().allowElytraFlight(self)) {
			// The entity is already fall flying by now, we just need to stop it.
			if (!getEntityWorld().isClient()) {
				setFlag(Entity.GLIDING_FLAG_INDEX, false);
			}

			info.cancel();
		}

		if (EntityElytraEvents.CUSTOM.invoker().useCustomElytra(self, true)) {
			// The entity is already fall flying by now, so all we need to do is an early return to bypass vanilla's own elytra check.
			info.cancel();
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EquipmentSlot;VALUES:Ljava/util/List;"), method = "canGlide", allow = 1, cancellable = true)
	void injectElytraCheck(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!EntityElytraEvents.ALLOW.invoker().allowElytraFlight(self)) {
			cir.setReturnValue(false);
			return; // Return to prevent the rest of this injector from running.
		}

		if (EntityElytraEvents.CUSTOM.invoker().useCustomElytra(self, false)) {
			cir.setReturnValue(true);
		}
	}
}
