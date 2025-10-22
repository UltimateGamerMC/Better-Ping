/*
 * External method calls:
 *   Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/world/timer/TimerCallbackSerializer;registerSerializer(Lnet/minecraft/util/Identifier;Lcom/mojang/serialization/MapCodec;)Lnet/minecraft/world/timer/TimerCallbackSerializer;
 */
package net.minecraft.world.timer;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.timer.FunctionTagTimerCallback;
import net.minecraft.world.timer.FunctionTimerCallback;
import net.minecraft.world.timer.TimerCallback;

public class TimerCallbackSerializer<C> {
    public static final TimerCallbackSerializer<MinecraftServer> INSTANCE = new TimerCallbackSerializer().registerSerializer(Identifier.ofVanilla("function"), FunctionTimerCallback.CODEC).registerSerializer(Identifier.ofVanilla("function_tag"), FunctionTagTimerCallback.CODEC);
    private final Codecs.IdMapper<Identifier, MapCodec<? extends TimerCallback<C>>> idMapper = new Codecs.IdMapper();
    private final Codec<TimerCallback<C>> codec = this.idMapper.getCodec(Identifier.CODEC).dispatch("Type", TimerCallback::getCodec, Function.identity());

    @VisibleForTesting
    public TimerCallbackSerializer() {
    }

    public TimerCallbackSerializer<C> registerSerializer(Identifier id, MapCodec<? extends TimerCallback<C>> codec) {
        this.idMapper.put(id, codec);
        return this;
    }

    public Codec<TimerCallback<C>> getCodec() {
        return this.codec;
    }
}

