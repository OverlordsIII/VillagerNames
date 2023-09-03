package io.github.overlordsiii.villagernames.mixin.guard;

import static io.github.overlordsiii.villagernames.VillagerNames.CONFIG;

import java.util.Objects;

import dev.mrsterner.guardvillagers.common.entity.GuardEntity;
import io.github.overlordsiii.villagernames.api.DefaultNameManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
@Mixin(GuardEntity.class)
public abstract class GuardEntityMixin extends PathAwareEntity implements DefaultNameManager {

	private String firstName;

	private String lastName;

	private String fullName;

	private String playerName;


	protected GuardEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
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
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String name) {
		this.playerName = name;
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

		if (CONFIG.villagerGeneralConfig.professionNames) {
			builder.append(" the Guard");
		}

		this.fullName = builder.toString();
	}

	@Override
	public String getFullName() {
		if (playerName != null) {
			return playerName;
		}
		return fullName;
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
		if (playerName != null) {
			tag.putString("playerName", playerName);
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
		if (tag.contains("playerName")) {
			this.playerName = tag.getString("playerName");
		}
	}
}
