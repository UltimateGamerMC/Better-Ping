package com.vladmarica.betterpingdisplay.hud;

import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.Config;
import com.vladmarica.betterpingdisplay.mixin.PlayerListHudInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;

public final class CustomPlayerListHud {
  private static final int PING_BAR_OFFSET = 11;
  private static final Config config = BetterPingDisplayMod.instance().getConfig();

  public static void renderPingDisplay(
      MinecraftClient client, PlayerListHud hud, DrawContext context, int width, int x, int y, PlayerListEntry player) {
    String pingString = String.format(config.getTextFormatString(), player.getLatency());
    int pingStringWidth = client.textRenderer.getWidth(pingString);
    int pingTextColor = config.shouldAutoColorPingText()
        ? PingColors.getColor(player.getLatency()) : config.getTextColor();

    int textX = x + width - PING_BAR_OFFSET - pingStringWidth - 2;

    context.drawTextWithShadow(client.textRenderer, pingString, textX, y, pingTextColor);

    if (config.shouldRenderPingBars()) {
      ((PlayerListHudInvoker) hud).invokeRenderLatencyIcon(context, width, x, y, player);
    }
  }
}
