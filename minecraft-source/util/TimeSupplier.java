package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

@FunctionalInterface
public interface TimeSupplier {
    public long get(TimeUnit var1);

    public static interface Nanoseconds
    extends TimeSupplier,
    LongSupplier {
        @Override
        default public long get(TimeUnit timeUnit) {
            return timeUnit.convert(this.getAsLong(), TimeUnit.NANOSECONDS);
        }
    }
}

