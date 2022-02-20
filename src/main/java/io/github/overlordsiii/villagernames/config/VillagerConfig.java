package io.github.overlordsiii.villagernames.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "VillagerNames")
@Config.Gui.Background("minecraft:textures/block/barrel_side.png")
@Config.Gui.CategoryBackground(category = "villagerNames", background = "minecraft:textures/block/emerald_block.png")
@Config.Gui.CategoryBackground(category = "golemName", background = "minecraft:textures/block/iron_block.png")
@Config.Gui.CategoryBackground(category = "sureName", background = "minecraft:textures/block/jukebox_side.png")
@Config.Gui.CategoryBackground(category = "piglinName", background = "minecraft:textures/block/gold_block.png")
@Config.Gui.CategoryBackground(category = "piglinSurname", background = "minecraft:textures/block/quartz_block_side.png")
public class VillagerConfig extends PartitioningSerializer.GlobalData implements ConfigData {
    @ConfigEntry.Category(value = "villagerGeneral")
    @ConfigEntry.Gui.CollapsibleObject
    public final VillagerGeneralConfig villagerGeneralConfig = new VillagerGeneralConfig();

    @ConfigEntry.Category(value = "villagerNames")
    @ConfigEntry.Gui.CollapsibleObject
    public final VillagerNamesConfig villagerNamesConfig = new VillagerNamesConfig();

    @ConfigEntry.Category(value = "golemName")
    @ConfigEntry.Gui.CollapsibleObject
    public final GolemNamesConfig golemNamesConfig = new GolemNamesConfig();

    @ConfigEntry.Category(value = "sureName")
    @ConfigEntry.Gui.CollapsibleObject
    public final SureNamesConfig sureNamesConfig = new SureNamesConfig();

    @ConfigEntry.Category(value = "piglinName")
    @ConfigEntry.Gui.CollapsibleObject
    public final PiglinNamesConfig piglinNamesConfig = new PiglinNamesConfig();

    @ConfigEntry.Category(value = "piglinSurname")
    @ConfigEntry.Gui.CollapsibleObject
    public final PiglinSurnamesConfig piglinSurnamesConfig = new PiglinSurnamesConfig();

}

