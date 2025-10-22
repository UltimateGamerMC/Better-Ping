/*
 * External method calls:
 *   Lnet/minecraft/network/encryption/PacketEncryptionManager;encrypt(Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/network/encryption/PacketEncryptor;encode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V
 */
package net.minecraft.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;
import net.minecraft.network.encryption.PacketEncryptionManager;

public class PacketEncryptor
extends MessageToByteEncoder<ByteBuf> {
    private final PacketEncryptionManager manager;

    public PacketEncryptor(Cipher cipher) {
        this.manager = new PacketEncryptionManager(cipher);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        this.manager.encrypt(byteBuf, byteBuf2);
    }

    @Override
    protected /* synthetic */ void encode(ChannelHandlerContext context, Object buf, ByteBuf result) throws Exception {
        this.encode(context, (ByteBuf)buf, result);
    }
}

