/*
 * External method calls:
 *   Lnet/minecraft/network/PacketByteBuf;readVelocity()Lnet/minecraft/util/math/Vec3d;
 *   Lnet/minecraft/network/PacketByteBuf;writeVarInt(I)Lnet/minecraft/network/PacketByteBuf;
 *   Lnet/minecraft/network/PacketByteBuf;writeVelocity(Lnet/minecraft/util/math/Vec3d;)V
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onEntityVelocityUpdate(Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;)V
 *   Lnet/minecraft/network/packet/Packet;createCodec(Lnet/minecraft/network/codec/ValueFirstEncoder;Lnet/minecraft/network/codec/PacketDecoder;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.util.math.Vec3d;

public class EntityVelocityUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, EntityVelocityUpdateS2CPacket> CODEC = Packet.createCodec(EntityVelocityUpdateS2CPacket::write, EntityVelocityUpdateS2CPacket::new);
    private final int entityId;
    private final Vec3d velocity;

    public EntityVelocityUpdateS2CPacket(Entity entity) {
        this(entity.getId(), entity.getVelocity());
    }

    public EntityVelocityUpdateS2CPacket(int entityId, Vec3d velocity) {
        this.entityId = entityId;
        this.velocity = velocity;
    }

    private EntityVelocityUpdateS2CPacket(PacketByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.velocity = buf.readVelocity();
    }

    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeVelocity(this.velocity);
    }

    @Override
    public PacketType<EntityVelocityUpdateS2CPacket> getPacketType() {
        return PlayPackets.SET_ENTITY_MOTION;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onEntityVelocityUpdate(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public Vec3d getVelocity() {
        return this.velocity;
    }
}

