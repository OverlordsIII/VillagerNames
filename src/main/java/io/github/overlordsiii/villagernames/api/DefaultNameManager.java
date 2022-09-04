package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.mixin.VillagerEntityMixin;

public interface DefaultNameManager {
	/**
	 * Set entity's first name
	 * @param firstName first name of villager
	 */
	void setFirstName(String firstName);

	/**
	 * Get first name of entity
	 * @return first name
	 */
	String getFirstName();

	/**
	 * Set entity's last name
	 * @param lastNames entity last name
	 */
	void setLastName(String lastNames);

	/**
	 * Get last name currently used
	 * @return entity's last name
	 */
	String getLastName();

	/**
	 * Returns the full name of the entity
	 * @return the fullName
	 */
	String getFullName();

	/**
	 * Updates the full name. This should be called when any other method in this interface is referenced
	 *
	 * It is by default called internally by the method implementation
	 *
	 */
	void updateFullName();

	/**
	 * Allows for the player to set a manual override for the full name.
	 *
	 * Whatever the player name is set to, it will supercede any other name
	 * @param name name player has set
	 */

	void setPlayerName(String name);

	/**
	 * Get the player name that the user has set when using a nametag
	 * @return player name, possibly null
	 */

	String getPlayerName();

}
