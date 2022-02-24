package io.github.overlordsiii.villagernames;

import java.io.IOException;

import io.github.overlordsiii.villagernames.api.PiglinNameManager;
import io.github.overlordsiii.villagernames.command.VillagerNameCommand;
import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.util.NamesLoader;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.dedicated.command.StopCommand;
import net.minecraft.util.ActionResult;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class VillagerNames implements ModInitializer {
    public static  ConfigManager<VillagerConfig> CONFIG_MANAGER;
    public static  VillagerConfig CONFIG;

    public static final Logger LOGGER = LogManager.getLogger(VillagerNames.class);

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
            } else if (entity instanceof PiglinEntity piglinEntity && CONFIG.villagerGeneralConfig.piglinNames) {
                VillagerUtil.createPiglinNames(piglinEntity);
                VillagerUtil.updatePiglinNames(piglinEntity);
            }
        });

    }

}
