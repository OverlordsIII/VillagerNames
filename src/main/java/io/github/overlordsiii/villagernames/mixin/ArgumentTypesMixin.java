package io.github.overlordsiii.villagernames.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import io.github.overlordsiii.villagernames.command.argument.FormattingArgumentType;
import io.github.overlordsiii.villagernames.command.argument.NameArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ArgumentTypes.class)
public abstract class ArgumentTypesMixin {

    private static ArrayList<String> strings = Util.make(new ArrayList<>(), arrayList -> {
        arrayList.add("formattingarg");
        arrayList.add("villagername");
        arrayList.add("golemname");
        arrayList.add("surename");
    });

    @SuppressWarnings("EmptyMethod")
    @Shadow
    public static <T extends ArgumentType<?>> void register(String id, Class<T> class_, ArgumentSerializer<T> argumentSerializer) {
    }


    @Inject(method = "register()V", at = @At("HEAD"))
    private static void registerOurArgumentTypes(CallbackInfo ci){
        register("formattingarg", FormattingArgumentType.class, new ConstantArgumentSerializer<>(FormattingArgumentType::format));
        register("villagername", NameArgumentType.Villager.class, new ConstantArgumentSerializer<>(NameArgumentType::villagerName));
        register("golemname", NameArgumentType.Golem.class, new ConstantArgumentSerializer<>(NameArgumentType::golemName));
        register("surename", NameArgumentType.Surename.class, new ConstantArgumentSerializer<>(NameArgumentType::sureName));
    }

    @ModifyVariable(method = "register(Ljava/lang/String;Ljava/lang/Class;Lnet/minecraft/command/argument/serialize/ArgumentSerializer;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z", ordinal = 0), index = 3)
    private static Identifier changeIds(Identifier identifier) {

        if (strings.contains(identifier.getPath())) {
            return new Identifier("villagernames", identifier.getPath());
        }
        return identifier;
    }


}
