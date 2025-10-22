/*
 * External method calls:
 *   Lnet/minecraft/util/packrat/Parser;parse(Lcom/mojang/brigadier/StringReader;)Ljava/lang/Object;
 *   Lnet/minecraft/util/packrat/Parser;listSuggestions(Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.util.packrat.Parser;

public abstract class ParserBackedArgumentType<T>
implements ArgumentType<T> {
    private final Parser<T> parser;

    public ParserBackedArgumentType(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        return this.parser.parse(reader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return this.parser.listSuggestions(builder);
    }
}

