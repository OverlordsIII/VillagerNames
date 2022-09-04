package io.github.overlordsiii.villagernames.integration.cca;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.WorldProperties;

public class RavagerCounterComponent implements IntComponent {

	//random starting point
	private int value = (int) (Math.random() * 100);

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Reads this component's properties from a {@link NbtCompound}.
	 *
	 * @param tag a {@code NbtCompound} on which this component's serializable data has been written
	 * @implNote implementations should not assert that the data written on the tag corresponds to any
	 * specific scheme, as saved data is susceptible to external tempering, and may come from an earlier
	 * version.
	 */
	@Override
	public void readFromNbt(NbtCompound tag) {
		if (tag.contains("ravagerValue")) {
			this.value = tag.getInt("ravagerValue");
		}
	}

	/**
	 * Writes this component's properties to a {@link NbtCompound}.
	 *
	 * @param tag a {@code NbtCompound} on which to write this component's serializable data
	 */
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("ravagerValue", this.value);
	}
}
