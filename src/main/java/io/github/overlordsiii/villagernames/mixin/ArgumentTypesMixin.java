package io.github.overlordsiii.villagernames.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import io.github.overlordsiii.villagernames.command.argument.FormattingArgumentType;
import io.github.overlordsiii.villagernames.command.argument.NameArgumentType;
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

    @SuppressWarnings("EmptyMethod")
    @Shadow
    public static <T extends ArgumentType<?>> void register(String id, Class<T> class_, ArgumentSerializer<T> argumentSerializer) {
    }


    @Inject(method = "register()V", at = @At("HEAD"))
    private static void registerOurArgumentTypes(CallbackInfo ci){
        register("villagernames:formattingarg", FormattingArgumentType.class, new ConstantArgumentSerializer<>(FormattingArgumentType::format));
        register("villagernames:villagername", NameArgumentType.Villager.class, new ConstantArgumentSerializer<>(NameArgumentType::villagerName));
        register("villagernames:golemname", NameArgumentType.Golem.class, new ConstantArgumentSerializer<>(NameArgumentType::golemName));
        register("villagernames:surename", NameArgumentType.Surename.class, new ConstantArgumentSerializer<>(NameArgumentType::sureName));
    }



}
