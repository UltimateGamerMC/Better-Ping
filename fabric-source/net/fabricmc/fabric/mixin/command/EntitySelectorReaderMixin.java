package net.fabricmc.fabric.mixin.command;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.command.EntitySelectorReader;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.command.v2.FabricEntitySelectorReader;

@Mixin(EntitySelectorReader.class)
public class EntitySelectorReaderMixin implements FabricEntitySelectorReader {
	@Unique
	private final Set<Identifier> flags = new HashSet<>();

	@Override
	public void setCustomFlag(Identifier key, boolean value) {
		if (value) {
			this.flags.add(key);
		} else {
			this.flags.remove(key);
		}
	}

	@Override
	public boolean getCustomFlag(Identifier key) {
		return this.flags.contains(key);
	}
}
