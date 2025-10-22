package net.minecraft.client.render.item.property.bool;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public record UsingItemProperty() implements BooleanProperty
{
    public static final MapCodec<UsingItemProperty> CODEC = MapCodec.unit(new UsingItemProperty());

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (entity == null) {
            return false;
        }
        return entity.isUsingItem() && entity.getActiveItem() == stack;
    }

    public MapCodec<UsingItemProperty> getCodec() {
        return CODEC;
    }
}

