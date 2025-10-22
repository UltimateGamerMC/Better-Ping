package net.fabricmc.fabric.mixin.content.registry;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {
	@Accessor("STRIPPED_BLOCKS")
	static Map<Block, Block> getStrippedBlocks() {
		throw new AssertionError("Untransformed @Accessor");
	}

	@Accessor("STRIPPED_BLOCKS")
	@Mutable
	static void setStrippedBlocks(Map<Block, Block> strippedBlocks) {
		throw new AssertionError("Untransformed @Accessor");
	}
}
