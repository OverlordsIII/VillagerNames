package io.github.overlordsiii.villagernames.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.base.Throwables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.overlordsiii.villagernames.VillagerNames;
import io.github.overlordsiii.villagernames.command.suggestion.FormattingSuggestionProvider;
import io.github.overlordsiii.villagernames.command.suggestion.NameSuggestionProvider;
import io.github.overlordsiii.villagernames.config.FormattingDummy;
import io.github.overlordsiii.villagernames.config.names.GolemNamesConfig;
import io.github.overlordsiii.villagernames.config.names.PiglinNamesConfig;
import io.github.overlordsiii.villagernames.config.names.PiglinSurnamesConfig;
import io.github.overlordsiii.villagernames.config.names.SureNamesConfig;
import io.github.overlordsiii.villagernames.config.VillagerGeneralConfig;
import io.github.overlordsiii.villagernames.config.names.VillagerNamesConfig;
import net.minecraft.text.MutableText;
import org.jetbrains.annotations.Nullable;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("ALL")
public class VillagerNameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("villagername")
        .requires(source -> VillagerNames.CONFIG.villagerGeneralConfig.needsOP ? source.hasPermissionLevel(2) : source.hasPermissionLevel(4))
            .then(literal("toggle")
                .then(literal("professionNames")
                    .executes(context -> executeToggle(context, "professionNames", "Profession names are now toggled %s")))
                .then(literal("golemNames")
                    .executes(context -> executeToggle(context, "golemNames", "Golem Names are now toggled %s")))
                .then(literal("needsOP")
                    .executes(context -> executeToggle(context, "needsOP", "The VillagerNames commands that need op are now toggled %s")))
                .then(literal("childNames")
                    .executes(context -> executeToggle(context, "childNames", "Children Having names is now toggled %s")))
                .then(literal("turnOffConsoleSpam")
                    .executes(context -> executeToggle(context, "turnOffVillagerConsoleSpam", "Villagers dying spamming the console is now toggled %s")))
                .then(literal("wanderingTraderNames")
                    .executes(context -> executeToggle(context, "wanderingTraderNames", "Wandering Traders having names is now toggled %s")))
                .then(literal("surnames")
                    .executes(context -> executeToggle(context, "surNames", "Villager Last Names is now toggled %s")))
                .then(literal("reverseLastNames")
                    .executes(context -> executeToggle(context, "reverseLastNames", "Reverse Villager Last Names is now toggled %s")))
                .then(literal("nameTagNames")
                    .executes(context -> executeToggle(context, "nameTagNames", "Name Tag Names is now toggled to %s")))
                .then(literal("piglinNames")
                    .executes(context -> executeToggle(context, "piglinNames", "Piglin Names is now toggled to %s")))
                .then(literal("piglinSurnames")
                    .executes(context -> executeToggle(context, "pillagerSurenames", "Piglin Surnames is now toggled to %s")))
                .then(literal("illagerEntityNames")
                    .executes(context -> executeToggle(context, "illagerEntityNames", "Illager Entity Names is now toggled to %s")))
                .then(literal("catNames")
                    .executes(context -> executeToggle(context, "catNames", "Cat Names is now toggled to %s"))))
            .then(literal("add")
                .then(literal("villagerNames")
                    .then(argument("villagerName", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.villagerNamesConfig.villagerNames, StringArgumentType.getString(context, "villagerName"), "Added %s to the villager names list", "villagerNames"))))
                .then(literal("golemNames")
                    .then(argument("golemName", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.golemNamesConfig.golemNames, StringArgumentType.getString(context, "golemName"), "Added %s to the golem names list", "golemNames"))))
                .then(literal("sureNames")
                    .then(argument("sureName", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.sureNamesConfig.sureNames, StringArgumentType.getString(context, "sureName"), "Added %s to the surename names list", "sureNames"))))
                .then(literal("piglinNames")
                    .then(argument("piglinName", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.piglinNamesConfig.piglinNames, StringArgumentType.getString(context, "piglinName"), "Added %s to the piglin names list", "piglinNames"))))
                .then(literal("piglinSurnames")
                    .then(argument("piglinSurname", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.piglinSurnamesConfig.piglinSurnames, StringArgumentType.getString(context, "pillagerSurname"), "Added %s to the piglin surnames list", "piglinSurnames"))))
                .then(literal("catNames")
                    .then(argument("catName", StringArgumentType.greedyString())
                        .executes(context -> executeAdd(context, VillagerNames.CONFIG.catNamesConfig.catNames, StringArgumentType.getString(context, "catName"), "Added %s to the cat names list", "catNames")))))
            .then(literal("remove")
                .then(literal("villagerNames")
                    .then(argument("villagerNam", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.Villager())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.villagerNamesConfig.villagerNames, StringArgumentType.getString(context, "villagerNam"), "Removed %s from the villager names list", "villagerNames"))))
                .then(literal("golemNames")
                    .then(argument("golemNam", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.Golem())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.golemNamesConfig.golemNames, StringArgumentType.getString(context, "golemNam"), "Removed %s from the golem names list", "golemNames"))))
                .then(literal("sureNames")
                    .then(argument("sureName", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.Surename())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.sureNamesConfig.sureNames, StringArgumentType.getString(context, "sureName"), "Removed %s from the sure names list", "sureNames"))))
                .then(literal("piglinNames")
                    .then(argument("piglinName", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.Piglin())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.piglinNamesConfig.piglinNames, StringArgumentType.getString(context, "piglinName"), "Removed %s from the piglin names list", "piglinNames"))))
                .then(literal("piglinSurnames")
                    .then(argument("piglinSurname", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.PiglinSurname())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.piglinSurnamesConfig.piglinSurnames, StringArgumentType.getString(context, "piglinSurname"), "Removed %s from the piglin surnames list", "piginSurnames"))))
                .then(literal("catNames")
                    .then(argument("catName", StringArgumentType.string())
                        .suggests(new NameSuggestionProvider.CatName())
                            .executes(context -> executeRemove(context, VillagerNames.CONFIG.catNamesConfig.catNames, StringArgumentType.getString(context, "catName"), "Removed %s from the cat names list", "catNames")))))
            .then(literal("set")
                .then(literal("nitwitText")
                    .then(argument("nitwit", StringArgumentType.greedyString())
                        .executes(context -> executeSetString(context, "nitwitText", StringArgumentType.getString(context, "nitwit"), "The nitwit Text is now set to '%s'"))))
                .then(literal("villagerTextFormat")
                    .then(argument("format", StringArgumentType.string())
                        .suggests(new FormattingSuggestionProvider())
                            .executes(context -> executeSetFormatting(context, "The villager Text formatting is now set to %s", StringArgumentType.getString(context, "format")))))
                .then(literal("wanderingTraderText")
                    .then(argument("wanderingText", StringArgumentType.greedyString())
                        .executes(context -> executeSetString(context, "wanderingTraderText", StringArgumentType.getString(context, "wanderingText"), "The Wandering Trader Text is now set to '%s'")))))
            .then(literal("info")
                .executes(VillagerNameCommand::executeInfo)));
    }

    @SuppressWarnings("ALL")
    private static int executeSetFormatting(CommandContext<ServerCommandSource> ctx, String displayText, String format) throws CommandSyntaxException {
        Formatting newFormatting;
        try {
            newFormatting = Formatting.valueOf(format);
        } catch (Exception e) {
            ctx.getSource().getPlayer().sendMessage(Text.literal("ha nice one, but you can't set the formatting to some random formatting. Stick with what the command suggests to you absolute lingesh").formatted(Formatting.LIGHT_PURPLE), false);
            return -1;
        }
        VillagerNames.CONFIG.villagerGeneralConfig.villagerTextFormatting = FormattingDummy.fromFormatting(newFormatting);
        ctx.getSource().sendFeedback(() -> Text.literal(String.format(displayText
                , FormattingDummy.fromFormatting(newFormatting)))
                .formatted(newFormatting == Formatting.OBFUSCATED ? Formatting.WHITE : newFormatting).styled(style ->
                        style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND
                                , "/villagername set villagerTextFormat " +
                                FormattingDummy.fromFormatting(newFormatting).toString()))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        Text.literal(FormattingDummy.fromFormatting(newFormatting)
                                                .toString()).formatted(newFormatting))))
                , true);
        VillagerNames.CONFIG_MANAGER.save();
        try {
            broadCastConfigChangeToOps(ctx, ConfigChange.SET, VillagerGeneralConfig.class.getDeclaredField("villagerTextFormatting"), ctx.getSource().getPlayer(), null);
        } catch (Exception e) {
            logError(ctx, e);
        }
        return 1;
    }

    private static int executeSetString(CommandContext<ServerCommandSource> ctx, String literal, String newvalue, String displayedText) throws CommandSyntaxException {
        switch (literal){
            case "nitwitText": VillagerNames.CONFIG.villagerGeneralConfig.nitwitText = newvalue;
            case "wanderingTraderText": VillagerNames.CONFIG.villagerGeneralConfig.wanderingTraderText = newvalue;
        }
        String text = String.format(displayedText, newvalue);
        ctx.getSource().sendFeedback( () -> Text.literal(text).formatted(Formatting.LIGHT_PURPLE)
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
                        , Text.literal(
                                "/villagername set " + literal + " " + newvalue)))
                        .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND
                                , "/villagername set " + literal))), true);
        VillagerNames.CONFIG_MANAGER.save();
        try {
            broadCastConfigChangeToOps(ctx, ConfigChange.SET, VillagerGeneralConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), null);
        } catch (Exception e) {
            logError(ctx, e);
        }
        return 1;
    }

    private static int executeToggle(CommandContext<ServerCommandSource> ctx, String literal, String displayText) throws CommandSyntaxException {
        String onOrOff;
        try {
            Field field = VillagerGeneralConfig.class.getField(literal);
            boolean newValue = !field.getBoolean(VillagerNames.CONFIG.villagerGeneralConfig);
            field.setBoolean(VillagerNames.CONFIG.villagerGeneralConfig, newValue);
            onOrOff = parseBoolean(newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logError(ctx, e);
            return -1;
        }
        //      System.out.println("onOrOff = " + onOrOff);
          String text = String.format(displayText, onOrOff);
          ctx.getSource().sendFeedback(() ->
                  Text.literal(text).formatted(Formatting.YELLOW)
                          .styled(style -> style.withClickEvent(
                                  new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND
                                          , "/villagername toggle " + literal))
                                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
                                          , Text.literal("Toggle the " + literal + " rule")))), true);
        VillagerNames.CONFIG_MANAGER.save();
        try {
            broadCastConfigChangeToOps(ctx, ConfigChange.TOGGLE, VillagerGeneralConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), null);
        } catch (Exception e) {
            logError(ctx, e);
        }
        return 1;
    }

    private static int executeAdd(CommandContext<ServerCommandSource> ctx, List<String> listToAddTo, String toAdd, String displayText, String literal) throws CommandSyntaxException {
        if (!listToAddTo.contains(toAdd)){
            listToAddTo.add(toAdd);
            String text = String.format(displayText, toAdd);
            ctx.getSource().sendFeedback(() ->
                    Text.literal(text).formatted(Formatting.AQUA)
                            .styled(style -> style.withHoverEvent(new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT
                                    , Text.literal("Add an item to the villager or golem list")))
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND
                                            , "/villagername add " + literal + " " + toAdd))), true);
        }
        else{
            ctx.getSource().getPlayer().sendMessage(
                    Text.literal("The villager or golem list you tried to add too already had that name in it")
                            .formatted(Formatting.RED), false);
        }
        try {
            if (literal.contains("villagerNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.ADD, VillagerNamesConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), toAdd);
            } else if (literal.contains("golemNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.ADD, GolemNamesConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), toAdd);
            } else if (literal.contains("sureNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.ADD, SureNamesConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), toAdd);
            } else if (literal.contains("piglinNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.ADD, PiglinNamesConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), toAdd);
            } else if (literal.contains("piglinSurnames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.ADD, PiglinSurnamesConfig.class.getDeclaredField(literal), ctx.getSource().getPlayer(), toAdd);
            }
        } catch (Exception e) {
            logError(ctx, e);
        }
        VillagerNames.CONFIG_MANAGER.save();
        return 1;
    }

    private static int executeRemove(CommandContext<ServerCommandSource> ctx, List<String> listToRemoveFrom, String toRemove, String displayText, String name) throws CommandSyntaxException {
        if (listToRemoveFrom.contains(toRemove)){
            listToRemoveFrom.remove(toRemove);
            String text = String.format(displayText, toRemove);
            ctx.getSource().sendFeedback(() -> Text.literal(text)
                    .formatted(Formatting.GOLD)
                    .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
                            , Text.literal("Remove a name from the villager or golem name list")))
                            .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/villagername remove " + name + " " + toRemove)))
                    , true);
        }
        else{
            ctx.getSource().getPlayer().sendMessage(Text.literal("The villager or golem list you tried to remove from does not have that name").formatted(Formatting.RED), false);
        }

        try {
            if (name.contains("villagerNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.REMOVE, VillagerNamesConfig.class.getDeclaredField(name), ctx.getSource().getPlayer(), toRemove);
            } else if (name.contains("golemNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.REMOVE, GolemNamesConfig.class.getDeclaredField(name), ctx.getSource().getPlayer(), toRemove);
            } else if (name.contains("sureNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.REMOVE, SureNamesConfig.class.getDeclaredField(name), ctx.getSource().getPlayer(), toRemove);
            } else if (name.contains("piglinNames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.REMOVE, PiglinNamesConfig.class.getDeclaredField(name), ctx.getSource().getPlayer(), toRemove);
            } else if (name.contains("piglinSurnames")) {
                broadCastConfigChangeToOps(ctx, ConfigChange.REMOVE, PiglinSurnamesConfig.class.getDeclaredField(name), ctx.getSource().getPlayer(), toRemove);
            }
        } catch (Exception e) {
            logError(ctx, e);
        }
        VillagerNames.CONFIG_MANAGER.save();
        return 1;
    }

    private static int executeInfo(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        try {
            for (Field field : VillagerGeneralConfig.class.getDeclaredFields()) {
                ctx.getSource().getPlayer().sendMessage(
                        Text.literal(field.getName() + " = " + field.get(VillagerNames.CONFIG.villagerGeneralConfig).toString())
                                .styled(style -> style.withClickEvent(
                                        FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ?
                                                new ClickEvent(ClickEvent.Action.OPEN_FILE, FabricLoader.getInstance().getConfigDir() + "\\VillagerNames\\" + "villagerRules.json")
                                                : new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/villagername info"))), false);


            }
        } catch (IllegalAccessException ex){
            logError(ctx, ex);
        }
        return 1;
    }

    private static void broadCastConfigChangeToOps(CommandContext<ServerCommandSource> ctx, ConfigChange change, Field field, ServerPlayerEntity executor, @Nullable String addedItem) throws IllegalAccessException {
        MutableText text;
        switch (change){
            case SET:{
                text = (MutableText) Text.literal("The " + field.getName() + " has been set to \""
                        + field.get(VillagerNames.CONFIG.villagerGeneralConfig).toString() + "\" by "
                        + executor.getName().getString() + ".").formatted(field.getName().equals("villagerTextFormatting")
                        ? FormattingDummy.valueOf(field.get(VillagerNames.CONFIG.villagerGeneralConfig).toString()).getFormatting()
                        : Formatting.LIGHT_PURPLE);
                break;
            }
            case ADD: {
                text = (MutableText) Text.literal("The " + field.getName() + " has had the name \"" + addedItem + "\" added to the " + field.getName() + " by " + executor.getName().getString() + ".").formatted(Formatting.AQUA);
                break;
            }
            case TOGGLE: {
                text = (MutableText) Text.literal("The " + field.getName() + " has been toggled to " + field.get(VillagerNames.CONFIG.villagerGeneralConfig).toString() + " by " + executor.getName().getString() + ".").formatted(Formatting.GRAY);
                break;
            }
            case REMOVE: {
                text = (MutableText) Text.literal("The " + field.getName() + " has had the name \"" + addedItem + "\" removed from it by " + executor.getName().getString() + ".").formatted(Formatting.YELLOW);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + change);
        }
        addConfigText(text);
        sendToOps(ctx, text);
    }

    private static void addConfigText(MutableText text){
        text.append(Text.literal(" Any changes to the config require a server restart.")
                .formatted(Formatting.ITALIC, Formatting.GRAY))
                .append(Text.literal(" Would you like to restart the server?")
                        .formatted(Formatting.BOLD, Formatting.GOLD).styled(style ->
                                style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stop"))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                Text.literal("⚠ WARNING! YOU HAVE TO RESTART THE SERVER BY YOURSELF! ⚠").formatted(Formatting.RED)))));
    }

    private static void sendToOps(CommandContext<ServerCommandSource> ctx, Text text){
        ctx.getSource().getServer().getPlayerManager().getPlayerList().forEach((serverPlayerEntity -> {
            if (ctx.getSource().getServer().getPlayerManager().isOperator(serverPlayerEntity.getGameProfile())){
                serverPlayerEntity.sendMessage(text, false);
            }
        }));
    }

    private static String parseBoolean(boolean rule) {
        if (rule) {
            return "on";
        }
        return "off";
    }

    private static void logError(CommandContext<ServerCommandSource> ctx, Exception e) throws CommandSyntaxException {
        if (ctx.getSource().getPlayer() != null) {
            ctx.getSource().getPlayer().sendMessage(Text.literal("Exception Thrown! Exception: " + Throwables.getRootCause(e)), false);
        }
        e.printStackTrace();
    }


    public enum ConfigChange {
        SET,
        TOGGLE,
        ADD,
        REMOVE,
    }
}
