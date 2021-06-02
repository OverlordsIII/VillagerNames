package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.ai.brain.task.VillagerBreedTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(VillagerBreedTask.class)
public abstract class VillagerBreedTaskMixin {
    @Inject(method = "createChild",
            at = @At("TAIL"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onCreateChild(ServerWorld serverWorld, VillagerEntity villagerEntity, VillagerEntity villagerEntity2, CallbackInfoReturnable<Optional<VillagerEntity>> cir, VillagerEntity villagerEntity3) {
        VillagerUtil.addLastNameFromBreeding(villagerEntity3, villagerEntity);
        if (VillagerNames.CONFIG.villagerGeneralConfig.surNames) {
            System.out.println("villager name for villagerEntity (parent) = " + valueOf(villagerEntity.getCustomName()));
            System.out.println("villager name for villagerEntity3 (child) = " + valueOf(villagerEntity.getCustomName()));
        }
    }

    private String valueOf(Text object) {
        return object == null ? "null" : object.asString();
    }


}
