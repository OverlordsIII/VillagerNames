package io.github.overlordsiii.villagernames.mixin;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.Objects;

import io.github.overlordsiii.villagernames.api.PiglinNameManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity implements PiglinNameManager {

	private String firstName;
	private String lastName;
	private String fullName;

	public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void setFirstName(String name) {
		this.firstName = name;
		updateFullName();
	}

	@Override
	public void setLastName(String name) {
		this.lastName = name;
		updateFullName();
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public void updateFullName() {
		StringBuilder builder = new StringBuilder();
		Objects.requireNonNull(this.firstName);
		if (CONFIG.villagerGeneralConfig.piglinSurnames && this.lastName != null) {
			builder.append(this.firstName)
				.append(" ")
				.append(this.lastName);
		} else {
			builder.append(this.firstName);
		}

		this.fullName = builder.toString();
	}

	@Override
	public String getFullName() {
		return this.fullName;
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
	}
}
