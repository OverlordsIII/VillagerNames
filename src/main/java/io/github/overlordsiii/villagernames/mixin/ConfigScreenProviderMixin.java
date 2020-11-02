package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.client.cloth.RestartStringVisitable;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.gui.ConfigScreenProvider;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Environment(EnvType.CLIENT)
@Mixin(value = ConfigScreenProvider.class)
public abstract class ConfigScreenProviderMixin<T extends ConfigData> {

   @ModifyVariable(method = "get",at = @At(value = "INVOKE", target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BEFORE, ordinal = 1), remap = false)
    private ConfigBuilder aClass(ConfigBuilder builder){

       boolean bl = false;
       TranslatableText texter = null;
       TranslatableText texterer = null;
       TranslatableText texterererer = null;
       for (Text text : ((ConfigBuilderImplAccessor)builder).getCategoryMap().keySet()){
           if (text instanceof TranslatableText){
               TranslatableText translatableText = (TranslatableText)text;
               if (translatableText.getKey().contains("villagerGeneral")){
                   bl = true;
                   texter = (TranslatableText) text;
               } else if (translatableText.getKey().contains("villagerNames")){
                   bl = true;
                   texterer = (TranslatableText) text;
               } else if (translatableText.getKey().contains("golem")){
                   bl = true;
                   texterererer = (TranslatableText) text;
               }
           }
       }
       if (bl){
           if (texter != null) {
               ConfigCategory configCategory = builder.getOrCreateCategory(texter);
               configCategory.setDescription(new StringVisitable[]{
                       new RestartStringVisitable()
               });
               ConfigEntryBuilder builder1 = builder.entryBuilder();
               configCategory.addEntry(builder1.startTextDescription(new LiteralText("⚠ ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART ⚠").formatted(Formatting.RED)).setColor(16733525).build());
           } if (texterer != null){
               ConfigCategory configCategory = builder.getOrCreateCategory(texterer);
               configCategory.setDescription(new StringVisitable[]{
                       new RestartStringVisitable()
               });
               ConfigEntryBuilder builder1 = builder.entryBuilder();
               configCategory.addEntry(builder1.startTextDescription(new LiteralText("⚠ ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART ⚠").formatted(Formatting.RED)).setColor(16733525).build());
           } if (texterererer != null){
               ConfigCategory configCategory = builder.getOrCreateCategory(texterererer);
               configCategory.setDescription(new StringVisitable[]{
                       new RestartStringVisitable()
               });
               ConfigEntryBuilder builder1 = builder.entryBuilder();
              configCategory.addEntry(builder1.startTextDescription(new LiteralText("⚠ ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART ⚠").formatted(Formatting.RED)).setColor(16733525).build());
           }
       }
       return builder;
   }

}
