package io.github.overlordsiii.villagernames.mixin.illager;

import io.github.overlordsiii.villagernames.api.RaiderNameManager;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.village.raid.Raid;

@Mixin(Raid.class)
public abstract class RaidMixin {

	@Inject(method = "setWaveCaptain", at = @At("TAIL"))
	private void setRaidCaptainRole(int wave, RaiderEntity entity, CallbackInfo ci) {
		VillagerUtil.createIllagerNames(entity);
		RaiderNameManager.setTitle(entity, "Raid Captain");
		entity.setCustomName(RaiderNameManager.getFullNameAsText(entity, true));
	}

}
