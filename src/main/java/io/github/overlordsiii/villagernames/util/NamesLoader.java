package io.github.overlordsiii.villagernames.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.overlordsiii.villagernames.VillagerNames;

import net.fabricmc.loader.api.FabricLoader;


public class NamesLoader {
    public static void load()  {

        if (!VillagerNames.CONFIG.villagerGeneralConfig.hasRead) {
           VillagerNames.CONFIG.villagerNamesConfig.villagerNames = loadJson("villagerNames.json");
           VillagerNames.CONFIG.golemNamesConfig.golemNames = loadJson("golemNames.json");
           VillagerNames.CONFIG.sureNamesConfig.sureNames = loadJson("surnameNames.json");
           VillagerNames.CONFIG.villagerGeneralConfig.hasRead = true;
            //noinspection UnstableApiUsage
            VillagerNames.CONFIG_MANAGER.save();
       } if (VillagerNames.CONFIG.sureNamesConfig.sureNames.isEmpty()) {
            VillagerNames.CONFIG.sureNamesConfig.sureNames = loadJson("surnameNames.json");
        }
        // no longer needed as it was only used for legacy code
        /*
        if (!VillagerNames.CONFIG.golemNamesConfig.golemNames.contains("Oracle")) {
            loadJson("golemNames.json").forEach(s -> {
                if (!VillagerNames.CONFIG.golemNamesConfig.golemNames.contains(s)) {
                    VillagerNames.CONFIG.golemNamesConfig.golemNames.add(s);
                }
            });
        }
         */
    }
    private static List<String> loadJson(String string){
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader =  new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + string)));
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray value = (JsonArray) object.get(string.substring(0, string.indexOf(".")));
        for (JsonElement jsonElement : value) {
            strings.add(jsonElement.getAsString());
        }
        return strings;
    }
    //the json file copy will be placed in the config dir
    //should be used to jsonify a names txt file
    //@SuppressWarnings("unused")
    private static void jsonifyTxtFile(String file) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + file)));
        reader.lines().forEach(strings::add);
        file = file.substring(0, file.indexOf("."));
        Path configPath = Paths.get(FabricLoader.getInstance().getConfigDir() + "/"+ file + ".json");
        System.out.println(configPath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.createDirectories(configPath.getParent());
            BufferedWriter writer = Files.newBufferedWriter(configPath);
            JsonArray array = new JsonArray();
            strings.forEach(array::add);
            JsonObject object = new JsonObject();
            object.add("villagerNames", array);
            gson.toJson(object, writer);
            writer.close();
    }

}


