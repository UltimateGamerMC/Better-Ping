/*
 * External method calls:
 *   Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
 */
package net.minecraft.enchantment.provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.provider.ByCostEnchantmentProvider;
import net.minecraft.enchantment.provider.ByCostWithDifficultyEnchantmentProvider;
import net.minecraft.enchantment.provider.EnchantmentProvider;
import net.minecraft.enchantment.provider.SingleEnchantmentProvider;
import net.minecraft.registry.Registry;

public interface EnchantmentProviderType {
    public static MapCodec<? extends EnchantmentProvider> registerAndGetDefault(Registry<MapCodec<? extends EnchantmentProvider>> registry) {
        Registry.register(registry, "by_cost", ByCostEnchantmentProvider.CODEC);
        Registry.register(registry, "by_cost_with_difficulty", ByCostWithDifficultyEnchantmentProvider.CODEC);
        return Registry.register(registry, "single", SingleEnchantmentProvider.CODEC);
    }
}

