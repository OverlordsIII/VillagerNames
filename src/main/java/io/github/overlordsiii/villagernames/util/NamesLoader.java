package io.github.overlordsiii.villagernames.util;

import com.google.gson.*;
import io.github.overlordsiii.villagernames.VillagerNames;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class NamesLoader {
    public static void load() {
       if (!VillagerNames.CONFIG.villagerGeneralConfig.hasRead){
           VillagerNames.CONFIG.villagerNamesConfig.villagerNames = loadJson("villagerNames.json");
           VillagerNames.CONFIG.golemNamesConfig.golemNames = loadJson("golemNames.json");
           VillagerNames.CONFIG.villagerGeneralConfig.hasRead = true;
           VillagerNames.CONFIG_MANAGER.save();
       }
    }
    private static ArrayList<String> loadJson(String string){
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader =  new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + string)));
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray value = (JsonArray) object.get(string.substring(0, string.indexOf(".")));
        Iterator<JsonElement> stringIterator = value.iterator();
        while (stringIterator.hasNext()){
            strings.add(stringIterator.next().getAsString());
        }
        return strings;
    }

    private static ArrayList<String> loadNames(String string){
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader =  new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + string)));
        reader.lines().forEach(strings::add);
        System.out.println("strings = " + strings);
        return strings;
    }

}


