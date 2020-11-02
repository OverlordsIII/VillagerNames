package io.github.overlordsiii.villagernames.command.argument;


import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.overlordsiii.villagernames.config.FormattingDummy;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class FormattingArgumentType implements ArgumentType<Formatting> {
    private static final Collection<String> EXAMPLES = Util.make(new ArrayList<>(), list -> {
        for (FormattingDummy formatting : FormattingDummy.values()){
            list.add(formatting.toString().toUpperCase(Locale.ROOT));
        }
    });
    public static FormattingArgumentType format(){
        return new FormattingArgumentType();
    }
    public static Formatting getFormat(CommandContext<ServerCommandSource> ctx, String name){
        return ctx.getArgument(name, Formatting.class);
    }
    @Override
    public Formatting parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor();
        if (!reader.canRead()) {
            reader.skip();
        }
        while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
        }
       String substring = reader.getString().substring(argBeginning, reader.getCursor());
        try {
            return Formatting.valueOf(substring);
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new LiteralText(ex.getMessage())).createWithContext(reader);
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
