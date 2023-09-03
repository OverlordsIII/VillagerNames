package io.github.overlordsiii.villagernames.integration.guardvillagers;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import dev.mrsterner.guardvillagers.common.entity.GuardEntity;
import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.api.DefaultNameManager;
import io.github.overlordsiii.villagernames.util.VillagerUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;

public class GuardVillagersIntegration {

	public static void createGuardVillagerNames(Entity entity) {
		if (entity instanceof GuardEntity guardEntity) {
			if (!entity.hasCustomName()) {
				setGuardFirstName(guardEntity, VillagerUtil.pickRandomName(CONFIG.villagerNamesConfig));
				if (CONFIG.villagerGeneralConfig.surNames) {
					setGuardLastName(guardEntity, VillagerUtil.pickRandomName(CONFIG.sureNamesConfig));
				}
				entity.setCustomName(getFullNameAsText(guardEntity, true));
			}
			entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
		}
	}

	private static void setGuardFirstName(GuardEntity entity, String name) {
		((DefaultNameManager) entity).setFirstName(name);
	}

	private static void setGuardLastName(GuardEntity entity, String lastName) {
		((DefaultNameManager) entity).setLastName(lastName);
	}

	private static String getFullName(GuardEntity entity) {
		return ((DefaultNameManager) entity).getFullName();
	}

	private static Text getFullNameAsText(GuardEntity entity, boolean configFormatting) {
		return configFormatting ? Text.literal(getFullName(entity)).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : Text.literal(getFullName(entity));
	}


}
