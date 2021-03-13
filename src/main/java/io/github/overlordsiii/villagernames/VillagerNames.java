package io.github.overlordsiii.villagernames;

import io.github.overlordsiii.villagernames.command.VillagerNameCommand;
import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.util.NamesLoader;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
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

public class VillagerNames implements ModInitializer {
    public static final ConfigHolder<VillagerConfig> CONFIG_HOLDER;
    public static final VillagerConfig CONFIG;

    public static final Logger LOGGER = LogManager.getLogger(VillagerNames.class);

    static {
        CONFIG_HOLDER = AutoConfig.register(VillagerConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        CONFIG = CONFIG_HOLDER.getConfig();
    }

    @Override
    public void onInitialize() {
        NamesLoader.load();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            VillagerNameCommand.register(commandDispatcher);
            if (!dedicated) {
                StopCommand.register(commandDispatcher);
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
            if (entity instanceof VillagerEntity) {
                VillagerUtil.updateVillagerName((VillagerEntity)entity);
            } else if (entity instanceof IronGolemEntity) {
                VillagerUtil.updateGolemName((IronGolemEntity)entity);
            } else if (entity instanceof WanderingTraderEntity) {
                VillagerUtil.updateWanderingTraderName((WanderingTraderEntity)entity);
            }
        });
    }
}
