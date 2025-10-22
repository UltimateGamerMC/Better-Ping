package net.fabricmc.fabric.mixin.transfer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(ContainerComponent.class)
public interface ContainerComponentAccessor {
	@Accessor("stacks")
	DefaultedList<ItemStack> fabric_getStacks();
}
