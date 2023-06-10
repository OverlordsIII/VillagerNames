package io.github.overlordsiii.villagernames.config.names;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "villagerNames")
public class VillagerNamesConfig implements ConfigData, NamesConfig {
    public List<String> villagerNames = new ArrayList<>();

	@Override
    public List<String> getNameList() {
        return villagerNames;
    }
}
