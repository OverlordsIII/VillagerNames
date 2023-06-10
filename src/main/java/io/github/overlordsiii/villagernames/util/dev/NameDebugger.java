package io.github.overlordsiii.villagernames.util.dev;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.google.gson.JsonObject;
import io.github.overlordsiii.villagernames.api.DefaultNameManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import net.fabricmc.loader.api.FabricLoader;

import static io.github.overlordsiii.villagernames.VillagerNames.*;

public class NameDebugger {
	public static ActionResult printNames(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
		if (!world.isClient() && FabricLoader.getInstance().isDevelopmentEnvironment()) {
			if (entity instanceof DefaultNameManager manager) {
				JsonObject object = new JsonObject();

				getInterfaces(entity.getClass())
					.stream()
					.filter(DefaultNameManager.class::isAssignableFrom)
					.flatMap(aClass -> Arrays.stream(aClass.getMethods()))
					.filter(method -> !method.getReturnType().equals(Void.class))
					.filter(method -> !Modifier.isStatic(method.getModifiers()))
					.filter(method -> method.getName().startsWith("get"))
					.map(method -> getStringObjectPair(method, manager, entity))
					.forEach(method -> object.addProperty(method.getLeft(), String.valueOf(method.getRight())));

				LOGGER.info(GSON.toJson(object));
				player.sendMessage(Text.literal(GSON.toJson(object)));


			}
		}

		return ActionResult.PASS;
	}

	@NotNull
	private static Pair<String, Object> getStringObjectPair(Method method, DefaultNameManager manager, Entity entity) {
		try {
			String varName = method.getName().substring(3);
			if (method.getParameterCount() == 0) {
				return new Pair<>(varName, method.invoke(manager));
			} else if (method.getParameterCount() == 1) {
				return new Pair<>(varName, method.invoke(manager, entity));
			}
			else return new Pair<>(varName, null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// looks at superclasses and adds interfaces from those classes till there is no more superclass
	private static List<Class<?>> getInterfaces(Class<?> targetClass) {
		List<Class<?>> interfaces = new ArrayList<>();

		while (targetClass.getSuperclass() != null) {
			interfaces.addAll(List.of(targetClass.getInterfaces()));
			targetClass = targetClass.getSuperclass();
		}

		return interfaces;
	}
}
