/*
 * Internal private/static methods:
 *   Lnet/minecraft/util/logging/LoggerPrintStream;log(Ljava/lang/String;)V
 */
package net.minecraft.util.logging;

import com.mojang.logging.LogUtils;
import java.io.OutputStream;
import java.io.PrintStream;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class LoggerPrintStream
extends PrintStream {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final String name;

    public LoggerPrintStream(String name, OutputStream out) {
        super(out);
        this.name = name;
    }

    @Override
    public void println(@Nullable String message) {
        this.log(message);
    }

    @Override
    public void println(Object object) {
        this.log(String.valueOf(object));
    }

    protected void log(@Nullable String message) {
        LOGGER.info("[{}]: {}", (Object)this.name, (Object)message);
    }
}

