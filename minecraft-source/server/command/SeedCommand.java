/*
 * External method calls:
 *   Lnet/minecraft/server/command/CommandManager;literal(Ljava/lang/String;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
 *   Lnet/minecraft/server/command/CommandManager;requirePermissionLevel(I)Lnet/minecraft/command/PermissionLevelPredicate;
 *   Lnet/minecraft/text/Texts;bracketedCopyable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *   Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/MutableText;
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;

public class SeedCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("seed").requires(CommandManager.requirePermissionLevel(dedicated ? 2 : 0))).executes(context -> {
            long l = ((ServerCommandSource)context.getSource()).getWorld().getSeed();
            MutableText lv = Texts.bracketedCopyable(String.valueOf(l));
            ((ServerCommandSource)context.getSource()).sendFeedback(() -> Text.translatable("commands.seed.success", lv), false);
            return (int)l;
        }));
    }
}

