/*
 * Internal private/static methods:
 *   Lnet/minecraft/registry/Registerable;register(Lnet/minecraft/registry/RegistryKey;Ljava/lang/Object;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/registry/entry/RegistryEntry$Reference;
 */
package net.minecraft.registry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public interface Registerable<T> {
    public RegistryEntry.Reference<T> register(RegistryKey<T> var1, T var2, Lifecycle var3);

    default public RegistryEntry.Reference<T> register(RegistryKey<T> key, T value) {
        return this.register(key, value, Lifecycle.stable());
    }

    public <S> RegistryEntryLookup<S> getRegistryLookup(RegistryKey<? extends Registry<? extends S>> var1);
}

