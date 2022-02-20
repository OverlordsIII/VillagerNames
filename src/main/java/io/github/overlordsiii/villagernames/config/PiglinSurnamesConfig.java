package io.github.overlordsiii.villagernames.config;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "piglinSurnames")
public class PiglinSurnamesConfig implements NamesConfig, ConfigData {

	public List<String> piglinSurnames = new ArrayList<>();

	@Override
	public String getConfigName() {
		return "piglinSurnames";
	}

	@Override
	public List<String> getNameList() {
		return piglinSurnames;
	}
}
