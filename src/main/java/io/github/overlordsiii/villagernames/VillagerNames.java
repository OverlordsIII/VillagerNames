package io.github.overlordsiii.villagernames;

import io.github.overlordsiii.villagernames.command.VillagerNameCommand;
import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.util.NamesLoader;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.dedicated.command.StopCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ALL")
public class VillagerNames implements ModInitializer {
    public static final ConfigManager CONFIG_MANAGER;
    public static final VillagerConfig CONFIG;
    public static final Logger LOGGER = LogManager.getLogger(VillagerNames.class);
    static {
      CONFIG_MANAGER = (ConfigManager) AutoConfig.register(VillagerConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
      CONFIG = AutoConfig.getConfigHolder(VillagerConfig.class).getConfig();
    }
    @Override
    public void onInitialize() {
        NamesLoader.load();
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            VillagerNameCommand.register(commandDispatcher);
            if (!dedicated){
                StopCommand.register(commandDispatcher);
            }
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
            if (entity instanceof VillagerEntity){
               VillagerUtil.createVillagerNames((VillagerEntity)entity);
               VillagerUtil.generalVillagerUpdate((VillagerEntity)entity);
            }
            else if (entity instanceof IronGolemEntity){
                VillagerUtil.loadGolemNames((IronGolemEntity)entity);
                VillagerUtil.updateGolemNames((IronGolemEntity)entity);
            } else if (entity instanceof WanderingTraderEntity){
                VillagerUtil.createWanderingTraderNames((WanderingTraderEntity)entity);
                VillagerUtil.updateWanderingTraderNames((WanderingTraderEntity)entity);
            }
        });

    }

}
