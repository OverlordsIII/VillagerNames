package io.github.overlordsiii.villagernames.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.overlordsiii.villagernames.VillagerNames;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class VillagerNameSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        String string = builder.getRemaining();
        sortVillagerNamesByString(string).forEach(builder::suggest);
        return builder.buildFuture();
    }
    private ArrayList<String> sortVillagerNamesByString(String currentArg){
        ArrayList<String> suggestionsBasedOnCurrentArg = new ArrayList<>();
        VillagerNames.CONFIG.villagerNamesConfig.villagerNames.forEach((string) -> {
            if (string.indexOf(currentArg) == 0){
                suggestionsBasedOnCurrentArg.add(string);
            }
        });
        return suggestionsBasedOnCurrentArg;
    }
}
