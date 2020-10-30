package io.github.overlordsiii.villagernames.config;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@SuppressWarnings("unused")
public enum FormattingDummy {
    BLACK(Formatting.BLACK),
    DARK_BLUE(Formatting.DARK_BLUE),
    DARK_GREEN(Formatting.DARK_GREEN),
    DARK_AQUA(Formatting.DARK_AQUA),
    DARK_RED(Formatting.DARK_RED),
    DARK_PURPLE(Formatting.DARK_PURPLE),
    GOLD(Formatting.GOLD),
    GRAY(Formatting.GRAY),
    DARK_GRAY(Formatting.DARK_GRAY),
    BLUE(Formatting.BLUE),
    GREEN(Formatting.GREEN),
    AQUA(Formatting.AQUA),
    RED(Formatting.RED),
    LIGHT_PURPLE(Formatting.LIGHT_PURPLE),
    YELLOW(Formatting.YELLOW),
    WHITE(Formatting.WHITE),
    OBFUSCATED(Formatting.OBFUSCATED),
    BOLD(Formatting.BOLD),
    STRIKETHROUGH(Formatting.STRIKETHROUGH),
    UNDERLINE(Formatting.UNDERLINE),
    ITALIC(Formatting.ITALIC),
    RESET(Formatting.RESET);

    private Formatting formatting;

    FormattingDummy(Formatting formatting){
        this.formatting = formatting;
    }

    public String toString(){
        return formatting.getName().toUpperCase(Locale.ROOT);
    }

    public Formatting getFormatting(){
        return this.formatting;
    }

    public static FormattingDummy fromFormatting(Formatting formatting){
        for (FormattingDummy dummy : FormattingDummy.values()){
            if (dummy.getFormatting().equals(formatting)){
                return dummy;
            }
        }
        return FormattingDummy.WHITE;
    }
}
