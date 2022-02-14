package io.github.overlordsiii.villagernames.api;

import java.util.List;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.config.VillagerNamesConfig;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

/**
 * Used to manage names on a single {@link net.minecraft.entity.passive.VillagerEntity}
 * Use via duck mixin on {@link net.minecraft.entity.passive.VillagerEntity}
 */
public interface VillagerNameManager {
	/**
	 * Set villager's first name
	 * @param firstName first name of villager
	 */
	void setFirstName(String firstName);

	/**
	 * Get first name of villager
	 * @return first name
	 */
	String getFirstName();

	/**
	 * Set Villager's last name
	 * @param lastNames villager last name
	 */
	void setLastName(String lastNames);

	/**
	 * Get last name currently used
	 * @return villager's last name
	 */
	String getLastName();
	/**
	 * Gets the villager's profession name
	 * @return the profession name, or null if the profession name is toggled off
	 */
	String getProfessionName();

	/**
	 * Removes the profession name if present
	 */
	void removeProfessionName();

	/**
	 * Adds a new profession name to the villagers name
	 * @param appendedProfession the name of the profession
	 */

	void setProfessionName(String appendedProfession);

	/**
	 * Returns the full name of the villager
	 * @return the fullName
	 */
	String getFullName();

	/**
	 * Updates the full name. This should be called when any other method in this interface is referenced
	 *
	 * It is by default called internally by the method implementation
	 *
	 * @see VillagerEntityMixin for more information
	 *
	 */
	void updateFullName();

	static String getFirstName(VillagerEntity entity) {
		return ((VillagerNameManager)entity).getFirstName();
	}

	static String getLastName(VillagerEntity entity) {
		return ((VillagerNameManager)entity).getLastName();
	}

	static void setFirstName(VillagerEntity entity, String name) {
		((VillagerNameManager)entity).setFirstName(name);
	}

	static void setLastName(VillagerEntity entity, String name) {
		((VillagerNameManager)entity).setLastName(name);
	}

	static String getProfessionName(VillagerEntity entity) {
		return ((VillagerNameManager)entity).getProfessionName();
	}

	static void removeProfessionName(VillagerEntity entity) {
		((VillagerNameManager)entity).removeProfessionName();
	}

	static void setProfessionName(String professionName, VillagerEntity entity) {
		((VillagerNameManager)entity).setProfessionName(professionName);
	}

	static String getFullName(VillagerEntity entity) {
		return ((VillagerNameManager)entity).getFullName();
	}

	static void updateFullName(VillagerEntity entity) {
		((VillagerNameManager)entity).updateFullName();
	}

	static Text getFullNameAsText(VillagerEntity entity, boolean configFormatting) {
		return configFormatting ? new LiteralText(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(getFullName(entity));
	}
}
