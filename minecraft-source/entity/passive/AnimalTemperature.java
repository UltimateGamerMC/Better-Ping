/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 */
package net.minecraft.entity.passive;

import net.minecraft.util.Identifier;

public interface AnimalTemperature {
    public static final Identifier TEMPERATE = Identifier.ofVanilla("temperate");
    public static final Identifier WARM = Identifier.ofVanilla("warm");
    public static final Identifier COLD = Identifier.ofVanilla("cold");
}

