/*
 * External method calls:
 *   Lnet/minecraft/network/codec/PacketCodecs;registryEntry(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/network/codec/PacketCodec;
 *   Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function3;)Lnet/minecraft/network/codec/PacketCodec;
 */
package net.minecraft.world.debug.data;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;

public record PoiDebugData(BlockPos pos, RegistryEntry<PointOfInterestType> poiType, int freeTicketCount) {
    public static final PacketCodec<RegistryByteBuf, PoiDebugData> PACKET_CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, PoiDebugData::pos, PacketCodecs.registryEntry(RegistryKeys.POINT_OF_INTEREST_TYPE), PoiDebugData::poiType, PacketCodecs.VAR_INT, PoiDebugData::freeTicketCount, PoiDebugData::new);

    public PoiDebugData(PointOfInterest poi) {
        this(poi.getPos(), poi.getType(), poi.getFreeTickets());
    }
}

