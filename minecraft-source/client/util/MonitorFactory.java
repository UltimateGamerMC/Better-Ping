package net.minecraft.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Monitor;

@Environment(value=EnvType.CLIENT)
public interface MonitorFactory {
    public Monitor createMonitor(long var1);
}

