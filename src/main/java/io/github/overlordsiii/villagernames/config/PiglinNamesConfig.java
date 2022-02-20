package io.github.overlordsiii.villagernames.config;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "piglinNames")
public class PiglinNamesConfig implements NamesConfig, ConfigData {
	public List<String> piglinNames = new ArrayList<>();

	@Override
	public String getConfigName() {
		return "piglinNames";
	}

	@Override
	public List<String> getNameList() {
		return piglinNames;
	}
}
