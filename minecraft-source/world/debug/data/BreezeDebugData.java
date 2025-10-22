/*
 * External method calls:
 *   Lnet/minecraft/network/codec/PacketCodec;collect(Lnet/minecraft/network/codec/PacketCodec$ResultFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Ljava/util/function/BiFunction;)Lnet/minecraft/network/codec/PacketCodec;
 */
package net.minecraft.world.debug.data;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

public record BreezeDebugData(Optional<Integer> attackTarget, Optional<BlockPos> jumpTarget) {
    public static final PacketCodec<ByteBuf, BreezeDebugData> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT.collect(PacketCodecs::optional), BreezeDebugData::attackTarget, BlockPos.PACKET_CODEC.collect(PacketCodecs::optional), BreezeDebugData::jumpTarget, BreezeDebugData::new);
}

