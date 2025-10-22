package net.fabricmc.fabric.impl.networking;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.Identifier;

public interface FabricRegistryByteBuf {
	void fabric_setSendableConfigurationChannels(Set<Identifier> globalChannels);

	@Nullable
	Set<Identifier> fabric_getSendableConfigurationChannels();
}
