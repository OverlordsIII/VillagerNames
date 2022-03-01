package io.github.overlordsiii.villagernames.mixin.illager;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.api.RaiderNameManager;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.text.LiteralText;

@Mixin(PatrolEntity.class)
public abstract class PatrolEntityMixin {
	@Inject(method = "setPatrolLeader", at = @At("TAIL"))
	private void setPatrolLeaderTitle(boolean patrolLeader, CallbackInfo ci) {
		PatrolEntity entity = (PatrolEntity) (Object) this;
		if (entity instanceof RaiderNameManager manager && !(entity instanceof RavagerEntity)) {

			if (entity instanceof RaiderEntity raiderEntity) {

				VillagerUtil.createIllagerNames(raiderEntity);

				if (raiderEntity.hasActiveRaid()) {
					return;
				}
			}



			if (patrolLeader) {
				manager.setTitle("Patrol Leader");
			} else {
				manager.setTitle(manager.getDefaultTitle());
			}

			entity.setCustomName(new LiteralText(manager.getFullName()).formatted(VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
		}
	}
}
