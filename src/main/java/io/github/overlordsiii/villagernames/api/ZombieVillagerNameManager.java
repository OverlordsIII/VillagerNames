package io.github.overlordsiii.villagernames.api;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;

/**
 * Same as {@link VillagerNameManager but for Zombie Villagers}
 * Has more limited functionality
 */
public interface ZombieVillagerNameManager {

	void setFirstName(String name);

	void setLastName(String name);

	String getFirstName();

	String getLastName();

	void updateFullName();

	String getFullName();

	void setPlayerName(String name);

	String getPlayerName();

	static String getPlayerName(ZombieVillagerEntity entity) {
		return ((ZombieVillagerNameManager)entity).getPlayerName();
	}


	static void setFirstName(String name, ZombieVillagerEntity entity) {
		((ZombieVillagerNameManager)entity).setFirstName(name);
	}

	static void setLastName(String name, ZombieVillagerEntity entity) {
		((ZombieVillagerNameManager)entity).setLastName(name);
	}

	static String getFirstName(ZombieVillagerEntity entity) {
		return ((ZombieVillagerNameManager)entity).getFirstName();
	}

	static String getLastName(ZombieVillagerEntity entity) {
		return ((ZombieVillagerNameManager)entity).getLastName();
	}

	static void updateLastName(ZombieVillagerEntity entity) {
		((ZombieVillagerNameManager)entity).updateFullName();
	}

	static String getFullName(ZombieVillagerEntity entity) {
		return ((ZombieVillagerNameManager)entity).getFullName();
	}

	static String getFullNameWithZombie(ZombieVillagerEntity entity) {
		return ((ZombieVillagerNameManager)entity).getFullName() + " the Zombie";
	}

	static void setPlayerName(ZombieVillagerEntity entity, String name) {
		((ZombieVillagerNameManager)entity).setPlayerName(name);
	}

}
