/*
 * External method calls:
 *   Lnet/minecraft/server/MinecraftServer$ServerResourcePackProperties;id()Ljava/util/UUID;
 *   Lnet/minecraft/server/MinecraftServer$ServerResourcePackProperties;url()Ljava/lang/String;
 *   Lnet/minecraft/server/MinecraftServer$ServerResourcePackProperties;hash()Ljava/lang/String;
 *   Lnet/minecraft/server/MinecraftServer$ServerResourcePackProperties;prompt()Lnet/minecraft/text/Text;
 */
package net.minecraft.server.network;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerConfigurationTask;

public class SendResourcePackTask
implements ServerPlayerConfigurationTask {
    public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key("server_resource_pack");
    private final MinecraftServer.ServerResourcePackProperties packProperties;

    public SendResourcePackTask(MinecraftServer.ServerResourcePackProperties packProperties) {
        this.packProperties = packProperties;
    }

    @Override
    public void sendPacket(Consumer<Packet<?>> sender) {
        sender.accept(new ResourcePackSendS2CPacket(this.packProperties.id(), this.packProperties.url(), this.packProperties.hash(), this.packProperties.isRequired(), Optional.ofNullable(this.packProperties.prompt())));
    }

    @Override
    public ServerPlayerConfigurationTask.Key getKey() {
        return KEY;
    }
}

