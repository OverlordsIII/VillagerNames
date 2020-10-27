package io.github.overlordsiii.villagernames.mixin;

import io.github.overlordsiii.villagernames.config.FormattingDummy;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(value = SelectionListEntry.class, remap = false)
public abstract class SelectionListEntryMixin<T> {
    @Shadow private Function<T, Text> nameProvider;

    @Inject(method = "<init>(Lnet/minecraft/text/Text;[Ljava/lang/Object;Ljava/lang/Object;Lnet/minecraft/text/Text;Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Function;Ljava/util/function/Supplier;Z)V", at = @At("TAIL"), remap = false)
    private void injectTime(Text fieldName, T[] valuesArray, T value, Text resetButtonKey, Supplier<T> defaultValue, Consumer<T> saveConsumer, Function<T, Text> nameProvider, Supplier<Optional<Text[]>> tooltipSupplier, boolean requiresRestart, CallbackInfo ci){
        this.nameProvider = nameProvider == null ? (t -> new TranslatableText(t instanceof SelectionListEntry.Translatable ? ((SelectionListEntry.Translatable) t).getKey() : t.toString()).formatted(FormattingDummy.valueOf(t.toString()).getFormatting())) : nameProvider;
    }
}
