package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow public abstract VillagerData getVillagerData();

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "setVillagerData", at = @At("TAIL"))
    private void changeText(VillagerData villagerData, CallbackInfo ci){
        if (villagerData.getProfession() != VillagerProfession.NONE && this.hasCustomName()) {
            VillagerUtil.updateVillagerNames((VillagerEntity) (Object) this);
        }
        if (this.hasCustomName() && villagerData.getProfession() == VillagerProfession.NONE){
            VillagerUtil.updateLostVillagerProfessionName((VillagerEntity)(Object)this);
        }
    }
    @SuppressWarnings("ALL")
    @Inject(method = "onGrowUp", at = @At("TAIL"))
    private void updateBabyText(CallbackInfo ci){
        VillagerUtil.updateGrownUpVillagerName((VillagerEntity)(Object)(this));
    }
}


