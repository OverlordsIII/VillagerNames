package io.github.overlordsiii.villagernames.config.names;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "golemNames")
public class GolemNamesConfig implements ConfigData, NamesConfig {
    public List<String> golemNames = new ArrayList<>();

	@Override
    public List<String> getNameList() {
        return golemNames;
    }
}
