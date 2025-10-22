package net.minecraft.network.listener;

import net.minecraft.network.NetworkPhase;
import net.minecraft.network.listener.ServerCookieResponsePacketListener;
import net.minecraft.network.packet.c2s.login.EnterConfigurationC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginKeyC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginQueryResponseC2SPacket;

public interface ServerLoginPacketListener
extends ServerCookieResponsePacketListener {
    @Override
    default public NetworkPhase getPhase() {
        return NetworkPhase.LOGIN;
    }

    public void onHello(LoginHelloC2SPacket var1);

    public void onKey(LoginKeyC2SPacket var1);

    public void onQueryResponse(LoginQueryResponseC2SPacket var1);

    public void onEnterConfiguration(EnterConfigurationC2SPacket var1);
}

