package net.minecraft.util;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

@Deprecated
public class Lazy<T> {
    private final Supplier<T> supplier = Suppliers.memoize(delegate::get);

    public Lazy(Supplier<T> delegate) {
    }

    public T get() {
        return this.supplier.get();
    }
}

