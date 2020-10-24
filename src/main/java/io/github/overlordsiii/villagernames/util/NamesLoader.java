package io.github.overlordsiii.villagernames.util;

import io.github.overlordsiii.villagernames.VillagerNames;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NamesLoader {
    public static void load() {
       if (!VillagerNames.CONFIG.villagerGeneralConfig.hasRead){
           VillagerNames.CONFIG.villagerNamesConfig.villagerNames = loadNames("villagerNames.txt");
           VillagerNames.CONFIG.golemNamesConfig.golemNames = loadNames("golemNames.txt");
           VillagerNames.CONFIG.villagerGeneralConfig.hasRead = true;
           VillagerNames.CONFIG_MANAGER.save();
       }
    }
    private static ArrayList<String> loadNames(String string){
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader =  new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + string)));
        reader.lines().forEach(strings::add);

        return strings;
    }

}
