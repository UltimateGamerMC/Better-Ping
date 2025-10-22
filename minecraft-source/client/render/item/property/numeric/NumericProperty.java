package net.minecraft.client.render.item.property.numeric;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public interface NumericProperty {
    public float getValue(ItemStack var1, @Nullable ClientWorld var2, @Nullable HeldItemContext var3, int var4);

    public MapCodec<? extends NumericProperty> getCodec();
}

