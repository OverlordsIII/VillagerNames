package io.github.overlordsiii.villagernames.util;

import io.github.overlordsiii.villagernames.config.NamesConfig;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;


public class VillagerUtil {
    private static final ArrayList<String> usedUpNames = new ArrayList<>();
    private static final Random random = new Random();
    private static String upperFirstLetter(String string){
        if (string.contains(":")){
            string = string.substring(string.indexOf(":") + 1);
        }
        StringBuilder builder = new StringBuilder(string);
        builder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
        return builder.toString();
    }
    private static String pickRandomName(NamesConfig namesConfig) {
        List<String> names = namesConfig.getNameList();
        int index = random.nextInt(names.size());
        if (usedUpNames.size() > names.size()/2) {
            usedUpNames.clear();
        }
        if (usedUpNames.contains(names.get(index))) {
            index = random.nextInt(names.size());
        }
        usedUpNames.add(names.get(index));
        return names.get(index);
    }
    private static String generateRandomVillagerName(){
        return pickRandomName(CONFIG.villagerNamesConfig);
    }
    private static String generateRandomGolemName(){
        return pickRandomName(CONFIG.golemNamesConfig);
    }
    private static void setGenericVillagerName(VillagerEntity entity, String name) {
        if (CONFIG.villagerGeneralConfig.childNames) {
            entity.setCustomName(entity.isBaby() ? new LiteralText(name + " the Child").formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
        else {
            entity.setCustomName(new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
    }

    public static void createVillagerNames(VillagerEntity entity){
        if (!entity.hasCustomName()){
            String randomName = generateRandomVillagerName();
            if (entity.getVillagerData().getProfession() != VillagerProfession.NONE && CONFIG.villagerGeneralConfig.professionNames){
                String text =  VillagerProfession.NITWIT == entity.getVillagerData().getProfession() ? CONFIG.villagerGeneralConfig.nitwitText : upperFirstLetter(entity.getVillagerData().getProfession().toString());
                    entity.setCustomName(new LiteralText(randomName + " the " + text).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
                    entity.setCustomNameVisible(true);

            }
            else {
                entity.setCustomNameVisible(true);
                setGenericVillagerName(entity, randomName);
            }
        }
        entity.setCustomNameVisible(true);
    }
    public static void loadGolemNames(IronGolemEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.golemNames) {
            String name = generateRandomGolemName();
            entity.setCustomName(new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(true);
        }
    }
    public static void updateVillagerNames(VillagerEntity entity){
        if (entity.getVillagerData().getProfession() == VillagerProfession.NONE) {
            setGenericVillagerName(entity, generateRandomVillagerName());
        }
        else {
            if (!Objects.requireNonNull(entity.getCustomName()).asString().contains(" the ") && CONFIG.villagerGeneralConfig.professionNames) {
                //LOGGER.info(entity.getCustomName().asString());
                entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString() + " the " + upperFirstLetter(entity.getVillagerData().getProfession().toString())).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
                //  LOGGER.info(entity.getCustomName().asString());
            }
        }
        entity.setCustomNameVisible(true);
    }
    public static void updateLostVillagerProfessionName(VillagerEntity entity){
        if (entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).asString().contains(" ")) {
            String string = Objects.requireNonNull(entity.getCustomName()).asString();
            //    LOGGER.info("Custom name inside lost Villager Name = " + string);
            String realString = CONFIG.villagerGeneralConfig.professionNames ? string.substring(0, string.indexOf(" ")) : string;
            //     LOGGER.info("Next string = " + realString);
            entity.setCustomName(new LiteralText(realString).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
    }
    public static void updateGrownUpVillagerName(VillagerEntity entity){
        if (entity.hasCustomName()) {
            if (Objects.requireNonNull(entity.getCustomName()).asString().contains(" the")) {
                entity.setCustomName(new LiteralText(entity.getCustomName().asString().substring(0, entity.getCustomName().asString().indexOf(" "))).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            }
        }
    }
    public static void addZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
   //     LOGGER.info(villagerEntity.hasCustomName());
        if (villagerEntity.hasCustomName()){
        //    LOGGER.info("before name = " + villagerEntity.getCustomName().asString());
            if (Objects.requireNonNull(villagerEntity.getCustomName()).asString().contains(" the")) {
                String string = villagerEntity.getCustomName().asString().substring(0, villagerEntity.getCustomName().asString().indexOf(" "));
           //     LOGGER.info("string = " + string);
                zombieVillagerEntity.setCustomName(new LiteralText(string + " the Zombie").formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        //        LOGGER.info("zombie name = " + zombieVillagerEntity.getCustomName().asString());
            }
            else{
                String string = " the Zombie";
                zombieVillagerEntity.setCustomName(new LiteralText(villagerEntity.getCustomName().asString() + string).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            }
        }
    }
    public static void removeZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
        if (zombieVillagerEntity.hasCustomName()){
            if (Objects.requireNonNull(zombieVillagerEntity.getCustomName()).asString().contains(" the")){
                String name = zombieVillagerEntity.getCustomName().asString().substring(0, zombieVillagerEntity.getCustomName().asString().indexOf(" the"));
                villagerEntity.setCustomName(new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            }
            else{
                String string = " the Zombie";
                zombieVillagerEntity.setCustomName(new LiteralText(Objects.requireNonNull(villagerEntity.getCustomName()).asString() + string).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            }
        }
    }
    public static void generalVillagerUpdate(VillagerEntity entity){
        if (entity.hasCustomName()){
            entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString()).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            if (entity.isBaby() && CONFIG.villagerGeneralConfig.childNames && !entity.getCustomName().asString().contains(" the Child")){
                entity.setCustomName(new LiteralText(entity.getCustomName().asString() + " the Child").formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            } else if (entity.getVillagerData().getProfession() == VillagerProfession.NITWIT && !entity.getCustomName().asString().contains(CONFIG.villagerGeneralConfig.nitwitText)){
              String name =  entity.getCustomName().asString().substring(0, entity.getCustomName().asString().indexOf(" the"));
              entity.setCustomName(new LiteralText(name + " the " + CONFIG.villagerGeneralConfig.nitwitText));
            }
            entity.setCustomNameVisible(true);
        }
    }
    public static void createWanderingTraderNames(WanderingTraderEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.wanderingTraderNames){
            entity.setCustomName(new LiteralText(generateRandomVillagerName() + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(true);
        }
    }
    public static void updateWanderingTraderNames(WanderingTraderEntity entity){
        if (entity.hasCustomName()){
            String fullName = Objects.requireNonNull(entity.getCustomName()).asString();
            String firstName = fullName.substring(0, fullName.indexOf(" the"));
            entity.setCustomName(new LiteralText(firstName + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(true);
        }
    }
    public static void updateGolemNames(IronGolemEntity entity){
        if (entity.hasCustomName()){
            entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString()).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(true);
        }
    }
}
