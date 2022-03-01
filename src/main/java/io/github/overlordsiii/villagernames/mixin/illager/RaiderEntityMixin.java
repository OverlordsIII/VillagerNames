package io.github.overlordsiii.villagernames.mixin.illager;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.Objects;

import io.github.overlordsiii.villagernames.api.RaiderNameManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;

@Mixin(RaiderEntity.class)
public abstract class RaiderEntityMixin implements RaiderNameManager {

	private String firstName = null;

	private String fullName = null;

	private String lastName = null;

	private String title = getDefaultTitle();

	/**
	 * Set pillager's first name
	 *
	 * @param firstName first name of illager entity
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		updateFullName();
	}

	@Override
	public String getDefaultTitle() {
		// easier than having to use mixins on all non-abstract subclasses of RaiderEntity
		RaiderEntity entity = (RaiderEntity) (Object) this;
		if (entity instanceof EvokerEntity) {
			return "Evoker";
		} else if (entity instanceof IllusionerEntity) {
			return "Illusioner";
		} else if (entity instanceof PillagerEntity) {
			return "Pillager";
		} else if (entity instanceof VindicatorEntity) {
			return "Vindicator";
		} else if (entity instanceof WitchEntity) {
			return "Witch";
		}

		return null;
	}

	/**
	 * Get first name of illager entity
	 *
	 * @return first name
	 */
	@Override
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Set illager entity's last name
	 *
	 * @param lastNames illager entity last name
	 */
	@Override
	public void setLastName(String lastNames) {
		this.lastName = lastNames;
		updateFullName();
	}

	/**
	 * Get last name currently used
	 *
	 * @return illager entity's last name
	 */
	@Override
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Gets the illager entity's title
	 *
	 * @return the title, or null if the title is toggled off
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Removes the title of the entity if present
	 */
	@Override
	public void removeTitle() {
		this.title = null;
		updateFullName();
	}

	/**
	 * Sets the title for the illager
	 *
	 * @param title the name of the title
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
		updateFullName();
	}

	/**
	 * Returns the full name of the illager entity
	 *
	 * @return the fullName
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	/**
	 * Updates the full name. This should be called when any other method in this interface is referenced
	 * <p>
	 * It is by default called internally by the method implementation*
	 */
	@Override
	public void updateFullName() {
		StringBuilder builder = new StringBuilder();
		Objects.requireNonNull(this.firstName);
		if (CONFIG.villagerGeneralConfig.reverseLastNames && CONFIG.villagerGeneralConfig.surNames && this.lastName != null) {
			builder.append(this.lastName)
				.append(" ")
				.append(this.firstName);
		} else if (CONFIG.villagerGeneralConfig.surNames && this.lastName != null) {
			builder.append(this.firstName)
				.append(" ")
				.append(this.lastName);
		} else {
			builder.append(this.firstName);
		}

		if (CONFIG.villagerGeneralConfig.professionNames && this.title != null && !this.title.equals("None")) {
			builder.append(" the ")
				.append(this.title);
		}

		this.fullName = builder.toString();
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void serializeData(NbtCompound tag, CallbackInfo ci) {
		if (firstName != null) {
			tag.putString("firstName", firstName);
		}
		if (fullName != null) {
			tag.putString("fullName", fullName);
		}
		if (lastName != null) {
			tag.putString("lastName", lastName);
		}
		if (title != null) {
			tag.putString("title", title);
		}
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void deserializeData(NbtCompound tag, CallbackInfo ci) {
		if (tag.contains("firstName")) {
			this.firstName = tag.getString("firstName");
		}
		if (tag.contains("fullName")) {
			this.fullName = tag.getString("fullName");
		}
		if (tag.contains("lastName")) {
			this.lastName = tag.getString("lastName");
		}
		if (tag.contains("title")) {
			this.title = tag.getString("title");
		}
	}
}
