/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onBundle(Lnet/minecraft/network/packet/s2c/play/BundleS2CPacket;)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/BundleS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.BundlePacket;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;

public class BundleS2CPacket
extends BundlePacket<ClientPlayPacketListener> {
    public BundleS2CPacket(Iterable<Packet<? super ClientPlayPacketListener>> iterable) {
        super(iterable);
    }

    @Override
    public PacketType<BundleS2CPacket> getPacketType() {
        return PlayPackets.BUNDLE;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onBundle(this);
    }
}

