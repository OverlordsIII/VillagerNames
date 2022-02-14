package io.github.overlordsiii.villagernames.util;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.api.VillagerNameManager;
import io.github.overlordsiii.villagernames.api.ZombieVillagerNameManager;
import io.github.overlordsiii.villagernames.config.NamesConfig;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.village.VillagerProfession;


public class VillagerUtil {

    private static final ArrayList<String> usedUpNames = new ArrayList<>();

    private static final Random random = new Random();

    private static String upperFirstLetter(String string){
        //System.out.println("original string: " + string);
        if (string.contains(":")){
            string = string.substring(string.indexOf(":") + 1);
            //System.out.println("string modified once: " + string);
        }
        if (string.contains("_")) {
            string = string.substring(string.indexOf("_") + 1);
           // System.out.println("string modified twice: " + string);
        }
      //  System.out.println("final string: " + string);
        StringBuilder builder = new StringBuilder(string);
        builder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
      //  System.out.println("builder to string: " + builder);
        return builder.toString();
    }

    private static String pickRandomName(NamesConfig namesConfig) {
        List<String> names = namesConfig.getNameList();
        int index = random.nextInt(names.size());
        if (usedUpNames.size() > names.size()/2) {
            usedUpNames.clear();
        }
        if (usedUpNames.contains(names.get(index))) {
            index = random.nextInt(names.size());//Partial random, but you could possibly choose a name in the list again
        }
        usedUpNames.add(names.get(index));
        return names.get(index);
    }

    private static String generateWanderingTraderName(){
        if (CONFIG.villagerGeneralConfig.surNames) {
            if (CONFIG.villagerGeneralConfig.reverseLastNames) {
                return generateRandomSurname() + " " + pickRandomName(CONFIG.villagerNamesConfig);
            }
            return pickRandomName(CONFIG.villagerNamesConfig) + " " + generateRandomSurname();
        }
        return pickRandomName(CONFIG.villagerNamesConfig);
    }

    private static String generateRandomGolemName(){
        return pickRandomName(CONFIG.golemNamesConfig);
    }

    private static String generateRandomSurname() {
        return pickRandomName(CONFIG.sureNamesConfig);
    }

