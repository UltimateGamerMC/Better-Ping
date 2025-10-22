package net.minecraft.client.render.item.tint;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public interface TintSource {
    public int getTint(ItemStack var1, @Nullable ClientWorld var2, @Nullable LivingEntity var3);

    public MapCodec<? extends TintSource> getCodec();
}

