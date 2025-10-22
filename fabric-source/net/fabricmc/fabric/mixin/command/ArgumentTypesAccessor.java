package net.fabricmc.fabric.mixin.command;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;

@Mixin(ArgumentTypes.class)
public interface ArgumentTypesAccessor {
	@Accessor("CLASS_MAP")
	static Map<Class<?>, ArgumentSerializer<?, ?>> fabric_getClassMap() {
		throw new AssertionError("");
	}
}
