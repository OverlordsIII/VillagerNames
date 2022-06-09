package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.client.cloth.RestartStringVisitable;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


@Environment(EnvType.CLIENT)
@Mixin(value = ConfigScreenProvider.class)
public abstract class ConfigScreenProviderMixin {

    @ModifyVariable(method = "get*",at = @At(value = "INVOKE", target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BEFORE, ordinal = 1), remap = false)
    private ConfigBuilder addVillagerConfigWarning(ConfigBuilder builder){
       boolean bl = false;
       String[] category = new String[]{"Villager Name Rules", "Villager Names", "Golem Names", "Last Names", "Piglin Names", "Piglin Surnames"};
       MutableText[] text = new MutableText[category.length];
       for (String t : ((ConfigBuilderImplAccessor)builder).getCategoryMap().keySet()) {
               for (int i = 0; i < category.length; i++){
                   if (t.contains(category[i])){
                       text[i] = Text.translatable(t);
                       bl = true;
                   }
               }
           }

       if (bl){
           for (int i = 0; i < category.length; i++){
               if (text[i] != null){
                   ConfigCategory configCategory = builder.getOrCreateCategory(text[i]);
                   configCategory.setDescription(new StringVisitable[]{
                       new RestartStringVisitable()
                   });
                   ConfigEntryBuilder builder1 = builder.entryBuilder();
                   configCategory.addEntry(builder1.startTextDescription(Text.literal("⚠ ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART ⚠").formatted(Formatting.RED)).setColor(16733525).build());
               }
           }
       }
       return builder;
   }

}
