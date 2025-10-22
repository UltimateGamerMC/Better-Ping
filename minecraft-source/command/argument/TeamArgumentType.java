/*
 * External method calls:
 *   Lnet/minecraft/command/CommandSource;suggestMatching(Ljava/lang/Iterable;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;
 *   Lnet/minecraft/text/Text;stringifiedTranslatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/MutableText;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/command/argument/TeamArgumentType;parse(Lcom/mojang/brigadier/StringReader;)Ljava/lang/String;
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TeamArgumentType
implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "123");
    private static final DynamicCommandExceptionType UNKNOWN_TEAM_EXCEPTION = new DynamicCommandExceptionType(name -> Text.stringifiedTranslatable("team.notFound", name));

    public static TeamArgumentType team() {
        return new TeamArgumentType();
    }

    public static Team getTeam(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        String string2 = context.getArgument(name, String.class);
        ServerScoreboard lv = context.getSource().getServer().getScoreboard();
        Team lv2 = lv.getTeam(string2);
        if (lv2 == null) {
            throw UNKNOWN_TEAM_EXCEPTION.create(string2);
        }
        return lv2;
    }

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSource) {
            return CommandSource.suggestMatching(((CommandSource)context.getSource()).getTeamNames(), builder);
        }
        return Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public /* synthetic */ Object parse(StringReader reader) throws CommandSyntaxException {
        return this.parse(reader);
    }
}

