package io.github.overlordsiii.villagernames.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.overlordsiii.villagernames.config.FormattingDummy;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class FormattingSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        String string = builder.getRemaining();
        sortFormattingByString(string).forEach(builder::suggest);
        return builder.buildFuture();
    }
    private ArrayList<String> sortFormattingByString(String currentArg){
        ArrayList<String> suggestionsBasedOnCurrentArg = new ArrayList<>();
        for (FormattingDummy formatting : FormattingDummy.values()) {
            if (formatting.toString().indexOf(currentArg) == 0){
                suggestionsBasedOnCurrentArg.add(formatting.toString());
            }
        }
        return suggestionsBasedOnCurrentArg;
    }
}
