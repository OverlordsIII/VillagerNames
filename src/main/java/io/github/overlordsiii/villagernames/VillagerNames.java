package io.github.overlordsiii.villagernames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import io.github.overlordsiii.villagernames.api.DefaultNameManager;
import io.github.overlordsiii.villagernames.api.PiglinNameManager;
import io.github.overlordsiii.villagernames.api.RaiderNameManager;
import io.github.overlordsiii.villagernames.api.VillagerNameManager;
import io.github.overlordsiii.villagernames.command.VillagerNameCommand;
import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.integration.cca.IntComponent;
import io.github.overlordsiii.villagernames.integration.cca.RavagerCounterComponent;
import io.github.overlordsiii.villagernames.util.NamesLoader;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import io.github.overlordsiii.villagernames.util.dev.NameDebugger;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.util.tuples.Pair;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.dedicated.command.StopCommand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class VillagerNames implements ModInitializer, LevelComponentInitializer {

    public static  ConfigManager<VillagerConfig> CONFIG_MANAGER;
    public static  VillagerConfig CONFIG;
    public static final Logger LOGGER = LogManager.getLogger(VillagerNames.class);
    public static final ComponentKey<IntComponent> INT_COMPONENT = ComponentRegistry.getOrCreate(new Identifier("villagernames", "intcomponent"), IntComponent.class);

    public static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();

    static {
      CONFIG_MANAGER = (ConfigManager<VillagerConfig>) AutoConfig.register(VillagerConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
      CONFIG = AutoConfig.getConfigHolder(VillagerConfig.class).getConfig();

    }
    @Override
    public void onInitialize() {
        try {
            NamesLoader.load();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while loading default names");
            e.printStackTrace();
        }

        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            VillagerNameCommand.register(commandDispatcher);
            if (!dedicated){
                StopCommand.register(commandDispatcher);
            }
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
            if (entity instanceof VillagerEntity villagerEntity){
               VillagerUtil.createVillagerNames(villagerEntity);
               VillagerUtil.generalVillagerUpdate(villagerEntity);
            } else if (entity instanceof IronGolemEntity ironGolemEntity){
                VillagerUtil.loadGolemNames(ironGolemEntity);
                VillagerUtil.updateGolemNames(ironGolemEntity);
            } else if (entity instanceof WanderingTraderEntity wanderingTraderEntity){
                VillagerUtil.createWanderingTraderNames(wanderingTraderEntity);
                VillagerUtil.updateWanderingTraderNames(wanderingTraderEntity);
            } else if (entity instanceof AbstractPiglinEntity piglinEntity && CONFIG.villagerGeneralConfig.piglinNames) {
                VillagerUtil.createPiglinNames(piglinEntity);
                VillagerUtil.updatePiglinNames(piglinEntity);
                // ravager names are seperate due to how they are different than normal raider entities
                // called "illagers" in code but these are raider entities bc not all raiders are illagers
            } else if (entity instanceof RaiderEntity raiderEntity && !(entity instanceof RavagerEntity) && CONFIG.villagerGeneralConfig.illagerEntityNames) {
                VillagerUtil.createIllagerNames(raiderEntity);
                VillagerUtil.updateIllagerNames(raiderEntity);
            } else if (CONFIG.villagerGeneralConfig.illagerEntityNames && entity instanceof RavagerEntity ravagerEntity) {
                VillagerUtil.createRavagerNames(serverWorld, ravagerEntity);
            }
        });

        UseEntityCallback.EVENT.register(NameDebugger::printNames);

    }



    /**
     * Called to register component factories for statically declared component types.
     *
     * <p><strong>The passed registry must not be held onto!</strong> Static component factories
     * must not be registered outside of this method.
     *
     * @param registry a {@link LevelComponentFactoryRegistry} for <em>statically declared</em> components
     */
    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(INT_COMPONENT, worldProperties -> new RavagerCounterComponent());
    }
}
