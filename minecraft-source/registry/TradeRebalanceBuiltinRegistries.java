/*
 * External method calls:
 *   Lnet/minecraft/registry/ExperimentalRegistriesValidator;validate(Ljava/util/concurrent/CompletableFuture;Lnet/minecraft/registry/RegistryBuilder;)Ljava/util/concurrent/CompletableFuture;
 *   Lnet/minecraft/registry/RegistryBuilder;addRegistry(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/registry/RegistryBuilder$BootstrapFunction;)Lnet/minecraft/registry/RegistryBuilder;
 */
package net.minecraft.registry;

import java.util.concurrent.CompletableFuture;
import net.minecraft.enchantment.provider.TradeRebalanceEnchantmentProviders;
import net.minecraft.registry.ExperimentalRegistriesValidator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class TradeRebalanceBuiltinRegistries {
    private static final RegistryBuilder REGISTRY_BUILDER = new RegistryBuilder().addRegistry(RegistryKeys.ENCHANTMENT_PROVIDER, TradeRebalanceEnchantmentProviders::bootstrap);

    public static CompletableFuture<RegistryBuilder.FullPatchesRegistriesPair> validate(CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        return ExperimentalRegistriesValidator.validate(registriesFuture, REGISTRY_BUILDER);
    }
}

