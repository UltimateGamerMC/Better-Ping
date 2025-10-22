package net.minecraft.util.profiler;

import java.util.Set;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.ProfilerSystem;
import net.minecraft.util.profiler.SampleType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public interface ReadableProfiler
extends Profiler {
    public ProfileResult getResult();

    @Nullable
    public ProfilerSystem.LocatedInfo getInfo(String var1);

    public Set<Pair<String, SampleType>> getSampleTargets();
}

