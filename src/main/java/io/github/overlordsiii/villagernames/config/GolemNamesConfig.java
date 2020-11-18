package io.github.overlordsiii.villagernames.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "golemNames")
public class GolemNamesConfig implements ConfigData, NamesConfig {
    public List<String> golemNames = new ArrayList<>();

    @Override
    public String getConfigName() {
        return "golemNames";
    }

    @Override
    public List<String> getNameList() {
        return golemNames;
    }
}
