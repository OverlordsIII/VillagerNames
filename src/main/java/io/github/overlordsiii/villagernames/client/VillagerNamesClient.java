package io.github.overlordsiii.villagernames.client;

import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.config.client.WarningMessageGuiTransformer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;

import net.fabricmc.api.ClientModInitializer;

public class VillagerNamesClient implements ClientModInitializer {

	/**
	 * Runs the mod initializer on the client environment.
	 */
	@Override
	public void onInitializeClient() {
		GuiRegistry registry = AutoConfig.getGuiRegistry(VillagerConfig.class);

		registry.registerAnnotationTransformer(new WarningMessageGuiTransformer(), ConfigEntry.Gui.CollapsibleObject.class);
	}
}
