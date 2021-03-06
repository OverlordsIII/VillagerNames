package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void redirect(Logger logger, String message, Object p0, Object p1){
        if (VillagerNames.CONFIG.villagerGeneralConfig.turnOffVillagerConsoleSpam) {
            String lol = "ha lol gottem";
        }
        else{
            logger.info(message, p0, p1);
        }
    }
    @SuppressWarnings("ALL")
    @Inject(method = "onGrowUp", at = @At("TAIL"))
    private void updateBabyText(CallbackInfo ci){
        VillagerUtil.updateGrownUpVillagerName((VillagerEntity)(Object)(this));
    }
    /*
    @Inject(method = "createChild",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/VillagerEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/entity/EntityData;")
            , locals = LocalCapture.CAPTURE_FAILHARD)
    private void onCreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<VillagerEntity> cir, VillagerType villagerType3, VillagerEntity villagerEntity) {
        VillagerUtil.addLastNameFromBreeding(villagerEntity, (VillagerEntity)(Object)this);
    }
     */
}


