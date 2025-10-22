package net.fabricmc.fabric.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.impl.item.ItemExtensions;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
	@Inject(method = "getPreferredEquipmentSlot", at = @At(value = "HEAD"), cancellable = true)
	private void onGetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info) {
		EquipmentSlotProvider equipmentSlotProvider = ((ItemExtensions) stack.getItem()).fabric_getEquipmentSlotProvider();

		if (equipmentSlotProvider != null) {
			info.setReturnValue(equipmentSlotProvider.getPreferredEquipmentSlot((LivingEntity) (Object) this, stack));
		}
	}
}
