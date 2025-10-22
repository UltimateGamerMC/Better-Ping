/*
 * External method calls:
 *   Lnet/minecraft/server/dedicated/management/RpcPlayer;of(Lnet/minecraft/server/network/ServerPlayerEntity;)Lnet/minecraft/server/dedicated/management/RpcPlayer;
 *   Lnet/minecraft/server/dedicated/management/dispatch/OperatorsRpcDispatcher$RpcEntry;of(Lnet/minecraft/server/OperatorEntry;)Lnet/minecraft/server/dedicated/management/dispatch/OperatorsRpcDispatcher$RpcEntry;
 *   Lnet/minecraft/server/dedicated/management/RpcPlayer;of(Lnet/minecraft/server/PlayerConfigEntry;)Lnet/minecraft/server/dedicated/management/RpcPlayer;
 *   Lnet/minecraft/server/dedicated/management/dispatch/IpBansRpcDispatcher$IpBanData;fromBannedIpEntry(Lnet/minecraft/server/BannedIpEntry;)Lnet/minecraft/server/dedicated/management/dispatch/IpBansRpcDispatcher$IpBanData;
 *   Lnet/minecraft/server/dedicated/management/dispatch/PlayerBansRpcDispatcher$RpcEntry;of(Lnet/minecraft/server/BannedPlayerEntry;)Lnet/minecraft/server/dedicated/management/dispatch/PlayerBansRpcDispatcher$RpcEntry;
 *   Lnet/minecraft/server/dedicated/management/dispatch/GameRuleRpcDispatcher;toTypedRule(Lnet/minecraft/server/dedicated/management/dispatch/ManagementHandlerDispatcher;Ljava/lang/String;Lnet/minecraft/world/GameRules$Rule;)Lnet/minecraft/server/dedicated/management/dispatch/GameRuleRpcDispatcher$TypedRule;
 *   Lnet/minecraft/server/dedicated/management/dispatch/ServerRpcDispatcher;status(Lnet/minecraft/server/dedicated/management/dispatch/ManagementHandlerDispatcher;)Lnet/minecraft/server/dedicated/management/dispatch/ServerRpcDispatcher$RpcStatus;
 *   Lnet/minecraft/server/dedicated/management/ManagementServer;forEachConnection(Ljava/util/function/Consumer;)V
 *   Lnet/minecraft/server/dedicated/management/network/ManagementConnectionHandler;sendNotification(Lnet/minecraft/registry/entry/RegistryEntry$Reference;Ljava/lang/Object;)V
 *   Lnet/minecraft/server/dedicated/management/network/ManagementConnectionHandler;sendNotification(Lnet/minecraft/registry/entry/RegistryEntry$Reference;)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/server/dedicated/management/listener/NotificationManagementListener;notifyAll(Lnet/minecraft/registry/entry/RegistryEntry$Reference;Ljava/lang/Object;)V
 *   Lnet/minecraft/server/dedicated/management/listener/NotificationManagementListener;notifyAll(Lnet/minecraft/registry/entry/RegistryEntry$Reference;)V
 */
package net.minecraft.server.dedicated.management.listener;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.BannedIpEntry;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.dedicated.management.ManagementServer;
import net.minecraft.server.dedicated.management.OutgoingRpcMethod;
import net.minecraft.server.dedicated.management.OutgoingRpcMethods;
import net.minecraft.server.dedicated.management.RpcPlayer;
import net.minecraft.server.dedicated.management.dispatch.GameRuleRpcDispatcher;
import net.minecraft.server.dedicated.management.dispatch.IpBansRpcDispatcher;
import net.minecraft.server.dedicated.management.dispatch.ManagementHandlerDispatcher;
import net.minecraft.server.dedicated.management.dispatch.OperatorsRpcDispatcher;
import net.minecraft.server.dedicated.management.dispatch.PlayerBansRpcDispatcher;
import net.minecraft.server.dedicated.management.dispatch.ServerRpcDispatcher;
import net.minecraft.server.dedicated.management.listener.ManagementListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;

