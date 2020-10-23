package io.github.overlordsiii.villagernames.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "villagerRules")
public class VillagerGeneralConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean professionNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean golemNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean needsOP = false;
    @ConfigEntry.Gui.Excluded
    public boolean hasRead = false;
}
