/*
 * External method calls:
 *   Lnet/minecraft/server/command/CommandManager;literal(Ljava/lang/String;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
 *   Lnet/minecraft/server/command/CommandManager;requirePermissionLevel(I)Lnet/minecraft/command/PermissionLevelPredicate;
 *   Lnet/minecraft/server/command/CommandManager;argument(Ljava/lang/String;Lcom/mojang/brigadier/arguments/ArgumentType;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
 *   Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/MutableText;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/server/dedicated/command/SetIdleTimeoutCommand;execute(Lnet/minecraft/server/command/ServerCommandSource;I)I
 */
package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetIdleTimeoutCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("setidletimeout").requires(CommandManager.requirePermissionLevel(3))).then(CommandManager.argument("minutes", IntegerArgumentType.integer(0)).executes(context -> SetIdleTimeoutCommand.execute((ServerCommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "minutes")))));
    }

    private static int execute(ServerCommandSource source, int minutes) {
        source.getServer().setPlayerIdleTimeout(minutes);
        if (minutes > 0) {
            source.sendFeedback(() -> Text.translatable("commands.setidletimeout.success", minutes), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.setidletimeout.success.disabled"), true);
        }
        return minutes;
    }
}

