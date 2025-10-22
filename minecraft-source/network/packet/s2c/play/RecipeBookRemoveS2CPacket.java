/*
 * External method calls:
 *   Lnet/minecraft/network/listener/ClientPlayPacketListener;onRecipeBookRemove(Lnet/minecraft/network/packet/s2c/play/RecipeBookRemoveS2CPacket;)V
 *   Lnet/minecraft/network/codec/PacketCodecs;toList()Lnet/minecraft/network/codec/PacketCodec$ResultFunction;
 *   Lnet/minecraft/network/codec/PacketCodec;collect(Lnet/minecraft/network/codec/PacketCodec$ResultFunction;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/network/codec/PacketCodec;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/packet/s2c/play/RecipeBookRemoveS2CPacket;apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V
 */
package net.minecraft.network.packet.s2c.play;

import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.recipe.NetworkRecipeId;

public record RecipeBookRemoveS2CPacket(List<NetworkRecipeId> recipes) implements Packet<ClientPlayPacketListener>
{
    public static final PacketCodec<ByteBuf, RecipeBookRemoveS2CPacket> CODEC = PacketCodec.tuple(NetworkRecipeId.PACKET_CODEC.collect(PacketCodecs.toList()), RecipeBookRemoveS2CPacket::recipes, RecipeBookRemoveS2CPacket::new);

    @Override
    public PacketType<RecipeBookRemoveS2CPacket> getPacketType() {
        return PlayPackets.RECIPE_BOOK_REMOVE;
    }

    @Override
    public void apply(ClientPlayPacketListener arg) {
        arg.onRecipeBookRemove(this);
    }
}