    private static void setGenericVillagerName(VillagerEntity entity, String name) {
        if (CONFIG.villagerGeneralConfig.childNames) {
            entity.setCustomName(entity.isBaby() ? new LiteralText(name + " the Child").formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()) : new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
        else {
            entity.setCustomName(new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
    }

    public static void createVillagerNames(VillagerEntity entity){
        if (!entity.hasCustomName()){
            VillagerNameManager.setFirstName(entity, pickRandomName(CONFIG.villagerNamesConfig));
            if (CONFIG.villagerGeneralConfig.surNames) {
                VillagerNameManager.setLastName(entity, generateRandomSurname());
            }
            if (entity.getVillagerData().getProfession() != VillagerProfession.NONE && CONFIG.villagerGeneralConfig.professionNames){
                VillagerNameManager.setProfessionName(VillagerProfession.NITWIT == entity.getVillagerData().getProfession() ? CONFIG.villagerGeneralConfig.nitwitText : upperFirstLetter(entity.getVillagerData().getProfession().toString()), entity);

                entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
                entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);

            }
            else {
                entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
                entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
            }
        }
        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    public static void loadGolemNames(IronGolemEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.golemNames) {
            String name = generateRandomGolemName();
            entity.setCustomName(new LiteralText(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void updateVillagerNames(VillagerEntity entity){
        //should never be called, but let's do it anyway!
        if (entity.getVillagerData().getProfession() == VillagerProfession.NONE) {
            VillagerNameManager.setLastName(entity, pickRandomName(CONFIG.villagerNamesConfig));
            if (CONFIG.villagerGeneralConfig.surNames) {
                VillagerNameManager.setLastName(entity, generateRandomSurname());
            }
        }
        else {
            if (VillagerNameManager.getProfessionName(entity) == null && CONFIG.villagerGeneralConfig.professionNames) {
                VillagerNameManager.setProfessionName(upperFirstLetter(entity.getVillagerData().getProfession().toString()), entity);
                entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
            }
            /*
            if (!Objects.requireNonNull(entity.getCustomName()).asString().contains(" the ") && CONFIG.villagerGeneralConfig.professionNames) {
                //LOGGER.info(entity.getCustomName().asString());
                entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString() + " the " + upperFirstLetter(entity.getVillagerData().getProfession().toString())).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
                //  LOGGER.info(entity.getCustomName().asString());
            }
             */
        }
        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    public static void updateLostVillagerProfessionName(VillagerEntity entity){
        /*
        if (entity.hasCustomName() && (CONFIG.villagerGeneralConfig.surNames ? secondIndexOf(entity.getCustomName().asString(), " ") != -1 : entity.getCustomName().asString().contains(" "))) {
            String string = Objects.requireNonNull(entity.getCustomName()).asString();
            //    LOGGER.info("Custom name inside lost Villager Name = " + string);
            String realString = CONFIG.villagerGeneralConfig.professionNames ? string.substring(0, CONFIG.villagerGeneralConfig.surNames ? secondIndexOf(string, " ") : string.indexOf(" ")) : string;
            //     LOGGER.info("Next string = " + realString);
            entity.setCustomName(new LiteralText(realString).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
         */

        if (entity.hasCustomName() && VillagerNameManager.getProfessionName(entity) != null) {
            VillagerNameManager.setProfessionName(null, entity);
            entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
        }
    }

    public static void updateGrownUpVillagerName(VillagerEntity entity){
        if (entity.hasCustomName()) {
            VillagerNameManager.updateFullName(entity);
            entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
        }
    }

    public static void addZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
   //     LOGGER.info(villagerEntity.hasCustomName());
        if (villagerEntity.hasCustomName()){
            /*
        //    LOGGER.info("before name = " + villagerEntity.getCustomName().asString());
            if (Objects.requireNonNull(villagerEntity.getCustomName()).asString().contains(" the ")) {
                String string = villagerEntity.getCustomName().asString().substring(0, CONFIG.villagerGeneralConfig.surNames ? secondIndexOf(villagerEntity.getCustomName().asString(), " ") : villagerEntity.getCustomName().asString().indexOf(" "));
           //     LOGGER.info("string = " + string);
                zombieVillagerEntity.setCustomName(new LiteralText(string + " the Zombie").formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        //        LOGGER.info("zombie name = " + zombieVillagerEntity.getCustomName().asString());
            }
            else {
                String string = " the Zombie";
                zombieVillagerEntity.setCustomName(new LiteralText(villagerEntity.getCustomName().asString() + string).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            }
             */

            String firstName = VillagerNameManager.getFirstName(villagerEntity);
            ZombieVillagerNameManager.setFirstName(firstName, zombieVillagerEntity);
            if (CONFIG.villagerGeneralConfig.surNames) {
                ZombieVillagerNameManager.setLastName(VillagerNameManager.getLastName(villagerEntity), zombieVillagerEntity);
            }
            zombieVillagerEntity.setCustomName(new LiteralText(ZombieVillagerNameManager.getFullNameWithZombie(zombieVillagerEntity)).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
    }

    public static void removeZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
        if (zombieVillagerEntity.hasCustomName()){
            if (ZombieVillagerNameManager.getFirstName(zombieVillagerEntity) != null) {
                String firstName = ZombieVillagerNameManager.getFirstName(zombieVillagerEntity);
                if (CONFIG.villagerGeneralConfig.surNames) {
                    VillagerNameManager.setLastName(villagerEntity, ZombieVillagerNameManager.getLastName(zombieVillagerEntity));
                }

                VillagerNameManager.setFirstName(villagerEntity, firstName);
                villagerEntity.setCustomName(VillagerNameManager.getFullNameAsText(villagerEntity, true));
            }
        }
    }

    public static void generalVillagerUpdate(VillagerEntity entity){
        if (entity.hasCustomName()){
            /*String namer = Objects.requireNonNull(entity.getCustomName()).asString();
            if (CONFIG.villagerGeneralConfig.surNames) {
                if (entity.getVillagerData().getProfession() == VillagerProfession.NONE && !namer.contains(" ")) {//indicates that the villager does not have a profession and does not have last name
                    namer = namer + " " + generateRandomSurname();
                } else if (entity.getVillagerData().getProfession() != VillagerProfession.NONE && namer.charAt(namer.indexOf(" ") + 1) == 't') {//indicates they have a profession but no last name
                    StringBuilder builder = new StringBuilder(namer);
                    builder.insert(namer.indexOf(" "), " " + generateRandomSurname());
                    namer = builder.toString();
                }
            }
            entity.setCustomName(new LiteralText(namer).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
             */

            if (CONFIG.villagerGeneralConfig.surNames) { // check if last name config rule was changed, and if so give them a last name
                if (VillagerNameManager.getLastName(entity) == null) {
                    VillagerNameManager.setLastName(entity, generateRandomSurname());
                }
            } else {
                if (VillagerNameManager.getLastName(entity) != null) {
                    VillagerNameManager.setLastName(entity, null);
                }
            }

            entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));

            if (entity.getVillagerData().getProfession() == VillagerProfession.NITWIT && !entity.getCustomName().asString().contains(CONFIG.villagerGeneralConfig.nitwitText)){ // update if villager is nitwit and nitwit text changed
                VillagerNameManager.setProfessionName(CONFIG.villagerGeneralConfig.nitwitText, entity);
                entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
            }
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void createWanderingTraderNames(WanderingTraderEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.wanderingTraderNames){
            entity.setCustomName(new LiteralText(generateWanderingTraderName() + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void updateWanderingTraderNames(WanderingTraderEntity entity){
        if (entity.hasCustomName()) {
            if (entity.getCustomName().asString().contains(" the ")) {
                String fullName = Objects.requireNonNull(entity.getCustomName()).asString();
                String firstName = fullName.substring(0, fullName.indexOf(" the "));
                entity.setCustomName(new LiteralText(firstName + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
                entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
            }
        }
    }

    public static void updateGolemNames(IronGolemEntity entity){
        if (entity.hasCustomName()){
            entity.setCustomName(new LiteralText(Objects.requireNonNull(entity.getCustomName()).asString()).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    private static int secondIndexOf(String source, String index) {
        return source.indexOf(index, source.indexOf(index) + 1);
    }

    public static void addLastNameFromBreeding(VillagerEntity childEntity, VillagerEntity parentEntity) {
        if (parentEntity.hasCustomName() && CONFIG.villagerGeneralConfig.surNames) {
            VillagerNameManager.setFirstName(childEntity, pickRandomName(CONFIG.villagerNamesConfig));
            VillagerNameManager.setLastName(childEntity, VillagerNameManager.getLastName(parentEntity));
            childEntity.setCustomName(VillagerNameManager.getFullNameAsText(childEntity, true));
        }
    }
}
