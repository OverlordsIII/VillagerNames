package io.github.overlordsiii.villagernames.config;

import java.util.Random;

import io.github.overlordsiii.villagernames.region.api.RegionGroup;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "villagerRules")
public class VillagerGeneralConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean professionNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean golemNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean needsOP = true;
    @ConfigEntry.Gui.Excluded
    public boolean hasRead = false;
    @ConfigEntry.Gui.Tooltip
    public String nitwitText = "Nitwit";
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Tooltip
    public FormattingDummy villagerTextFormatting = FormattingDummy.WHITE;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Tooltip
    public RegionNames regionNames = RegionNames.DEFAULT;

    @ConfigEntry.Gui.Tooltip
    public boolean childNames = false;
    @ConfigEntry.Gui.Tooltip
    public boolean turnOffVillagerConsoleSpam = false;
    @ConfigEntry.Gui.Tooltip
    public boolean wanderingTraderNames = true;
    @ConfigEntry.Gui.Tooltip
    public String wanderingTraderText = "Wandering Trader";
    @ConfigEntry.Gui.Tooltip
    public boolean surNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean reverseLastNames = false;
    @ConfigEntry.Gui.Tooltip
    public boolean nameTagNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean piglinNames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean piglinSurnames = true;
    @ConfigEntry.Gui.Tooltip
    public boolean illagerEntityNames = true;

    @ConfigEntry.Gui.Tooltip
    public boolean catNames = true;
}
