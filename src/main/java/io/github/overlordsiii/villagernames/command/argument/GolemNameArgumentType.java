package io.github.overlordsiii.villagernames.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.overlordsiii.villagernames.VillagerNames;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class GolemNameArgumentType implements ArgumentType<String> {
    public static GolemNameArgumentType golemName(){
        return new GolemNameArgumentType();
    }

    public static String getGolemName(CommandContext<ServerCommandSource> ctx, String name){
        return ctx.getArgument(name, String.class);
    }
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor();
        if (!reader.canRead()) {
            reader.skip();
        }
        while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
        }
        String substring = reader.getString().substring(argBeginning, reader.getCursor()) + reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        try {
            if (VillagerNames.CONFIG.golemNamesConfig.golemNames.contains(substring)){
                return substring;
            } else {
                throw new SimpleCommandExceptionType(new LiteralText("That name is not in the GolemNames Config")).createWithContext(reader);
            }
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new LiteralText(ex.getMessage())).createWithContext(reader);
        }
    }
}