public class NotificationManagementListener
implements ManagementListener {
    private final ManagementServer managementServer;
    private final ManagementHandlerDispatcher dispatcher;

    public NotificationManagementListener(ManagementHandlerDispatcher dispatcher, ManagementServer managementServer) {
        this.dispatcher = dispatcher;
        this.managementServer = managementServer;
    }

    @Override
    public void onPlayerJoined(ServerPlayerEntity player) {
        this.notifyAll(OutgoingRpcMethods.PLAYER_JOINED, RpcPlayer.of(player));
    }

    @Override
    public void onPlayerLeft(ServerPlayerEntity player) {
        this.notifyAll(OutgoingRpcMethods.PLAYER_LEFT, RpcPlayer.of(player));
    }

    @Override
    public void onServerStarted() {
        this.notifyAll(OutgoingRpcMethods.SERVER_STARTED);
    }

    @Override
    public void onServerStopping() {
        this.notifyAll(OutgoingRpcMethods.SERVER_STOPPING);
    }

    @Override
    public void onServerSaving() {
        this.notifyAll(OutgoingRpcMethods.SERVER_SAVING);
    }

    @Override
    public void onServerSaved() {
        this.notifyAll(OutgoingRpcMethods.SERVER_SAVED);
    }

    @Override
    public void onOperatorAdded(OperatorEntry operator) {
        this.notifyAll(OutgoingRpcMethods.OPERATOR_ADDED, OperatorsRpcDispatcher.RpcEntry.of(operator));
    }

    @Override
    public void onOperatorRemoved(OperatorEntry operator) {
        this.notifyAll(OutgoingRpcMethods.OPERATOR_REMOVED, OperatorsRpcDispatcher.RpcEntry.of(operator));
    }

    @Override
    public void onAllowlistAdded(PlayerConfigEntry player) {
        this.notifyAll(OutgoingRpcMethods.ALLOWLIST_ADDED, RpcPlayer.of(player));
    }

    @Override
    public void onAllowlistRemoved(PlayerConfigEntry player) {
        this.notifyAll(OutgoingRpcMethods.ALLOWLIST_REMOVED, RpcPlayer.of(player));
    }

    @Override
    public void onIpBanAdded(BannedIpEntry entry) {
        this.notifyAll(OutgoingRpcMethods.IP_BAN_ADDED, IpBansRpcDispatcher.IpBanData.fromBannedIpEntry(entry));
    }

    @Override
    public void onIpBanRemoved(String string) {
        this.notifyAll(OutgoingRpcMethods.IP_BAN_REMOVED, string);
    }

    @Override
    public void onBanAdded(BannedPlayerEntry entry) {
        this.notifyAll(OutgoingRpcMethods.BAN_ADDED, PlayerBansRpcDispatcher.RpcEntry.of(entry));
    }

    @Override
    public void onBanRemoved(PlayerConfigEntry player) {
        this.notifyAll(OutgoingRpcMethods.BAN_REMOVED, RpcPlayer.of(player));
    }

    @Override
    public void onGameRuleUpdated(String gameRuleKey, GameRules.Rule<?> gameRule) {
        this.notifyAll(OutgoingRpcMethods.GAMERULE_UPDATED, GameRuleRpcDispatcher.toTypedRule(this.dispatcher, gameRuleKey, gameRule));
    }

    @Override
    public void onServerStatusHeartbeat() {
        this.notifyAll(OutgoingRpcMethods.SERVER_STATUS_HEARTBEAT, ServerRpcDispatcher.status(this.dispatcher));
    }

    private void notifyAll(RegistryEntry.Reference<? extends OutgoingRpcMethod<Void, ?>> method) {
        this.managementServer.forEachConnection(connection -> connection.sendNotification(method));
    }

    private <Params> void notifyAll(RegistryEntry.Reference<? extends OutgoingRpcMethod<Params, ?>> method, Params params) {
        this.managementServer.forEachConnection(connection -> connection.sendNotification(method, params));
    }
}

