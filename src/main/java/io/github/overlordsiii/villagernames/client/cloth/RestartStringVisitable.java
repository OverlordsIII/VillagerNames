package io.github.overlordsiii.villagernames.client.cloth;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class RestartStringVisitable implements StringVisitable {
    /**
     * Supplies this visitable's literal content to the visitor.
     *
     * @param visitor the visitor
     * @return {@code Optional.empty()} if the visit finished, or a terminating
     * result from the {@code visitor}
     */
    @Override
    public <T> Optional<T> visit(Visitor<T> visitor) {
        return visitor.accept("⚠ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART⚠");
    }

    /**
     * Supplies this visitable's literal content and contextual style to
     * the visitor.
     *
     * @param styledVisitor the visitor
     * @param style         the contextual style
     * @return {@code Optional.empty()} if the visit finished, or a terminating
     * result from the {@code visitor}
     */
    @Override
    public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
        return styledVisitor.accept(style.withFormatting(Formatting.RED), "⚠ANY CHANGES TO THIS CONFIG REQUIRE A SERVER RESTART⚠");
    }
}
