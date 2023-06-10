package io.github.overlordsiii.villagernames.region.api;

public class RegionRules {

	private final double middleNameChance;

	private final double oneSurnameChance;

	private final double twoSurnameChance;

	private final boolean familyNameFirst;
	public RegionRules(double middleNameChance, double oneSurNameChance, double twoSurnamesChance, boolean familyNameFirst) {
		this.middleNameChance = middleNameChance;
		this.oneSurnameChance = oneSurNameChance;
		this.twoSurnameChance = twoSurnamesChance;
		this.familyNameFirst = familyNameFirst;
	}

	public double getMiddleNameChance() {
		return middleNameChance;
	}

	public double getOneSurnameChance() {
		return oneSurnameChance;
	}

	public double getTwoSurnameChance() {
		return twoSurnameChance;
	}

	public boolean isFamilyNameFirst() {
		return familyNameFirst;
	}
}
