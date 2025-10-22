/*
 * External method calls:
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/MutableText;
 */
package net.minecraft.test;

import net.minecraft.test.TestException;
import net.minecraft.text.Text;

public class GameTestException
extends TestException {
    protected final Text message;
    protected final int tick;

    public GameTestException(Text message, int tick) {
        super(message.getString());
        this.message = message;
        this.tick = tick;
    }

    @Override
    public Text getText() {
        return Text.translatable("test.error.tick", this.message, this.tick);
    }

    @Override
    public String getMessage() {
        return this.getText().getString();
    }
}

