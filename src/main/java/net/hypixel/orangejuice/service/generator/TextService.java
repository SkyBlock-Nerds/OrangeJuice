package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class TextService extends GeneratorService {

    public static GeneratedObject generate(
        String text,
        @Nullable Boolean centered,
        @Nullable Integer alpha,
        @Nullable Integer padding,
        @Nullable Integer maxLineLength,
        @Nullable Boolean renderBorder,
        @Nullable String texturePack,
        @Nullable String tooltipStyle
    ) {
        centered = centered != null && centered;
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;
        maxLineLength = maxLineLength == null ? MinecraftTooltipGenerator.DEFAULT_MAX_LINE_LENGTH : maxLineLength;
        renderBorder = renderBorder != null && renderBorder;

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();
        PackId packId = StringUtil.isNullOrBlank(texturePack) ? null : PackId.parse(texturePack);
        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withItemLore(text)
            .withAlpha(alpha)
            .withPadding(padding)
            .withMaxLineLength(maxLineLength)
            .isTextCentered(centered)
            .hasFirstLinePadding(false)
            .withRenderBorder(renderBorder)
            .withTooltipStyle(tooltipStyle)
            .withPack(packId);

        if (ShouldApplyHypixelSkyblockTextColor(packId)){
            tooltipGenerator = tooltipGenerator.withTextColorRemap(InventoryService.SKYBLOCK_TEXT_COLOR_REMAP);
        }

        generatorImageBuilder.addGenerator(tooltipGenerator.build());
        return generatorImageBuilder.build();
    }
}
