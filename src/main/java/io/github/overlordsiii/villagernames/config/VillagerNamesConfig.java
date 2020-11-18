package io.github.overlordsiii.villagernames.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "villagerNames")
public class VillagerNamesConfig implements ConfigData, NamesConfig {
    public List<String> villagerNames = new ArrayList<>();

    @Override
    public String getConfigName() {
        return "villagerNames";
    }

    @Override
    public List<String> getNameList() {
        return villagerNames;
    }
}
