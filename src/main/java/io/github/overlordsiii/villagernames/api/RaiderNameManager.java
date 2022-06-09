package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.VillagerNames;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.text.Text;

public interface RaiderNameManager {
	/**
	 * Set pillager's first name
	 * @param firstName first name of illager entity
	 */
	void setFirstName(String firstName);

	/**
	 * Get first name of illager entity
	 * @return first name
	 */
	String getFirstName();

	/**
	 * Set illager entity's last name
	 * @param lastNames illager entity last name
	 */
	void setLastName(String lastNames);

	/**
	 * Get last name currently used
	 * @return illager entity's last name
	 */
	String getLastName();
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

	/**
	 * Returns the full name of the illager entity
	 * @return the fullName
	 */
	String getFullName();

	/**
	 * Updates the full name. This should be called when any other method in this interface is referenced
	 *
	 * It is by default called internally by the method implementation*
	 */
	void updateFullName();

	/**
	 * Allows for the player to set a manual override for the full name.
	 *
	 * Whatever the player name is set to, it will supercede any other name
	 * @param name
	 */

	void setPlayerName(String name);

	String getPlayerName();

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
		System.out.println("changing raider entity title: " + entity + " to " + title);
		((RaiderNameManager)entity).setTitle(title);
		System.out.println("new raider: " + entity);
	}

	static String getFullName(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getFullName();
	}

	static void updateFullName(RaiderEntity entity) {
		((RaiderNameManager)entity).updateFullName();
	}

	static Text getFullNameAsText(RaiderEntity entity, boolean configFormatting) {
		return configFormatting ? Text.literal(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : Text.literal(getFullName(entity));
	}

	static String getDefaultTitle(RaiderEntity entity) {
		return ((RaiderNameManager)entity).getDefaultTitle();
	}

}
