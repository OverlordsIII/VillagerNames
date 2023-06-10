package io.github.overlordsiii.villagernames.config.names;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "sureNames")
public class SureNamesConfig implements ConfigData, NamesConfig {

    public List<String> sureNames = new ArrayList<>();

	@Override
    public List<String> getNameList() {
        return sureNames;
    }
}
