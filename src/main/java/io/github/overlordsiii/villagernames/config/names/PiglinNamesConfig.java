package io.github.overlordsiii.villagernames.config.names;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "piglinNames")
public class PiglinNamesConfig implements NamesConfig, ConfigData {
	public List<String> piglinNames = new ArrayList<>();

	@Override
	public List<String> getNameList() {
		return piglinNames;
	}
}
