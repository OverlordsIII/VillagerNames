package io.github.overlordsiii.villagernames.util;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.overlordsiii.villagernames.config.NamesConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Nameable;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class VillagerUtil {

    private static final ArrayList<String> usedUpNames = new ArrayList<>();

    private static final Random random = new Random();

    private static String upperFirstLetter(String string) {
        int j = string.indexOf(":");
        if (j != -1) {
            string = string.substring(j + 1);
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
            index = random.nextInt(names.size());//Partial random, but you could possibly choose a name in the list again
        }
        usedUpNames.add(names.get(index));
        return names.get(index);
    }

    private static String generateRandomVillagerName() {
        String forename = pickRandomName(CONFIG.villagerNamesConfig);

        if (CONFIG.villagerGeneralConfig.surNames) {
            String surname = generateRandomSurname();

            if (CONFIG.villagerGeneralConfig.reverseLastNames) {
                return surname + " " + forename;
            } else {
                return forename + " " + surname;
            }
        }

        return forename;
    }

    private static String generateRandomGolemName() {
        return pickRandomName(CONFIG.golemNamesConfig);
    }

    private static String generateRandomSurname() {
        return pickRandomName(CONFIG.sureNamesConfig);
    }

    public static void updateVillagerName(VillagerEntity entity) {
        String fullName;

        if (!entity.hasCustomName()) {
            fullName = generateRandomVillagerName();
        } else {
            fullName = parseFullName(entity);

            if (CONFIG.villagerGeneralConfig.surNames) {
                String surname = parseSurname(fullName);
                if (surname == null) {
                    fullName += " " + generateRandomSurname();
                }
            }

            // TODO: what if surNames were enabled and now disabled? Do we try to remove them?
            // this is one thing which would be easier if fore- and surnames were stored independently
            // Alternatively we could assume that a full name consists of exactly 2 tokens; with the implementation
            // right now, middle names are allowed (as part of the forename)
        }

        String titledFullName = getTitledVillagerName(entity, fullName);

        setEntityName(entity, titledFullName);
    }

    @NotNull
    private static String getTitledVillagerName(VillagerEntity entity, String fullName) {
        VillagerProfession profession = entity.getVillagerData().getProfession();

        if (CONFIG.villagerGeneralConfig.childNames && entity.isBaby()) {
            return fullName + " the Child";
        } else if (CONFIG.villagerGeneralConfig.professionNames && profession != VillagerProfession.NONE) {
            String professionName = (profession == VillagerProfession.NITWIT ? CONFIG.villagerGeneralConfig.nitwitText : upperFirstLetter(profession.toString()));
            return fullName + " the " + professionName;
        } else {
            return fullName;
        }
    }

    @NotNull
    private static String parseFullName(Nameable entity) {
        String customName = Objects.requireNonNull(entity.getCustomName()).asString();

        int j = customName.indexOf(" the ");
        if (j == -1) {
            return customName;
        } else {
            return customName.substring(0, j);
        }
    }

    @NotNull
    private static String parseForename(String fullName) {
        int j = fullName.lastIndexOf(' ');
        if (j == -1) {
            return fullName;
        } else {
            return fullName.substring(0, j);
        }
    }

    @Nullable
    private static String parseSurname(String fullName) {
        int j = fullName.lastIndexOf(' ');
        if (j == -1) {
            return null;
        } else {
            return fullName.substring(j + 1);
        }
    }

    public static void inheritSurname(VillagerEntity childEntity, VillagerEntity parentEntity) {
        if (CONFIG.villagerGeneralConfig.surNames) {
            assert childEntity.hasCustomName();
            assert parentEntity.hasCustomName();

            String childForename = parseForename(parseFullName(childEntity));
            String parentSurname = parseSurname(parseFullName(parentEntity));

            assert parentSurname != null; // possible when changing surNames without a restart

            String titledFullName = getTitledVillagerName(childEntity, childForename + " " + parentSurname);

            setEntityName(childEntity, titledFullName);
        }
    }

    public static void setZombieVillagerName(ZombieVillagerEntity zombieVillagerEntity, VillagerEntity villagerEntity) {
        assert villagerEntity.hasCustomName();

        String titledFullName = parseFullName(villagerEntity) + " the Zombie";
        setEntityName(zombieVillagerEntity, titledFullName);
    }

    public static void transferZombieVillagerName(ZombieVillagerEntity zombieVillagerEntity, VillagerEntity villagerEntity) {
        // TODO: do naturally spawned zombie villagers get names?

        if (zombieVillagerEntity.hasCustomName()) {
            // a freshly converted villager probably can't have a title, but just in case
            String titledFullName = getTitledVillagerName(villagerEntity, parseFullName(zombieVillagerEntity));
            setEntityName(villagerEntity, titledFullName);
        }
    }

    public static void updateWanderingTraderName(WanderingTraderEntity entity) {
        if (CONFIG.villagerGeneralConfig.wanderingTraderNames) {
            String fullName;
            if (!entity.hasCustomName()) {
                fullName = generateRandomVillagerName();
            } else {
                fullName = parseFullName(entity);
            }
            String titledFullName = fullName + " the " + CONFIG.villagerGeneralConfig.wanderingTraderText;

            setEntityName(entity, titledFullName);
        } else {
            // TODO: what should actually happen if names are disabled and entities already have custom names?
            applyFormatting(entity);
        }
    }

    public static void updateGolemName(IronGolemEntity entity) {
        if (CONFIG.villagerGeneralConfig.golemNames && !entity.hasCustomName()) {
            // Note: Golems have no titles
            String fullName = generateRandomGolemName();
            setEntityName(entity, fullName);
        } else {
            // TODO
            applyFormatting(entity);
        }
    }

    private static void setEntityName(Entity entity, String titledFullName) {
        entity.setCustomName(new LiteralText(titledFullName).formatted(CONFIG.villagerGeneralConfig.villagerTextFormatting.getFormatting()));
        entity.setCustomNameVisible(!CONFIG.villagerGeneralConfig.nameTagNames);
    }

    private static void applyFormatting(Entity entity) {
        if (entity.hasCustomName()) {
            setEntityName(entity, entity.getCustomName().asString());
        }
    }
}
