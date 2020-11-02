package io.github.overlordsiii.villagernames.mixin;

import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(TextListEntry.class)
public abstract class TextListEntryMixin {
    @Shadow private Text text;

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"), index = 2)
    private float changeX(float x){
        if (this.text.asString().contains("⚠")){
            return x + MinecraftClient.getInstance().getWindow().getWidth()/7F;
        }
        return x;
    }
}
