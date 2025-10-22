/*
 * External method calls:
 *   Lnet/minecraft/command/MacroInvocation;parse(Ljava/lang/String;)Lnet/minecraft/command/MacroInvocation;
 *   Lnet/minecraft/command/MacroInvocation;variables()Ljava/util/List;
 *   Lnet/minecraft/command/MacroInvocation;apply(Ljava/util/List;)Ljava/lang/String;
 */
package net.minecraft.dialog.action;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Map;
import net.minecraft.command.MacroInvocation;

public class ParsedTemplate {
    public static final Codec<ParsedTemplate> CODEC = Codec.STRING.comapFlatMap(ParsedTemplate::parse, parsedTemplate -> parsedTemplate.raw);
    public static final Codec<String> NAME_CODEC = Codec.STRING.validate(name -> MacroInvocation.isValidMacroName(name) ? DataResult.success(name) : DataResult.error(() -> name + " is not a valid input name"));
    private final String raw;
    private final MacroInvocation parsed;

    private ParsedTemplate(String raw, MacroInvocation parsed) {
        this.raw = raw;
        this.parsed = parsed;
    }

    private static DataResult<ParsedTemplate> parse(String raw) {
        MacroInvocation lv;
        try {
            lv = MacroInvocation.parse(raw);
        } catch (Exception exception) {
            return DataResult.error(() -> "Failed to parse template " + raw + ": " + exception.getMessage());
        }
        return DataResult.success(new ParsedTemplate(raw, lv));
    }

    public String apply(Map<String, String> args) {
        List<String> list = this.parsed.variables().stream().map(variable -> args.getOrDefault(variable, "")).toList();
        return this.parsed.apply(list);
    }
}

