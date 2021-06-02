package io.github.overlordsiii.villagernames.mixin;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.ConfigBuilderImpl;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = ConfigBuilderImpl.class, remap = false)
public interface ConfigBuilderImplAccessor {
    @Accessor("categoryMap")
    Map<Text, ConfigCategory> getCategoryMap();
}
