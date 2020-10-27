package io.github.overlordsiii.villagernames;

import com.mojang.brigadier.CommandDispatcher;
import io.github.overlordsiii.villagernames.command.VillagerNameCommand;
import io.github.overlordsiii.villagernames.config.VillagerConfig;
import io.github.overlordsiii.villagernames.config.VillagerNamesConfig;
import io.github.overlordsiii.villagernames.util.NamesLoader;
import io.github.overlordsiii.villagernames.util.VillagerUtil;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;


public class VillagerNames implements ModInitializer {
    public static final ConfigManager CONFIG_MANAGER;
    public static final VillagerConfig CONFIG;
    static {
      CONFIG_MANAGER = (ConfigManager) AutoConfig.register(VillagerConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
      CONFIG = AutoConfig.getConfigHolder(VillagerConfig.class).getConfig();
    }
    @Override
    public void onInitialize() {
        System.out.println("Client = " + MinecraftClient.getInstance());
        NamesLoader.load();
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            VillagerNameCommand.register(commandDispatcher);
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
            if (entity instanceof VillagerEntity){
               VillagerUtil.createVillagerNames((VillagerEntity)entity);
               VillagerUtil.generalVillagerUpdate((VillagerEntity)entity);
            }
            if (entity instanceof IronGolemEntity){
                    VillagerUtil.loadGolemNames((IronGolemEntity)entity);
            }
        });
    }

}
