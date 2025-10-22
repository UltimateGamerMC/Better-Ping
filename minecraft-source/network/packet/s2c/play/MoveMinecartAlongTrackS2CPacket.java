/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onMoveMinecartAlongTrack(Lnet/minecraft/network/packet/s2c/play/MoveMinecartAlongTrackS2CPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodecs;toList()Lnet/minecraft/network/codec/PacketCodec$ResultFunction;
 *   Lnet/minecraft/network/codec/PacketCodec;collect(Lnet/minecraft/network/codec/PacketCodec$ResultFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Ljava/util/function/BiFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/MoveMinecartAlongTrackS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public record MoveMinecartAlongTrackS2CPacket(int entityId, List<ExperimentalMinecartController.Step> lerpSteps) implements Packet<ClientPlayPacketListener>
{
    public static final PacketCodec<PacketByteBuf, MoveMinecartAlongTrackS2CPacket> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, MoveMinecartAlongTrackS2CPacket::entityId, ExperimentalMinecartController.Step.PACKET_CODEC.collect(PacketCodecs.toList()), MoveMinecartAlongTrackS2CPacket::lerpSteps, MoveMinecartAlongTrackS2CPacket::new);

    @Override
    public PacketType<MoveMinecartAlongTrackS2CPacket> getPacketType() {
        return PlayPackets.MOVE_MINECART_ALONG_TRACK;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onMoveMinecartAlongTrack(this);
    }

    @Nullable
    public Entity getEntity(World world) {
        return world.getEntityById(this.entityId);
    }
}

