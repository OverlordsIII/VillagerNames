package io.github.overlordsiii.villagernames.util;


import com.google.gson.*;
import io.github.overlordsiii.villagernames.VillagerNames;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class NamesLoader {
    public static void load() {
       if (!VillagerNames.CONFIG.villagerGeneralConfig.hasRead){
           VillagerNames.CONFIG.villagerNamesConfig.villagerNames = loadJson("villagerNames.json");
           VillagerNames.CONFIG.golemNamesConfig.golemNames = loadJson("golemNames.json");
           VillagerNames.CONFIG.villagerGeneralConfig.hasRead = true;
           VillagerNames.CONFIG_MANAGER.save();
       }
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
    @SuppressWarnings("ALL")
    private static void jsonifyTxtFile(String file) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(NamesLoader.class.getResourceAsStream("/assets/villagernames/names/" + file)));
        reader.lines().forEach(strings::add);
        Path configPath = Paths.get(FabricLoader.getInstance().getConfigDir() + "/VillagerNames.json");
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


