/*
 * External method calls:
 *   Lnet/minecraft/entity/ai/brain/Brain;remember(Lnet/minecraft/entity/ai/brain/MemoryModuleType;Ljava/util/Optional;)V
 *   Lnet/minecraft/entity/ai/brain/Brain;remember(Lnet/minecraft/entity/ai/brain/MemoryModuleType;Ljava/lang/Object;J)V
 *   Lnet/minecraft/entity/ai/brain/Brain;forget(Lnet/minecraft/entity/ai/brain/MemoryModuleType;)V
 */
package net.minecraft.entity.ai.brain;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.K1;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;

public final class MemoryQueryResult<F extends K1, Value> {
    private final Brain<?> brain;
    private final MemoryModuleType<Value> memory;
    private final App<F, Value> value;

    public MemoryQueryResult(Brain<?> brain, MemoryModuleType<Value> memory, App<F, Value> value) {
        this.brain = brain;
        this.memory = memory;
        this.value = value;
    }

    public App<F, Value> getValue() {
        return this.value;
    }

    public void remember(Value value) {
        this.brain.remember(this.memory, Optional.of(value));
    }

    public void remember(Optional<Value> value) {
        this.brain.remember(this.memory, value);
    }

    public void remember(Value value, long expiry) {
        this.brain.remember(this.memory, value, expiry);
    }

    public void forget() {
        this.brain.forget(this.memory);
    }
}

