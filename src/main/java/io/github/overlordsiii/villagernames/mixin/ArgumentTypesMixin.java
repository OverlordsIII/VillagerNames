package io.github.overlordsiii.villagernames.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import io.github.overlordsiii.villagernames.command.argument.FormattingArgumentType;
import io.github.overlordsiii.villagernames.command.argument.GolemNameArgumentType;
import io.github.overlordsiii.villagernames.command.argument.VillagerNameArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArgumentTypes.class)
public abstract class ArgumentTypesMixin {
    @Shadow
    public static <T extends ArgumentType<?>> void register(String id, Class<T> class_, ArgumentSerializer<T> argumentSerializer) {
    }


    @Inject(method = "register()V", at = @At("HEAD"))
    private static void registerOurArgumentTypes(CallbackInfo ci){
        register("formattingarg", FormattingArgumentType.class, new ConstantArgumentSerializer<>(FormattingArgumentType::format));
        register("villagername", VillagerNameArgumentType.class, new ConstantArgumentSerializer<>(VillagerNameArgumentType::villagerName));
        register("golemname", GolemNameArgumentType.class, new ConstantArgumentSerializer<>(GolemNameArgumentType::golemName));
    }

}
