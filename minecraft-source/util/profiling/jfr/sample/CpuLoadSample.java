package net.minecraft.util.profiling.jfr.sample;

import jdk.jfr.consumer.RecordedEvent;

public record CpuLoadSample(double jvm, double userJvm, double system) {
    public static CpuLoadSample fromEvent(RecordedEvent event) {
        return new CpuLoadSample(event.getFloat("jvmSystem"), event.getFloat("jvmUser"), event.getFloat("machineTotal"));
    }
}

