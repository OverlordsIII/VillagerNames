package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasCustomName()Z"))
	public boolean turnOfConsole(LivingEntity instance) {
		return instance.hasCustomName() && !VillagerNames.CONFIG.villagerGeneralConfig.turnOffVillagerConsoleSpam;
	}
}
