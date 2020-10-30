package io.github.overlordsiii.villagernames.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public class FormattingArgumentType implements ArgumentType<Formatting> {
    public static FormattingArgumentType format(){
        return new FormattingArgumentType();
    }
    public static Formatting getFormat(CommandContext<ServerCommandSource> ctx, String name){
        return ctx.getArgument(name, Formatting.class);
    }
    @Override
    public Formatting parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor(); // The starting position of the cursor is at the beginning of the argument.
        if (!reader.canRead()) {
            reader.skip();
        }

        // Now we check the contents of the argument till either we hit the end of the command line (When canRead becomes false)
        // Otherwise we go till reach reach a space, which signifies the next argument
        while (reader.canRead() && reader.peek() != ' ') { // peek provides the character at the current cursor position.
            reader.skip(); // Tells the StringReader to move it's cursor to the next position.
        }

        // Now we substring the specific part we want to see using the starting cursor position and the ends where the next argument starts.
        String substring = reader.getString().substring(argBeginning, reader.getCursor());
        try {
            return Formatting.valueOf(substring);
        } catch (Exception ex) {
            // UUIDs can throw an exception when made by a string, so we catch the exception and repackage it into a CommandSyntaxException type.
            // Create with context tells Brigadier to supply some context to tell the user where the command failed at.
            // Though normal create method could be used.
            throw new SimpleCommandExceptionType(new LiteralText(ex.getMessage())).createWithContext(reader);
        }
    }
}
