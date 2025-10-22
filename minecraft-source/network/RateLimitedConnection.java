/*
 * External method calls:
 *   Lnet/minecraft/network/PacketCallbacks;always(Ljava/lang/Runnable;)Lio/netty/channel/ChannelFutureListener;
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/RateLimitedConnection;send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;)V
 *   Lnet/minecraft/network/RateLimitedConnection;disconnect(Lnet/minecraft/text/Text;)V
 */
package net.minecraft.network;

import com.mojang.logging.LogUtils;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class RateLimitedConnection
extends ClientConnection {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Text RATE_LIMIT_EXCEEDED_MESSAGE = Text.translatable("disconnect.exceeded_packet_rate");
    private final int rateLimit;

    public RateLimitedConnection(int rateLimit) {
        super(NetworkSide.SERVERBOUND);
        this.rateLimit = rateLimit;
    }

    @Override
    protected void updateStats() {
        super.updateStats();
        float f = this.getAveragePacketsReceived();
        if (f > (float)this.rateLimit) {
            LOGGER.warn("Player exceeded rate-limit (sent {} packets per second)", (Object)Float.valueOf(f));
            this.send(new DisconnectS2CPacket(RATE_LIMIT_EXCEEDED_MESSAGE), PacketCallbacks.always(() -> this.disconnect(RATE_LIMIT_EXCEEDED_MESSAGE)));
            this.tryDisableAutoRead();
        }
    }
}

