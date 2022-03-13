package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.VillagerNames;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public interface PiglinNameManager {

	void setFirstName(String name);

	void setLastName(String name);

	String getFirstName();

	String getLastName();

	void updateFullName();

	String getFullName();

	void setPlayerName(String name);

	static void setFirstName(String name, AbstractPiglinEntity entity) {
		((PiglinNameManager)entity).setFirstName(name);
	}

	static void setLastName(String name, AbstractPiglinEntity entity) {
		((PiglinNameManager)entity).setLastName(name);
	}

	static String getFirstName(AbstractPiglinEntity entity) {
		return ((PiglinNameManager)entity).getFirstName();
	}

	static String getLastName(AbstractPiglinEntity entity) {
		return ((PiglinNameManager)entity).getLastName();
	}

	static void updateLastName(AbstractPiglinEntity entity) {
		((PiglinNameManager)entity).updateFullName();
	}

	static String getFullName(AbstractPiglinEntity entity) {
		return ((PiglinNameManager)entity).getFullName();
	}

	static void setPlayerName(AbstractPiglinEntity entity, String name) {
		((PiglinNameManager)entity).setPlayerName(name);
	}

	static Text getFullNameAsText(AbstractPiglinEntity entity, boolean configFormatting) {
		return configFormatting ? new LiteralText(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(getFullName(entity));
	}

	default void debug() {
		//updateFullName();
		System.out.println("Piglin debug info");
		System.out.println("First name: " + getFirstName());
		System.out.println("Last Name: " + getLastName());
		System.out.println("Full Name: " + getFullName());
	}
}
