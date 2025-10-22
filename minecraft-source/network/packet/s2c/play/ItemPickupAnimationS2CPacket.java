/*
 * External method calls:
 *   Lnet/minecraft/network/PacketByteBuf;writeVarInt(I)Lnet/minecraft/network/PacketByteBuf;
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onItemPickupAnimation(Lnet/minecraft/network/packet/s2c/play/ItemPickupAnimationS2CPacket;)V
 *   Lnet/minecraft/network/packet/Packet;createCodec(Lnet/minecraft/network/codec/ValueFirstEncoder;Lnet/minecraft/network/codec/PacketDecoder;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/ItemPickupAnimationS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;

public class ItemPickupAnimationS2CPacket
implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ItemPickupAnimationS2CPacket> CODEC = Packet.createCodec(ItemPickupAnimationS2CPacket::write, ItemPickupAnimationS2CPacket::new);
    private final int entityId;
    private final int collectorEntityId;
    private final int stackAmount;

    public ItemPickupAnimationS2CPacket(int entityId, int collectorId, int stackAmount) {
        this.entityId = entityId;
        this.collectorEntityId = collectorId;
        this.stackAmount = stackAmount;
    }

    private ItemPickupAnimationS2CPacket(PacketByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.collectorEntityId = buf.readVarInt();
        this.stackAmount = buf.readVarInt();
    }

    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeVarInt(this.collectorEntityId);
        buf.writeVarInt(this.stackAmount);
    }

    @Override
    public PacketType<ItemPickupAnimationS2CPacket> getPacketType() {
        return PlayPackets.TAKE_ITEM_ENTITY;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onItemPickupAnimation(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public int getCollectorEntityId() {
        return this.collectorEntityId;
    }

    public int getStackAmount() {
        return this.stackAmount;
    }
}

