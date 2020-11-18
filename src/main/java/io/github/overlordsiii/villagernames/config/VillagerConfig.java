package io.github.overlordsiii.villagernames.config;

import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;

@Config(name = "VillagerNames")
@Config.Gui.Background("minecraft:textures/block/barrel_side.png")
@Config.Gui.CategoryBackground(category = "villagerNames", background = "minecraft:textures/block/emerald_block.png")
@Config.Gui.CategoryBackground(category = "golemName", background = "minecraft:textures/block/iron_block.png")
public class VillagerConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category(value = "villagerGeneral")
    @ConfigEntry.Gui.CollapsibleObject
    public final VillagerGeneralConfig villagerGeneralConfig = new VillagerGeneralConfig();

    @ConfigEntry.Category(value = "villagerNames")
    @ConfigEntry.Gui.CollapsibleObject
    public final VillagerNamesConfig villagerNamesConfig = new VillagerNamesConfig();

    @ConfigEntry.Category(value = "golemName")
    @ConfigEntry.Gui.CollapsibleObject
    public final GolemNamesConfig golemNamesConfig = new GolemNamesConfig();

}

