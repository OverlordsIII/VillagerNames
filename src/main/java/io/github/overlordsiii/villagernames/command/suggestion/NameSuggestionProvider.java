package io.github.overlordsiii.villagernames.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.config.NamesConfig;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public abstract class NameSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    public static class Villager extends NameSuggestionProvider {
        public Villager() {
            this.namesConfig = VillagerNames.CONFIG.villagerNamesConfig;
        }
    }

    public static class Golem extends NameSuggestionProvider {
        public Golem() {
            this.namesConfig = VillagerNames.CONFIG.golemNamesConfig;
        }
    }

    public static class Surename extends NameSuggestionProvider {
        public Surename() {
            this.namesConfig = VillagerNames.CONFIG.sureNamesConfig;
        }
    }

    protected NamesConfig namesConfig;

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        String string = builder.getRemaining();
        sortNamesByString(string).forEach(builder::suggest);
        return builder.buildFuture();
    }
    private ArrayList<String> sortNamesByString(String currentArg){
        ArrayList<String> suggestionsBasedOnCurrentArg = new ArrayList<>();
        namesConfig.getNameList().forEach((string) -> {
            if (string.indexOf(currentArg) == 0){
                suggestionsBasedOnCurrentArg.add(string);
            }
        });
        return suggestionsBasedOnCurrentArg;
    }
}
