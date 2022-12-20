package io.github.overlordsiii.villagernames.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.config.names.NamesConfig;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public abstract class NameSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    public static class Villager extends NameSuggestionProvider {
        public Villager() {
            super(VillagerNames.CONFIG.villagerNamesConfig);
        }
    }

    public static class Golem extends NameSuggestionProvider {
        public Golem() {
            super(VillagerNames.CONFIG.golemNamesConfig);
        }
    }

    public static class Surename extends NameSuggestionProvider {
        public Surename() {
            super(VillagerNames.CONFIG.sureNamesConfig);
        }
    }

    public static class Piglin extends NameSuggestionProvider {
        public Piglin() {
            super(VillagerNames.CONFIG.piglinNamesConfig);
        }
    }

    public static class PiglinSurname extends NameSuggestionProvider {
        public PiglinSurname() {
            super(VillagerNames.CONFIG.piglinSurnamesConfig);
        }
    }

    public static class CatName extends NameSuggestionProvider {

        public CatName() {
            super(VillagerNames.CONFIG.catNamesConfig);
        }
    }

    protected NameSuggestionProvider(NamesConfig config) {
        this.namesConfig = config;
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
