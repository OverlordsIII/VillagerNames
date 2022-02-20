package io.github.overlordsiii.villagernames.api;

import io.github.overlordsiii.villagernames.VillagerNames;

import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public interface PiglinNameManager {

	void setFirstName(String name);

	void setLastName(String name);

	String getFirstName();

	String getLastName();

	void updateFullName();

	String getFullName();

	static void setFirstName(String name, PiglinEntity entity) {
		((PiglinNameManager)entity).setFirstName(name);
	}

	static void setLastName(String name, PiglinEntity entity) {
		((PiglinNameManager)entity).setLastName(name);
	}

	static String getFirstName(PiglinEntity entity) {
		return ((PiglinNameManager)entity).getFirstName();
	}

	static String getLastName(PiglinEntity entity) {
		return ((PiglinNameManager)entity).getLastName();
	}

	static void updateLastName(PiglinEntity entity) {
		((PiglinNameManager)entity).updateFullName();
	}

	static String getFullName(PiglinEntity entity) {
		return ((PiglinNameManager)entity).getFullName();
	}

	static Text getFullNameAsText(PiglinEntity entity, boolean configFormatting) {
		return configFormatting ? new LiteralText(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(getFullName(entity));
	}
}
