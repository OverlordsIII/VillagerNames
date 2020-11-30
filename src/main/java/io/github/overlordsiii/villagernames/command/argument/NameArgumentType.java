package io.github.overlordsiii.villagernames.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.config.NamesConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public abstract class NameArgumentType implements ArgumentType<String> {
    public static class Villager extends NameArgumentType {
        public Villager() {
            this.namesConfig = VillagerNames.CONFIG.villagerNamesConfig;
        }
    }

    public static class Golem extends NameArgumentType {
        public Golem() {
            this.namesConfig = VillagerNames.CONFIG.golemNamesConfig;
        }
    }

    public static NameArgumentType.Villager villagerName() {
        return new NameArgumentType.Villager();
    }

    public static NameArgumentType.Golem golemName() {
        return new NameArgumentType.Golem();
    }

    public static String getName(CommandContext<ServerCommandSource> ctx, String name) {
        return ctx.getArgument(name, String.class);
    }

    protected NamesConfig namesConfig;

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
            if (namesConfig.getNameList().contains(substring)){
                return substring;
            } else {
                throw new SimpleCommandExceptionType(new LiteralText("That name is not in the " + namesConfig.getConfigName() + " Config")).createWithContext(reader);
            }
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new LiteralText(ex.getMessage())).createWithContext(reader);
        }
    }
}
