package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.ai.brain.task.VillagerBreedTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Optional;

@Mixin(VillagerBreedTask.class)
public abstract class VillagerBreedTaskMixin {
    @Inject(method = "createChild",
            at = @At("TAIL"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onCreateChild(ServerWorld serverWorld, VillagerEntity villagerEntity, VillagerEntity villagerEntity2, CallbackInfoReturnable<Optional> cir, VillagerEntity villagerEntity3) {
        VillagerUtil.addLastNameFromBreeding(villagerEntity3, villagerEntity);
        System.out.println("villager name for villagerEntity (parent) = " + Objects.requireNonNull(villagerEntity.getCustomName()).asString());
        System.out.println("villager name for villagerEntity3 (child) = " + Objects.requireNonNull(villagerEntity3.getCustomName()).asString());
    }
}
