package io.github.overlordsiii.villagernames.region.api;

import java.util.List;

import org.jetbrains.annotations.Nullable;

public interface RegionTemplate {

	String getRegionName();

	@Nullable RegionGroup getRegionGroup();

	RegionRules getRegionRules();

	List<String> getNames();

	List<String> getSurnames();

	List<String> getMiddleNames();

	default List<String> getNames(NameType type) {
		switch (type) {
			case FIRST_NAME -> {
				return getNames();
			}
			case MIDDLE_NAME -> {
				return getMiddleNames();
			}
			case SURNAME -> {
				return getSurnames();
			}
			default -> throw new NullPointerException("NameType Cannot be Null!");
		}
	}
}
