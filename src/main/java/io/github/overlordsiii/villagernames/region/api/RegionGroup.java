package io.github.overlordsiii.villagernames.region.api;

public enum RegionGroup {
	EAST_ASIA("east_asia"),
	SOUTH_ASIA("south_asia");

	private final String name;

	RegionGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
