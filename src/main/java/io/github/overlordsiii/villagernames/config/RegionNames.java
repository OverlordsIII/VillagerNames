package io.github.overlordsiii.villagernames.config;

import java.util.List;

import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.config.names.NamesConfig;
import io.github.overlordsiii.villagernames.region.api.NameType;
import io.github.overlordsiii.villagernames.region.api.Region;
import io.github.overlordsiii.villagernames.region.api.Regions;

public enum RegionNames {
	DEFAULT(null),
	BALTIC(Regions.BALTIC),
	CHINA_TAIWAN_SINGAPORE(Regions.CHINA_TAIWAN_SINGAPORE),
	JAPAN(Regions.JAPAN),
	KOREA(Regions.KOREA),
	MONGOLIA(Regions.MONGOLIA),
	ENGLISH(Regions.ENGLISH),
	FRENCH(Regions.FRENCH),
	GERMAN(Regions.GERMAN),
	MIDDLE_EASTERN(Regions.MIDDLE_EAST),
	PORTUGUESE(Regions.PORTUGUESE),
	SCANDANAVIAN(Regions.SCANDANAVIA),
	SLAVIC(Regions.SLAVIC),
	AFGHANISTAN(Regions.AFGHANISTAN),
	BHUTAN(Regions.BHUTAN),
	INDIA_PAKISTAN_BANGALORE(Regions.INDIA_PAKISTAN_BANGLADESH),
	MALDIVES(Regions.MALDIVES),
	MYANMAR(Regions.MYANMAR),
	NEPAL(Regions.NEPAL),
	SRI_LANKA(Regions.SRI_LANKA),
	SPANISH(Regions.SPANISH);

	private final Region region;

	RegionNames(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public NamesConfig toNamesConfig(NameType type) {
		if (this.region == null) {
			return VillagerNames.CONFIG.villagerNamesConfig;
		}

		return () -> this.region.getNames(type);
	}
}
