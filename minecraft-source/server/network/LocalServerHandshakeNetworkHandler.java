/*
 * External method calls:
 *   Lnet/minecraft/network/packet/c2s/handshake/HandshakeC2SPacket;intendedState()Lnet/minecraft/network/packet/c2s/handshake/ConnectionIntent;
 *   Lnet/minecraft/network/ClientConnection;transitionInbound(Lnet/minecraft/network/state/NetworkState;Lnet/minecraft/network/listener/PacketListener;)V
 *   Lnet/minecraft/network/ClientConnection;transitionOutbound(Lnet/minecraft/network/state/NetworkState;)V
 */
package net.minecraft.server.network;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.network.listener.ServerHandshakePacketListener;
import net.minecraft.network.packet.c2s.handshake.ConnectionIntent;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.state.LoginStates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;

public class LocalServerHandshakeNetworkHandler
implements ServerHandshakePacketListener {
    private final MinecraftServer server;
    private final ClientConnection connection;

    public LocalServerHandshakeNetworkHandler(MinecraftServer server, ClientConnection connection) {
        this.server = server;
        this.connection = connection;
    }

    @Override
    public void onHandshake(HandshakeC2SPacket packet) {
        if (packet.intendedState() != ConnectionIntent.LOGIN) {
            throw new UnsupportedOperationException("Invalid intention " + String.valueOf((Object)packet.intendedState()));
        }
        this.connection.transitionInbound(LoginStates.C2S, new ServerLoginNetworkHandler(this.server, this.connection, false));
        this.connection.transitionOutbound(LoginStates.S2C);
    }

    @Override
    public void onDisconnected(DisconnectionInfo info) {
    }

    @Override
    public boolean isConnectionOpen() {
        return this.connection.isOpen();
    }
}

