/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ServerPlayPacketListener;onClientTickEnd(Lnet/minecraft/network/packet/c2s/play/ClientTickEndC2SPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodec;unit(Ljava/lang/Object;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/c2s/play/ClientTickEndC2SPacket;apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V
 */
package net.minecraft.network.packet.c2s.play;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;

public record ClientTickEndC2SPacket() implements Packet<ServerPlayPacketListener>
{
    public static final ClientTickEndC2SPacket INSTANCE = new ClientTickEndC2SPacket();
    public static final PacketCodec<ByteBuf, ClientTickEndC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public PacketType<ClientTickEndC2SPacket> getPacketType() {
        return PlayPackets.CLIENT_TICK_END;
    }

    @Override
    public void apply(ServerPlayPacketListener arg) {
        arg.onClientTickEnd(this);
    }
}

