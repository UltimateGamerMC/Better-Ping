/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onExplosion(Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodec;collect(Lnet/minecraft/network/codec/PacketCodec$ResultFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/util/collection/Pool;createPacketCodec(Lnet/minecraft/network/codec/PacketCodec;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function7;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import java.util.Optional;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.particle.BlockParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Vec3d;

public record ExplosionS2CPacket(Vec3d center, float radius, int blockCount, Optional<Vec3d> playerKnockback, ParticleEffect explosionParticle, RegistryEntry<SoundEvent> explosionSound, Pool<BlockParticleEffect> blockParticles) implements Packet<ClientPlayPacketListener>
{
    public static final PacketCodec<RegistryByteBuf, ExplosionS2CPacket> CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC, ExplosionS2CPacket::center, PacketCodecs.FLOAT, ExplosionS2CPacket::radius, PacketCodecs.INTEGER, ExplosionS2CPacket::blockCount, Vec3d.PACKET_CODEC.collect(PacketCodecs::optional), ExplosionS2CPacket::playerKnockback, ParticleTypes.PACKET_CODEC, ExplosionS2CPacket::explosionParticle, SoundEvent.ENTRY_PACKET_CODEC, ExplosionS2CPacket::explosionSound, Pool.createPacketCodec(BlockParticleEffect.PACKET_CODEC), ExplosionS2CPacket::blockParticles, ExplosionS2CPacket::new);

    @Override
    public PacketType<ExplosionS2CPacket> getPacketType() {
        return PlayPackets.EXPLODE;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onExplosion(this);
    }
}

