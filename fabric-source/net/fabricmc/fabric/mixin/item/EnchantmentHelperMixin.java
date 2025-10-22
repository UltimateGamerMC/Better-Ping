package net.fabricmc.fabric.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {
	@Redirect(
			method = "method_60143",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isPrimaryItem(Lnet/minecraft/item/ItemStack;)Z")
	)
	private static boolean useCustomEnchantingChecks(Enchantment instance, ItemStack stack, ItemStack itemStack, boolean bl, RegistryEntry<Enchantment> registryEntry) {
		return stack.canBeEnchantedWith(registryEntry, EnchantingContext.PRIMARY);
	}
}
