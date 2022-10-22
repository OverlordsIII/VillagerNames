package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.config.FormattingDummy;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(value = SelectionListEntry.class)
public abstract class SelectionListEntryMixin<T> {
    @Shadow @Final @Mutable
    private Function<T, Text> nameProvider;


    @Inject(method = "<init>(Lnet/minecraft/text/Text;[Ljava/lang/Object;Ljava/lang/Object;Lnet/minecraft/text/Text;Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Function;Ljava/util/function/Supplier;Z)V", at = @At("TAIL"))
    private void injectingToCreateFormatting(Text fieldName, T[] valuesArray, T value, Text resetButtonKey, Supplier<T> defaultValue, Consumer<T> saveConsumer, Function<T, Text> nameProvider, Supplier<Optional<Text[]>> tooltipSupplier, boolean requiresRestart, CallbackInfo ci){
        if (nameProvider == null) {
           this.nameProvider = (t -> {
               MutableText text;
               if (t instanceof SelectionListEntry.Translatable){
                   text = new TranslatableText(((SelectionListEntry.Translatable) t).getKey());
                   ArrayList<String> formattingDummyAsStrings = new ArrayList<>();
                   for (FormattingDummy dummy : FormattingDummy.values()){
                       formattingDummyAsStrings.add(dummy.toString());
                   }
                   if (formattingDummyAsStrings.contains(t.toString())){
                       text = text.formatted(FormattingDummy.valueOf(((SelectionListEntry.Translatable)t).getKey()).getFormatting());
                   }
               } else {
                   text = new TranslatableText(t.toString());
                   ArrayList<String> formattingDummyAsStrings = new ArrayList<>();
                   for (FormattingDummy dummy : FormattingDummy.values()){
                       formattingDummyAsStrings.add(dummy.toString());
                   }
                   if (formattingDummyAsStrings.contains(t.toString())){
                       text = text.formatted(FormattingDummy.valueOf(t.toString()).getFormatting());
                   }
               }

             return text;
           });
        } else {
            this.nameProvider = nameProvider;
        }
    }
}
