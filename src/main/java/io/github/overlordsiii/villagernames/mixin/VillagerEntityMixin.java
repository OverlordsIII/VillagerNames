package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow public abstract VillagerData getVillagerData();

    // TODO: may or may not be used, probably not though
//    public String forename;
//    @Nullable public String surname;

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
//    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
//        tag.putString("forename", forename);
//        tag.putString("surname", surname);
//    }
//
//    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
//    public void fromTag(CompoundTag tag, CallbackInfo ci) {
//        forename = tag.getString("forename");
//        surname  = tag.getString("surname");
//    }

    @Inject(method = "setVillagerData", at = @At("RETURN"))
    private void setVillagerData(VillagerData villagerData, CallbackInfo ci) {
        VillagerUtil.updateVillagerName((VillagerEntity) (Object) this);
    }

    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), remap = false)
    private void redirect(Logger logger, String message, Object p0, Object p1) {
        // ha lol gottem
        if (!VillagerNames.CONFIG.villagerGeneralConfig.turnOffVillagerConsoleSpam) {
            logger.info(message, p0, p1);
        }
    }

    @Inject(method = "onGrowUp", at = @At("RETURN"))
    private void onGrowUp(CallbackInfo ci) {
        VillagerUtil.updateVillagerName((VillagerEntity) (Object) this);
    }
}


