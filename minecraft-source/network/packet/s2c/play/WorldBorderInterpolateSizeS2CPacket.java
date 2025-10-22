/*
 * External method calls:
 *   Lnet/minecraft/network/PacketByteBuf;writeDouble(D)Lnet/minecraft/network/PacketByteBuf;
 *   Lnet/minecraft/network/PacketByteBuf;writeVarLong(J)Lnet/minecraft/network/PacketByteBuf;
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onWorldBorderInterpolateSize(Lnet/minecraft/network/packet/s2c/play/WorldBorderInterpolateSizeS2CPacket;)V
 *   Lnet/minecraft/network/packet/Packet;createCodec(Lnet/minecraft/network/codec/ValueFirstEncoder;Lnet/minecraft/network/codec/PacketDecoder;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/WorldBorderInterpolateSizeS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.world.border.WorldBorder;

public class WorldBorderInterpolateSizeS2CPacket
implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, WorldBorderInterpolateSizeS2CPacket> CODEC = Packet.createCodec(WorldBorderInterpolateSizeS2CPacket::write, WorldBorderInterpolateSizeS2CPacket::new);
    private final double size;
    private final double sizeLerpTarget;
    private final long sizeLerpTime;

    public WorldBorderInterpolateSizeS2CPacket(WorldBorder worldBorder) {
        this.size = worldBorder.getSize();
        this.sizeLerpTarget = worldBorder.getSizeLerpTarget();
        this.sizeLerpTime = worldBorder.getSizeLerpTime();
    }

    private WorldBorderInterpolateSizeS2CPacket(PacketByteBuf buf) {
        this.size = buf.readDouble();
        this.sizeLerpTarget = buf.readDouble();
        this.sizeLerpTime = buf.readVarLong();
    }

    private void write(PacketByteBuf buf) {
        buf.writeDouble(this.size);
        buf.writeDouble(this.sizeLerpTarget);
        buf.writeVarLong(this.sizeLerpTime);
    }

    @Override
    public PacketType<WorldBorderInterpolateSizeS2CPacket> getPacketType() {
        return PlayPackets.SET_BORDER_LERP_SIZE;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onWorldBorderInterpolateSize(this);
    }

    public double getSize() {
        return this.size;
    }

    public double getSizeLerpTarget() {
        return this.sizeLerpTarget;
    }

    public long getSizeLerpTime() {
        return this.sizeLerpTime;
    }
}

