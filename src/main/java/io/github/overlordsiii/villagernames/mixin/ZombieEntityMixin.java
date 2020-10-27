package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieVillagerEntity;setXp(I)V"), locals = LocalCapture.PRINT)
    private void addZombieNameTag(ServerWorld serverWorld, LivingEntity livingEntity, CallbackInfo ci, VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
        VillagerUtil.addZombieVillagerName(villagerEntity, zombieVillagerEntity);
    }
}
