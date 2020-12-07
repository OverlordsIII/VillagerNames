package io.github.overlordsiii.villagernames.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "sureNames")
public class SureNamesConfig implements ConfigData, NamesConfig {

    public List<String> sureNames = new ArrayList<>();

    @Override
    public String getConfigName() {
        return "surnames";
    }

    @Override
    public List<String> getNameList() {
        return sureNames;
    }
}
