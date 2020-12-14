package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.extension.VillagerNameTracker;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements VillagerNameTracker {

    private String firstName = null;

    private final List<String> lastNames = new ArrayList<>();

    private String professionName = null;

    private boolean childTag = false;

    @SuppressWarnings("FieldMayBeFinal")
    private String fullName = updateFullName();

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

    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void redirect(Logger logger, String message, Object p0, Object p1){
        if (VillagerNames.CONFIG.villagerGeneralConfig.turnOffVillagerConsoleSpam) {
            String lol = "ha lol gottem";
        }
        else{
            logger.info(message, p0, p1);
        }
    }

    @Inject(method = "onGrowUp", at = @At("TAIL"))
    private void updateBabyText(CallbackInfo ci){
        VillagerUtil.updateGrownUpVillagerName((VillagerEntity)(Object)(this));
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void serializeData(CompoundTag tag, CallbackInfo ci) {
        if (firstName != null) {
            tag.putString("FirstName", firstName);
        }
        ListTag lastNamesTag = new ListTag();
        lastNames.forEach(string -> {
            CompoundTag tag1 = new CompoundTag();
            tag1.putString("LastName", string);
            lastNamesTag.add(tag1);
        });
        tag.put("LastNames", lastNamesTag);
        if (professionName != null) {
            tag.putString("ProfessionName", professionName);
        }
        tag.putBoolean("ChildTag", childTag);
        tag.putString("FullName", fullName);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void deserializeData(CompoundTag tag, CallbackInfo ci) {

    }

    public String updateFullName() {
        StringBuilder builder = new StringBuilder();
        if (CONFIG.villagerGeneralConfig.multipleLastNames) {
            builder.append(firstName);
            if (CONFIG.villagerGeneralConfig.surNames) {
                builder.append(" ");
                lastNames.forEach(string -> {
                    builder.append(string);
                    builder.append(" ");
                });
            }
        } else if (CONFIG.villagerGeneralConfig.reverseLastNames) {
            if (CONFIG.villagerGeneralConfig.surNames) {
                builder.append(lastNames.get(0));
                builder.append(" ");
            }
            builder.append(firstName);
        }
        if (CONFIG.villagerGeneralConfig.professionNames && !this.isBaby() && professionName != null) {
            builder.append(" the ");
            builder.append(professionName);
        } else if (this.isBaby() && childTag && CONFIG.villagerGeneralConfig.childNames) {
            builder.append(" the Child");
        }
        return builder.toString();
    }

    /**
     * gets the villagers first name
     *
     * @return the villagers very first name (or first name)
     */
    @Override
    public @Nullable String getFirstName() {
        return firstName;
    }

    /**
     * Gets the villagers last names, if it has any
     *
     * @return the number of lastnames
     */
    @Override
    public List<String> getLastNames() {
        return lastNames;
    }

    /**
     * Sets the villager's first name
     *
     * @param name the new first name
     */
    @Override
    public void setFirstName(String name) {
        this.firstName = name;
        fullName = updateFullName();
    }

    /**
     * Removes the villager's last name
     *
     * @param name the last name to remove
     */
    @Override
    public void removeLastName(String name) {
        lastNames.remove(name);
        fullName = updateFullName();
    }

    /**
     * Removes all last names
     */
    @Override
    public void clearLastNames() {
        lastNames.clear();
        fullName = updateFullName();
    }

    /**
     * Adds a last name
     *
     * @param name the name to add
     */
    @Override
    public void addLastName(String name) {
        lastNames.add(name);
        fullName = updateFullName();
    }

    /**
     * Gets the villager's profession name
     *
     * @return the profession name, or null if the profession name is toggled off
     */
    @Override
    public @Nullable String getProfessionName() {
        return professionName;
    }

    /**
     * Removes the profession name if present
     */
    @Override
    public void removeProfessionName() {
        professionName = null;
        fullName = updateFullName();
    }

    /**
     * Adds a new profession name to the villagers name
     *
     * @param appendedProfession the name of the profession
     */
    @Override
    public void setProfessionName(String appendedProfession) {
        this.professionName = appendedProfession;
        fullName = updateFullName();
    }

    /**
     * Removes the Child Tag
     */
    @Override
    public void removeChildName() {
        childTag = false;
        fullName = updateFullName();
    }

    /**
     * Adds the Child Tag if enabled
     */
    @Override
    public void addChildName() {
        childTag = true;
        fullName = updateFullName();
    }

    /**
     * Returns the full name of the villager
     *
     * @return the fullName
     */
    @Override
    public @Nullable String getFullName() {
        return fullName;
    }
}


