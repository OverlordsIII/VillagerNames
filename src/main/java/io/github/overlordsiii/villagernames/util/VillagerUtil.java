package io.github.overlordsiii.villagernames.util;

import io.github.overlordsiii.villagernames.VillagerNames;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class VillagerUtil {
    private static ArrayList<String> usedUpNames = new ArrayList<>();
    private static String upperFirstLetter(String string){
        StringBuilder builder = new StringBuilder(string);
        builder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
        return builder.toString();
    }
    public static void createVillagerNames(VillagerEntity entity){
        if (!entity.hasCustomName()){
            String randomName = generateRandomVillagerName();
            if (entity.getVillagerData().getProfession() != VillagerProfession.NONE){
                entity.setCustomName(new LiteralText(randomName + " the " + upperFirstLetter(entity.getVillagerData().getProfession().toString())));
                entity.setCustomNameVisible(true);
            }
            else{
                entity.setCustomNameVisible(true);
                entity.setCustomName(entity.isBaby() ?new LiteralText(randomName + " the Child") : new LiteralText(randomName));
            }
        }
        entity.setCustomNameVisible(true);
    }
    public static String generateRandomVillagerName(){
        Random random = new Random();
       int index = random.nextInt(VillagerNames.CONFIG.villagerNamesConfig.villagerNames.size());
       if (usedUpNames.size() > VillagerNames.CONFIG.villagerNamesConfig.villagerNames.size()/2){
           usedUpNames.clear();
       }
       if (usedUpNames.contains(VillagerNames.CONFIG.villagerNamesConfig.villagerNames.get(index))) {
           index = random.nextInt(VillagerNames.CONFIG.villagerNamesConfig.villagerNames.size());
       }
           usedUpNames.add(VillagerNames.CONFIG.villagerNamesConfig.villagerNames.get(index));
        return VillagerNames.CONFIG.villagerNamesConfig.villagerNames.get(index);
    }
    public static String generateRandomGolemName(){
        Random random = new Random();
        int index = random.nextInt(VillagerNames.CONFIG.golemNamesConfig.golemNames.size());
        if (usedUpNames.size() > VillagerNames.CONFIG.golemNamesConfig.golemNames.size()/2){
            usedUpNames.clear();
        }
        if (usedUpNames.contains(VillagerNames.CONFIG.golemNamesConfig.golemNames.get(index))) {
            index = random.nextInt(VillagerNames.CONFIG.golemNamesConfig.golemNames.size());
        }
        usedUpNames.add(VillagerNames.CONFIG.golemNamesConfig.golemNames.get(index));
        return VillagerNames.CONFIG.golemNamesConfig.golemNames.get(index);
    }
    public static void loadGolemNames(IronGolemEntity entity){
        if (!entity.hasCustomName()) {
            String name = generateRandomGolemName();
            entity.setCustomName(new LiteralText(name));
            entity.setCustomNameVisible(true);
        }
    }
    public static void updateVillagerNames(VillagerEntity entity){
        if (entity.getVillagerData().getProfession() == VillagerProfession.NONE){
            String random = generateRandomVillagerName();
            entity.setCustomName(entity.isBaby() ? new LiteralText(random + " the Child") : new LiteralText(random));
        }
        else {
            if (!Objects.requireNonNull(entity.getCustomName()).asString().contains("the")) {
                //System.out.println(entity.getCustomName().asString());
                entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString() + " the " + upperFirstLetter(entity.getVillagerData().getProfession().toString())));
                //  System.out.println(entity.getCustomName().asString());
            }
        }
        entity.setCustomNameVisible(true);
    }
    public static void updateLostVillagerProfessionName(VillagerEntity entity){
        if (entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).asString().contains(" ")) {
            String string = Objects.requireNonNull(entity.getCustomName()).asString();
            //    System.out.println("Custom name inside lost Villager Name = " + string);
            String realString = string.substring(0, string.indexOf(" "));
            //     System.out.println("Next string = " + realString);
            entity.setCustomName(new LiteralText(realString));
        }
    }
}
