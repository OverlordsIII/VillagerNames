package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.VillagerNames;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public interface RaiderNameManager extends DefaultNameManager {
	/**
	 * Gets the illager entity's title
	 * @return the title, or null if the title is toggled off
	 */
	String getTitle();

	/**
	 * Removes the title of the entity if present
	 */
	void removeTitle();

	/**
	 * Sets the title for the illager
	 * @param title the name of the title
	 */

	void setTitle(String title);

	String getDefaultTitle();


	static String getPlayerName(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getPlayerName();
	}

	static void setFirstName(RaiderEntity entity, String firstName) {
		((RaiderNameManager)entity).setFirstName(firstName);
	}

	static String getFirstName(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getFirstName();
	}

	static void setLastName(RaiderEntity entity, String lastName) {
		((RaiderNameManager)entity).setLastName(lastName);
	}

	static String getLastName(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getLastName();
	}

	static String getTitle(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getTitle();
	}

	static void removeTitle(RaiderEntity entity) {
		((RaiderNameManager)entity).removeTitle();
	}

	static void setPlayerName(RaiderEntity entity, String name) {
		((RaiderNameManager)entity).setPlayerName(name);
	}

	static void setTitle(RaiderEntity entity, String title) {
		((RaiderNameManager)entity).setTitle(title);
	}

	static String getFullName(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getFullName();
	}

	static void updateFullName(RaiderEntity entity) {
		((RaiderNameManager)entity).updateFullName();
	}

	static Text getFullNameAsText(RaiderEntity entity, boolean configFormatting) {
		return configFormatting ? new LiteralText(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(getFullName(entity));
	}

	static String getDefaultTitle(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getDefaultTitle();
	}

}
