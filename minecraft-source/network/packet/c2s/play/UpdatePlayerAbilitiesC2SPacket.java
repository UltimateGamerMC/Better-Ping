/*
 * External method calls:
 *   Lnet/minecraft/network/PacketByteBuf;writeByte(I)Lnet/minecraft/network/PacketByteBuf;
 *   Lnet/minecraft/network/listener/ServerPlayPacketListener;onUpdatePlayerAbilities(Lnet/minecraft/network/packet/c2s/play/UpdatePlayerAbilitiesC2SPacket;)V
 *   Lnet/minecraft/network/packet/Packet;createCodec(Lnet/minecraft/network/codec/ValueFirstEncoder;Lnet/minecraft/network/codec/PacketDecoder;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/c2s/play/UpdatePlayerAbilitiesC2SPacket;apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V
 */
package net.minecraft.network.packet.c2s.play;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;

public class UpdatePlayerAbilitiesC2SPacket
implements Packet<ServerPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, UpdatePlayerAbilitiesC2SPacket> CODEC = Packet.createCodec(UpdatePlayerAbilitiesC2SPacket::write, UpdatePlayerAbilitiesC2SPacket::new);
    private static final int FLYING_MASK = 2;
    private final boolean flying;

    public UpdatePlayerAbilitiesC2SPacket(PlayerAbilities abilities) {
        this.flying = abilities.flying;
    }

    private UpdatePlayerAbilitiesC2SPacket(PacketByteBuf buf) {
        byte b = buf.readByte();
        this.flying = (b & 2) != 0;
    }

    private void write(PacketByteBuf buf) {
        int b = 0;
        if (this.flying) {
            b = (byte)(b | 2);
        }
        buf.writeByte(b);
    }

    @Override
    public PacketType<UpdatePlayerAbilitiesC2SPacket> getPacketType() {
        return PlayPackets.PLAYER_ABILITIES_C2S;
    }

    @Override
    public void apply(ServerPlayPacketListener arg) {
        arg.onUpdatePlayerAbilities(this);
    }

    public boolean isFlying() {
        return this.flying;
    }
}

