package io.github.overlordsiii.villagernames.region.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.overlordsiii.villagernames.VillagerNames;
import org.jetbrains.annotations.Nullable;

public class Region implements RegionTemplate {

	private final String name;

	private final RegionGroup group;

	private final RegionRules regionRules;

	private final List<String> names;

	private final List<String> surnames;

	private final List<String> middleNames;

	private static final Path COUNTRIES_PATH = new File(Region.class.getResource("/assets/villagernames/").getPath().replace("file:\\", "")).toPath().resolve("names").resolve("countries");

	public Region(String name, RegionGroup group, RegionRules regionRules, String path, boolean middleNames) {
		this.name = name;
		this.group = group;
		this.regionRules = regionRules;

		Path resourcesPath = COUNTRIES_PATH.resolve((path == null ? name : path));

		Path firstNamesStrPath = resourcesPath.resolve(name + "_names.json");
		Path surnamesStrPath = resourcesPath.resolve(name + "_surnames.json");
		Path middlenamesStrPath = resourcesPath.resolve(name + "_middle_names.json");

		try {
			names = parsePath(firstNamesStrPath);
			surnames = parsePath(surnamesStrPath);

			if (middleNames) {
				this.middleNames = parsePath(middlenamesStrPath);
			} else {
				this.middleNames = null;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public String getRegionName() {
		return this.name;
	}


	@Override
	public @Nullable RegionGroup getRegionGroup() {
		return this.group;
	}


	@Override
	public RegionRules getRegionRules() {
		return this.regionRules;
	}


	@Override
	public List<String> getNames() {
		return this.names;
	}


	@Override
	public List<String> getSurnames() {
		return this.surnames;
	}

	@Override
	public List<String> getMiddleNames() {
		return this.middleNames;
	}

	private static List<String> parsePath(Path path) throws IOException {
		List<String> strings = new ArrayList<>();
		String str = readFile(path);
		JsonObject object = JsonParser.parseString(str).getAsJsonObject();
		object.get("names").getAsJsonArray().forEach(jsonElement -> {
			strings.add(jsonElement.getAsString());
		});

		return strings;
	}

	public static String readFile(Path path) {
		VillagerNames.LOGGER.info(path);
		String fileText;
		try {
			if(Files.size(path) == 0) {
				throw new RuntimeException("File has zero bytes");
			}
			fileText = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			if(fileText.trim().isEmpty()) {
				throw new RuntimeException("File contains only whitespace");
			}
			return fileText;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
