/*
 * External method calls:
 *   Lnet/minecraft/util/function/CharPredicate;test(C)Z
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import net.minecraft.util.function.CharPredicate;

public class ArgumentReaderUtils {
    public static String readWhileMatching(StringReader stringReader, CharPredicate predicate) {
        int i = stringReader.getCursor();
        while (stringReader.canRead() && predicate.test(stringReader.peek())) {
            stringReader.skip();
        }
        return stringReader.getString().substring(i, stringReader.getCursor());
    }
}

