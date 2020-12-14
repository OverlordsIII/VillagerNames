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
    public boolean needsOP = true;
    @ConfigEntry.Gui.Excluded
    public boolean hasRead = false;
    @ConfigEntry.Gui.Tooltip
    public String nitwitText = "Nitwit";
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Tooltip
    public FormattingDummy villagerTextFormatting = FormattingDummy.WHITE;
    @ConfigEntry.Gui.Tooltip
    public boolean childNames = false;
    @ConfigEntry.Gui.Tooltip
    public boolean turnOffVillagerConsoleSpam = false;
    @ConfigEntry.Gui.Tooltip
    public boolean wanderingTraderNames = true;
    @ConfigEntry.Gui.Tooltip
    public String wanderingTraderText = "Wandering Trader";
    @ConfigEntry.Gui.Tooltip
    public boolean surNames = false;
    /**
     * Will not work if {@link VillagerGeneralConfig#multipleLastNames} is enabled
     */
    @ConfigEntry.Gui.Tooltip
    public boolean reverseLastNames = false;
    @ConfigEntry.Gui.Tooltip
    public boolean multipleLastNames = false;
}
