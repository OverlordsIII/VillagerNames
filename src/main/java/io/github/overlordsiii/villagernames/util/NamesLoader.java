package io.github.overlordsiii.villagernames.util;


import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import io.github.overlordsiii.villagernames.VillagerNames;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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
        Iterator<JsonElement> stringIterator = value.iterator();
        while (stringIterator.hasNext()){
            strings.add(stringIterator.next().getAsString());
        }
        return strings;
    }
    //the json file copy will be placed in the config dir
    //shuold be used to jsonify a names txt file
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


