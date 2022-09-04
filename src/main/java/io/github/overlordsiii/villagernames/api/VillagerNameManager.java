package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.mixin.VillagerEntityMixin;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;

/**
 * Used to manage names on a single {@link net.minecraft.entity.passive.VillagerEntity}
 * Use via duck mixin on {@link net.minecraft.entity.passive.VillagerEntity}
 */
public interface VillagerNameManager extends DefaultNameManager {
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

	static String getPlayerName(VillagerEntity entity) {
		return ((VillagerNameManager)entity).getPlayerName();
	}

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
		return configFormatting ? Text.literal(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : Text.literal(getFullName(entity));
	}

	static void setPlayerName(VillagerEntity entity, String name) {
		((VillagerNameManager)entity).setPlayerName(name);
	}
}
