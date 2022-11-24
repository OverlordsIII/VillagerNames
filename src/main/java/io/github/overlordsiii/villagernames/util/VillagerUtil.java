package io.github.overlordsiii.villagernames.util;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.api.PiglinNameManager;
import io.github.overlordsiii.villagernames.api.RaiderNameManager;
import io.github.overlordsiii.villagernames.api.VillagerNameManager;
import io.github.overlordsiii.villagernames.api.ZombieVillagerNameManager;
import io.github.overlordsiii.villagernames.config.names.NamesConfig;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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
        int index = random.nextInt(names.size() - 1);
        if (usedUpNames.size() > names.size()/2) {
            usedUpNames.clear();
        }
        if (usedUpNames.contains(names.get(index))) {
            index = random.nextInt(names.size() - 1); // Partial random, but you could possibly choose a name in the list again
        }
        usedUpNames.add(names.get(index));
        return names.get(index);
    }

    private static String generateWanderingTraderName(){
        if (CONFIG.villagerGeneralConfig.surNames) {
            if (CONFIG.villagerGeneralConfig.reverseLastNames) {
                return generateRandomSurname() + "" + pickRandomName(CONFIG.villagerNamesConfig);
            }
            return pickRandomName(CONFIG.villagerNamesConfig) + "" + generateRandomSurname();
        }
        return pickRandomName(CONFIG.villagerNamesConfig);
    }

    private static String generateRandomGolemName(){
        return pickRandomName(CONFIG.golemNamesConfig);
    }

    private static String generateRandomSurname() {
        return pickRandomName(CONFIG.sureNamesConfig);
    }

    public static void createPiglinNames(AbstractPiglinEntity entity) {
        if (!entity.hasCustomName()) {
            PiglinNameManager.setFirstName(pickRandomName(CONFIG.piglinNamesConfig), entity);
            if (CONFIG.villagerGeneralConfig.piglinSurnames) {
                PiglinNameManager.setLastName(pickRandomName(CONFIG.piglinSurnamesConfig), entity);
            }
            entity.setCustomName(PiglinNameManager.getFullNameAsText(entity, true));
        }
        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    public static void createRavagerNames(ServerWorld world, RavagerEntity entity) {
        if (!entity.hasCustomName()) {
            int counter = VillagerNames.INT_COMPONENT.get(world.getLevelProperties()).getValue() + 1;
            entity.setCustomName(Text.literal("Test Subject " + counter).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            VillagerNames.INT_COMPONENT.get(world.getLevelProperties()).setValue(counter);
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

    public static void createIllagerNames(RaiderEntity entity) {
        if (!entity.hasCustomName()) {
            RaiderNameManager.setFirstName(entity, pickRandomName(CONFIG.villagerNamesConfig));
            if (CONFIG.villagerGeneralConfig.surNames) {
                RaiderNameManager.setLastName(entity, generateRandomSurname());
            }

            entity.setCustomName(RaiderNameManager.getFullNameAsText(entity, true));
        }

        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    public static void loadGolemNames(IronGolemEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.golemNames) {
            String name = generateRandomGolemName();
            entity.setCustomName(Text.literal(name).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void addProfessionName(VillagerEntity entity){
        // this is done bc this is called before villagers are loaded by server, so villagers with professions will just dissapear unless they are parsed correctly
        generalVillagerUpdate(entity);
        if (VillagerNameManager.getProfessionName(entity) == null && CONFIG.villagerGeneralConfig.professionNames) {
            VillagerNameManager.setProfessionName(upperFirstLetter(entity.getVillagerData().getProfession().toString()), entity);
        }
        entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    public static void updateLostVillagerProfessionName(VillagerEntity entity){

        if (entity.hasCustomName() && VillagerNameManager.getProfessionName(entity) != null) {
            VillagerNameManager.setProfessionName(null, entity);
            entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
        }
    }

    public static void updateGrownUpVillagerName(VillagerEntity entity){
        if (entity.hasCustomName()) {
            generalVillagerUpdate(entity); // parse name if not already done
            VillagerNameManager.updateFullName(entity);
            entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
        }
    }

    public static void addZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
   //     LOGGER.info(villagerEntity.hasCustomName());
        if (villagerEntity.hasCustomName()){

            String firstName = VillagerNameManager.getFirstName(villagerEntity);
            ZombieVillagerNameManager.setFirstName(firstName, zombieVillagerEntity);
            if (CONFIG.villagerGeneralConfig.surNames) {
                ZombieVillagerNameManager.setLastName(VillagerNameManager.getLastName(villagerEntity), zombieVillagerEntity);
            }
            zombieVillagerEntity.setCustomName(Text.literal(ZombieVillagerNameManager.getFullNameWithZombie(zombieVillagerEntity)).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        }
    }

    public static void removeZombieVillagerName(VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
        if (zombieVillagerEntity.hasCustomName()){
            if (ZombieVillagerNameManager.getFirstName(zombieVillagerEntity) == null) {
                String[] components = zombieVillagerEntity.getCustomName().getString().split("\\s+");
                String firstNameParsed = components[0];
                String lastNameParsed = null;
                if (CONFIG.villagerGeneralConfig.surNames && components.length > 1) {
                    lastNameParsed = components[1];
                }
                ZombieVillagerNameManager.setFirstName(firstNameParsed, zombieVillagerEntity);
                ZombieVillagerNameManager.setLastName(lastNameParsed == null ? generateRandomSurname() : lastNameParsed, zombieVillagerEntity);
            }

            String firstName = ZombieVillagerNameManager.getFirstName(zombieVillagerEntity);
            if (CONFIG.villagerGeneralConfig.surNames) {
                VillagerNameManager.setLastName(villagerEntity, ZombieVillagerNameManager.getLastName(zombieVillagerEntity));
            }

            VillagerNameManager.setFirstName(villagerEntity, firstName);
            villagerEntity.setCustomName(VillagerNameManager.getFullNameAsText(villagerEntity, true));
        }
    }

    public static void updatePiglinNames(AbstractPiglinEntity entity) {

        if (PiglinNameManager.getFirstName(entity) == null && CONFIG.villagerGeneralConfig.piglinNames) {
            PiglinNameManager.setFirstName(pickRandomName(CONFIG.piglinNamesConfig), entity);
        }

        if (CONFIG.villagerGeneralConfig.piglinSurnames) { // check if last name config rule was changed, and if so give them a last name

            if (PiglinNameManager.getLastName(entity) == null) {
                PiglinNameManager.setLastName(pickRandomName(CONFIG.piglinSurnamesConfig), entity);
            }
        } else {
            if (PiglinNameManager.getLastName(entity) != null) {
                PiglinNameManager.setLastName(null, entity);
            }
        }

        entity.setCustomName(PiglinNameManager.getFullNameAsText(entity, true));
    }

    public static void updateIllagerNames(RaiderEntity entity) {

        if (RaiderNameManager.getFirstName(entity) == null && CONFIG.villagerGeneralConfig.illagerEntityNames) {
            RaiderNameManager.setFirstName(entity, pickRandomName(CONFIG.villagerNamesConfig));
        }

        if (CONFIG.villagerGeneralConfig.surNames) { // check if last name config rule was changed, and if so give them a last name
            if (RaiderNameManager.getLastName(entity) == null) {
                RaiderNameManager.setLastName(entity, generateRandomSurname());
            }
        } else {
            if (RaiderNameManager.getLastName(entity) != null) {
                RaiderNameManager.setLastName(entity, null);
            }
        }

        if (!CONFIG.villagerGeneralConfig.professionNames) {
            if (RaiderNameManager.getTitle(entity) != null) {
                RaiderNameManager.setTitle(entity, null);
            }
        } else {
            if (entity.hasActiveRaid() && entity.isPatrolLeader()) {
                RaiderNameManager.setTitle(entity, "Raid Captain");
            } else if (RaiderNameManager.getTitle(entity) == null) {
                RaiderNameManager.setTitle(entity, RaiderNameManager.getDefaultTitle(entity));
            }
        }

        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        entity.setCustomName(RaiderNameManager.getFullNameAsText(entity, true));


    }

    public static void generalVillagerUpdate(VillagerEntity entity){
        if (entity.hasCustomName()){

            if (VillagerNameManager.getFirstName(entity) == null) {
                // this indicates that the mod has found a villager that has been named according to the older version of mod
                // so to update, we'll have to parse the name and put it in our version of the villager names
                //TODO this ^

                String[] nameComponents = entity.getCustomName().getString().split("\\s+");
                if (nameComponents.length == 0) {
                    return; // should never happen
                }
                String firstName = nameComponents[0]; // guaranteed to be first one there
                String lastName = null;
                String professionName = null;
                if (CONFIG.villagerGeneralConfig.surNames && nameComponents.length >= 2) {
                    if (CONFIG.villagerGeneralConfig.reverseLastNames) {
                        firstName = nameComponents[1];
                        lastName = nameComponents[0];
                    } else {
                        lastName = nameComponents[1];
                    }
                }
                // indicates the name has "the" plus another name, possibly the profession name or nitwit text.
                if (nameComponents.length >= 4) {
                    //ensures that profession name is not set to child
                    if (!nameComponents[3].toLowerCase(Locale.ROOT).trim().equals("child")) {
                        professionName = nameComponents[3];
                    }
                }

                VillagerNameManager.setFirstName(entity, firstName);
                VillagerNameManager.setLastName(entity, firstName);
                VillagerNameManager.setProfessionName(professionName, entity);

            }

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

            if (entity.getVillagerData().getProfession() == VillagerProfession.NITWIT && !entity.getCustomName().getString().contains(CONFIG.villagerGeneralConfig.nitwitText)){ // update if villager is nitwit and nitwit text changed
                VillagerNameManager.setProfessionName(CONFIG.villagerGeneralConfig.nitwitText, entity);
                entity.setCustomName(VillagerNameManager.getFullNameAsText(entity, true));
            }
            if (!CONFIG.villagerGeneralConfig.professionNames) {
                if (VillagerNameManager.getProfessionName(entity) != null) {
                    VillagerNameManager.setProfessionName(null, entity);
                }
            } else {
                if (entity.getVillagerData().getProfession() != VillagerProfession.NONE && !entity.isBaby()) {
                    VillagerNameManager.setProfessionName(upperFirstLetter(entity.getVillagerData().getProfession().toString()), entity);
                }
            }

            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void createWanderingTraderNames(WanderingTraderEntity entity){
        if (!entity.hasCustomName() && CONFIG.villagerGeneralConfig.wanderingTraderNames){
            entity.setCustomName(Text.literal(generateWanderingTraderName() + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void updateWanderingTraderNames(WanderingTraderEntity entity){
        if (entity.hasCustomName()) {
            if (entity.getCustomName().getString().contains(" the ")) {
                String fullName = Objects.requireNonNull(entity.getCustomName()).getString();
                String firstName = fullName.substring(0, fullName.indexOf(" the "));
                entity.setCustomName(Text.literal(firstName + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
                entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
            }
        }
    }

    public static void updateGolemNames(IronGolemEntity entity){
        if (entity.hasCustomName()){
            entity.setCustomName(Text.literal(Objects.requireNonNull(entity.getCustomName()).getString()).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
            entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
        }
    }

    public static void addLastNameFromBreeding(VillagerEntity childEntity, VillagerEntity parentEntity) {
        if (parentEntity.hasCustomName() && CONFIG.villagerGeneralConfig.surNames) {
            VillagerNameManager.setFirstName(childEntity, pickRandomName(CONFIG.villagerNamesConfig));
            VillagerNameManager.setLastName(childEntity, VillagerNameManager.getLastName(parentEntity));
            childEntity.setCustomName(VillagerNameManager.getFullNameAsText(childEntity, true));
        }
    }
}
