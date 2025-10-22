package net.minecraft.resource;

import net.minecraft.resource.ResourceManager;

public interface LifecycledResourceManager
extends ResourceManager,
AutoCloseable {
    @Override
    public void close();
}

