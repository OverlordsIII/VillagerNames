package io.github.overlordsiii.villagernames.config.client;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.autoconfig.gui.registry.api.GuiTransformer;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;

import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class WarningMessageGuiTransformer implements GuiTransformer {

	@Override
	public List<AbstractConfigListEntry> transform(List<AbstractConfigListEntry> list, String s, Field field, Object o, Object o1, GuiRegistryAccess guiRegistryAccess) {
		List<AbstractConfigListEntry> newList = new ArrayList<>(list);

		TextListEntry entry = ConfigEntryBuilder
			.create()
			.startTextDescription(new TranslatableText("text.autoconfig.VillagerNames.warning")
				.formatted(Formatting.RED))
			.build();

		newList.add(entry);
		return newList;
	}
}
