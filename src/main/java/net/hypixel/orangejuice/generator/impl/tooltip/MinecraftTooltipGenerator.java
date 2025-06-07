package net.hypixel.orangejuice.generator.impl.tooltip;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.Generator;
import net.hypixel.orangejuice.generator.builder.ClassBuilder;
import net.hypixel.orangejuice.generator.data.Rarity;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.generator.text.ChatFormat;
import net.hypixel.orangejuice.generator.text.segment.LineSegment;
import net.hypixel.orangejuice.generator.text.wrapper.TextWrapper;
import net.hypixel.orangejuice.requestmodel.generator.TooltipGeneratorRequest;
import net.hypixel.orangejuice.util.ImageUtil;
import net.hypixel.orangejuice.util.model.Range;
import net.hypixel.orangejuice.util.Util;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftTooltipGenerator implements Generator {

    public static final int DEFAULT_MAX_LINE_LENGTH = 36;

    private final String name;
    private final Rarity rarity;
    private final String itemLore;
    private final String type;
    private final boolean disableRarityLineBreak;
    private final int alpha;
    private final int padding;
    private final boolean normalItem;
    private final boolean centeredText;
    private final int maxLineLength;
    private final boolean renderBorder;

    @Override
    public GeneratedObject generate() throws GeneratorException {
        TooltipSettings settings = new TooltipSettings(
            name,
            disableRarityLineBreak,
            type,
            alpha,
            padding,
            normalItem,
            maxLineLength,
            renderBorder,
            centeredText
        );

        MinecraftTooltip tooltip = parseLore(itemLore, settings).render();

        if (tooltip.isAnimated()) {
            try {
                byte[] gifData = ImageUtil.toGifBytes(tooltip.getAnimationFrames(), tooltip.getFrameDelayMs(), true);
                return new GeneratedObject(gifData, tooltip.getAnimationFrames(), tooltip.getFrameDelayMs());
            } catch (IOException e) {
                throw new GeneratorException("Failed to generate animated tooltip GIF", e);
            }
        } else {
            return new GeneratedObject(tooltip.getImage());
        }
    }

    /**
     * Parses the lore of the item and applies the {@link TooltipSettings} settings.
     *
     * @param input    The lore to parse
     * @param settings The {@link TooltipSettings} to apply
     *
     * @return The parsed {@link MinecraftTooltip}
     */
    public MinecraftTooltip parseLore(String input, TooltipSettings settings) {
        log.debug("Parsing lore for item: {} with TooltipSettings: {}", name, settings);

        MinecraftTooltip.Builder builder = MinecraftTooltip.builder()
            .withPadding(settings.getPadding())
            .isPaddingFirstLine(settings.isPaddingFirstLine())
            .setRenderBorder(settings.isRenderBorder())
            .isTextCentered(settings.isCenteredText())
            .withAlpha(Range.between(0, 255).fit(settings.getAlpha()));

        if (settings.getName() != null && !settings.getName().isEmpty()) {
            String name = settings.getName();

            if (rarity != null && rarity != Rarity.byName("NONE")) {
                name = rarity.getColorCode() + name;
            }

            builder.withLines(LineSegment.fromLegacy(TextWrapper.parseLine(name), '&'));
        }

        List<List<LineSegment>> segments = new ArrayList<>();

        for (String line : TextWrapper.wrapString(input, settings.getMaxLineLength())) {
            segments.add(LineSegment.fromLegacy(line, '&'));
        }

        for (List<LineSegment> line : segments) {
            builder.withLines(line);
        }

        if (rarity != null && rarity != Rarity.byName("NONE")) {
            if (settings.isDisableRarityLineBreak()) {
                builder.withRarityLineBreak();
            }

            String formattedType = settings.getType() == null || settings.getType().isEmpty() ? "" : " " + settings.getType();
            builder.withLines(LineSegment.fromLegacy(rarity.getFormattedDisplay() + formattedType, '&'));
        }

        return builder.build();
    }

    public enum TooltipSide {
        LEFT,
        RIGHT
    }

    public static class Builder implements ClassBuilder<MinecraftTooltipGenerator> {
        private String name;
        private Rarity rarity;
        private String itemLore;
        private String type;
        private Boolean disableRarityLineBreak;
        private Integer alpha = MinecraftTooltip.DEFAULT_ALPHA;
        private Integer padding = 0;
        private boolean paddingFirstLine = true;
        private int maxLineLength = DEFAULT_MAX_LINE_LENGTH;
        private transient boolean bypassMaxLineLength;
        private boolean centered;
        private boolean renderBorder = true;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Builder withItemLore(String itemLore) {
            this.itemLore = itemLore;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder disableRarityLineBreak(boolean disableRarityLineBreak) {
            this.disableRarityLineBreak = disableRarityLineBreak;
            return this;
        }

        public Builder withAlpha(int alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder withPadding(int padding) {
            this.padding = padding;
            return this;
        }

        public Builder isPaddingFirstLine(boolean paddingFirstLine) {
            this.paddingFirstLine = paddingFirstLine;
            return this;
        }

        public Builder isTextCentered(boolean centered) {
            this.centered = centered;
            return this;
        }

        public Builder withMaxLineLength(int maxLineLength) {
            if (bypassMaxLineLength) {
                this.maxLineLength = maxLineLength;
            } else {
                this.maxLineLength = MinecraftTooltip.LINE_LENGTH.fit(maxLineLength);
            }
            return this;
        }

        public Builder bypassMaxLineLength(boolean bypassMaxLineLength) {
            this.bypassMaxLineLength = bypassMaxLineLength;
            return this;
        }

        public Builder withRenderBorder(boolean renderBorder) {
            this.renderBorder = renderBorder;
            return this;
        }

        // TODO support components
        public Builder parseNbtJson(JsonObject nbtJson) {
            this.disableRarityLineBreak = false;
            this.paddingFirstLine = false;
            this.centered = false;
            this.rarity = Rarity.byName("NONE");
            this.itemLore = "";
            this.renderBorder = true;
            this.alpha = MinecraftTooltip.DEFAULT_ALPHA;
            this.padding = 0;

            JsonObject tagObject = nbtJson.get("tag").getAsJsonObject();
            JsonObject displayObject = tagObject.get("display").getAsJsonObject();
            this.name = displayObject.getAsJsonObject().get("Name").getAsString();

            displayObject.getAsJsonObject().get("Lore").getAsJsonArray().forEach(jsonElement -> {
                this.itemLore += jsonElement.getAsString() + "\\n";
            });

            this.itemLore = this.itemLore.replaceAll(String.valueOf(ChatFormat.SECTION_SYMBOL), String.valueOf(ChatFormat.AMPERSAND_SYMBOL));

            return this;
        }

        /**
         * Request model from the current state of the builder.
         * Some values in the request model may be null if not set in the builder or not directly related to a tooltip.
         *
         * @return A {@link TooltipGeneratorRequest} populated with the current builder's data.
         */
        public TooltipGeneratorRequest buildTooltipGeneratorRequest() {
            TooltipGeneratorRequest request = new TooltipGeneratorRequest();
            request.setItemName(name);
            request.setItemLore(itemLore);
            request.setType(type);
            request.setRarity(rarity != null ? rarity.getName() : null);
            request.setItemId(null);
            request.setSkinValue(null);
            request.setRecipe(null);
            request.setAlpha(alpha);
            request.setPadding(padding);
            request.setDisableRarityLineBreak(disableRarityLineBreak);
            request.setEnchanted(false);
            request.setCentered(centered);
            request.setPaddingFirstLine(paddingFirstLine);
            request.setMaxLineLength(maxLineLength);
            request.setTooltipSide(null);
            request.setRenderBorder(renderBorder);

            return request;
        }

        @Override
        public MinecraftTooltipGenerator build() {
            return new MinecraftTooltipGenerator(
                name,
                rarity,
                itemLore,
                type,
                disableRarityLineBreak,
                alpha,
                padding,
                paddingFirstLine,
                centered,
                maxLineLength,
                renderBorder
            );
        }
    }
}