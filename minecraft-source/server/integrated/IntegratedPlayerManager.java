/*
 * External method calls:
 *   Lnet/minecraft/storage/NbtWriteView;create(Lnet/minecraft/util/ErrorReporter;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/storage/NbtWriteView;
 *   Lnet/minecraft/server/network/ServerPlayerEntity;writeData(Lnet/minecraft/storage/WriteView;)V
 *   Lnet/minecraft/server/PlayerManager;savePlayerData(Lnet/minecraft/server/network/ServerPlayerEntity;)V
 *   Lnet/minecraft/server/PlayerConfigEntry;name()Ljava/lang/String;
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *   Lnet/minecraft/server/PlayerManager;checkCanJoin(Ljava/net/SocketAddress;Lnet/minecraft/server/PlayerConfigEntry;)Lnet/minecraft/text/Text;
 */
package net.minecraft.server.integrated;

import com.mojang.logging.LogUtils;
import java.net.SocketAddress;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ErrorReporter;
import net.minecraft.world.PlayerSaveHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@Environment(value=EnvType.CLIENT)
public class IntegratedPlayerManager
extends PlayerManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    @Nullable
    private NbtCompound userData;

    public IntegratedPlayerManager(IntegratedServer server, CombinedDynamicRegistries<ServerDynamicRegistryType> registryManager, PlayerSaveHandler saveHandler) {
        super(server, registryManager, saveHandler, server.getManagementListener());
        this.setViewDistance(10);
    }

    @Override
    protected void savePlayerData(ServerPlayerEntity player) {
        if (this.getServer().isHost(player.getPlayerConfigEntry())) {
            try (ErrorReporter.Logging lv = new ErrorReporter.Logging(player.getErrorReporterContext(), LOGGER);){
                NbtWriteView lv2 = NbtWriteView.create(lv, player.getRegistryManager());
                player.writeData(lv2);
                this.userData = lv2.getNbt();
            }
        }
        super.savePlayerData(player);
    }

    @Override
    public Text checkCanJoin(SocketAddress address, PlayerConfigEntry configEntry) {
        if (this.getServer().isHost(configEntry) && this.getPlayer(configEntry.name()) != null) {
            return Text.translatable("multiplayer.disconnect.name_taken");
        }
        return super.checkCanJoin(address, configEntry);
    }

    @Override
    public IntegratedServer getServer() {
        return (IntegratedServer)super.getServer();
    }

    @Override
    @Nullable
    public NbtCompound getUserData() {
        return this.userData;
    }

    @Override
    public /* synthetic */ MinecraftServer getServer() {
        return this.getServer();
    }
}

