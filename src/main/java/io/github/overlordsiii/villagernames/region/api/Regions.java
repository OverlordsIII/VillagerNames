package io.github.overlordsiii.villagernames.region.api;

//TODO use Datapacks instead of hardcoding
public class Regions {

	public static Region ENGLISH;
	public static Region SPANISH;
	public static Region PORTUGUESE;
	public static Region FRENCH;
	public static Region GERMAN;
	public static Region SCANDANAVIA;
	public static Region CHINA_TAIWAN_SINGAPORE;
	public static Region JAPAN;
	public static Region KOREA;
	public static Region MONGOLIA;
	public static Region INDIA_PAKISTAN_BANGLADESH;
	public static Region SRI_LANKA;
	public static Region MALDIVES;
	public static Region BHUTAN;
	public static Region NEPAL;
	public static Region AFGHANISTAN;
	public static Region MYANMAR;
	public static Region MIDDLE_EAST;
	public static Region BALTIC;
	public static Region SLAVIC;

	public static void loadRegions() {
		ENGLISH = new Region("english", null, new RegionRules(0.75, 0.85, 0.15, false), null, false);
		SPANISH = new Region("spanish", null, new RegionRules(0.5, 0.2, 0.8, false), null, false);
		PORTUGUESE = new Region("portuguese", null, new RegionRules(0.4, 0.2, 0.8, false), null, false);
		FRENCH = new Region("french", null, new RegionRules(0.3, 0.8, 0.2, false), null, false);
		GERMAN = new Region("german", null, new RegionRules(0.75, 0.85, 0.15, false), null, false);
		SCANDANAVIA = new Region("scandanavian", null, new RegionRules(0.15, 0.9, 0.1, false), null, false);
		CHINA_TAIWAN_SINGAPORE = new Region("china_taiwan_singapore", RegionGroup.EAST_ASIA, new RegionRules(0.03, 0.95, 0.05, true), "east_asia/china_taiwan_singapore", false);
		JAPAN = new Region("japan", RegionGroup.EAST_ASIA, new RegionRules(0.03, 1, 0, true), "east_asia/japan", false);
		KOREA = new Region("korea", RegionGroup.EAST_ASIA, new RegionRules(0.1, 1, 0, true), "east_asia/korea", false);
		MONGOLIA = new Region("mongolia", RegionGroup.EAST_ASIA, new RegionRules(0, 0.5, 0.5, false), "east_asia/mongolia", false);
		INDIA_PAKISTAN_BANGLADESH = new Region("india_pakistan_bangalore", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/india_pakistan_bangalore", false);
		SRI_LANKA = new Region("sri_lanka", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/sri_lanka", false);
		MALDIVES = new Region("maldives", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/maldives", false);
		BHUTAN = new Region("bhutan", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/bhutan", false);
		NEPAL = new Region("nepal", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/nepal", true);
		AFGHANISTAN = new Region("afghanistan", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/afghanistan", false);
		MYANMAR = new Region("myanmar", RegionGroup.SOUTH_ASIA, new RegionRules(0.1, 0.95, 0, false), "south_asia/myanmar", false);
		MIDDLE_EAST = new Region("middle_eastern", null, new RegionRules(0.15, 0.85, 0.15, false), null, false);
		BALTIC = new Region("baltic", null, new RegionRules(0.1, 0.99, 0.01, false), null, false);
		SLAVIC = new Region("slavic", null, new RegionRules(0.8, 0.99, 0.01, false), null, false);
	}

}
