/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onBlockUpdate(Lnet/minecraft/network/packet/s2c/play/BlockUpdateS2CPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodecs;entryOf(Lnet/minecraft/util/collection/IndexedIterable;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Ljava/util/function/BiFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/BlockUpdateS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class BlockUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<RegistryByteBuf, BlockUpdateS2CPacket> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, BlockUpdateS2CPacket::getPos, PacketCodecs.entryOf(Block.STATE_IDS), BlockUpdateS2CPacket::getState, BlockUpdateS2CPacket::new);
    private final BlockPos pos;
    private final BlockState state;

    public BlockUpdateS2CPacket(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
    }

    public BlockUpdateS2CPacket(BlockView world, BlockPos pos) {
        this(pos, world.getBlockState(pos));
    }

    @Override
    public PacketType<BlockUpdateS2CPacket> getPacketType() {
        return PlayPackets.BLOCK_UPDATE;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onBlockUpdate(this);
    }

    public BlockState getState() {
        return this.state;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}

