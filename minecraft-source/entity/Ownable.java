package net.minecraft.entity;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface Ownable {
    @Nullable
    public Entity getOwner();
}

