package io.github.overlordsiii.villagernames.config.names;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "catNames")
public class CatNamesConfig implements NamesConfig, ConfigData {

	public List<String> catNames = new ArrayList<>();

	/**
	 * @return
	 */
	@Override
	public String getConfigName() {
		return "catNames";
	}

	/**
	 * @return
	 */
	@Override
	public List<String> getNameList() {
		return catNames;
	}
}
