/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ServerCommonPacketListener;onCustomClickAction(Lnet/minecraft/network/packet/c2s/common/CustomClickActionC2SPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodecs;nbtElement(Ljava/util/function/Supplier;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodecs;lengthPrepended(I)Lnet/minecraft/network/codec/PacketCodec$ResultFunction;
 *   Lnet/minecraft/network/codec/PacketCodec;collect(Lnet/minecraft/network/codec/PacketCodec$ResultFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Ljava/util/function/BiFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/c2s/common/CustomClickActionC2SPacket;apply(Lnet/minecraft/network/listener/ServerCommonPacketListener;)V
 */
package net.minecraft.network.packet.c2s.common;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ServerCommonPacketListener;
import net.minecraft.network.packet.CommonPackets;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.util.Identifier;

public record CustomClickActionC2SPacket(Identifier id, Optional<NbtElement> payload) implements Packet<ServerCommonPacketListener>
{
    private static final PacketCodec<ByteBuf, Optional<NbtElement>> field_60958 = PacketCodecs.nbtElement(() -> new NbtSizeTracker(32768L, 16)).collect(PacketCodecs.lengthPrepended(65536));
    public static final PacketCodec<ByteBuf, CustomClickActionC2SPacket> CODEC = PacketCodec.tuple(Identifier.PACKET_CODEC, CustomClickActionC2SPacket::id, field_60958, CustomClickActionC2SPacket::payload, CustomClickActionC2SPacket::new);

    @Override
    public PacketType<CustomClickActionC2SPacket> getPacketType() {
        return CommonPackets.CUSTOM_CLICK_ACTION;
    }

    @Override
    public void apply(ServerCommonPacketListener arg) {
        arg.onCustomClickAction(this);
    }
}

