package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.data.Rarity;
import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.MinecraftInventoryGenerator;
import net.aerh.imagegenerator.impl.MinecraftItemGenerator;
import net.aerh.imagegenerator.impl.MinecraftPlayerHeadGenerator;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class TooltipService extends GeneratorService {

    public static GeneratedObject generate(
        String itemName,
        String itemLore,
        @Nullable String type,
        @Nullable String rarity,
        @Nullable String itemId,
        @Nullable String skinValue,
        @Nullable String recipe,
        @Nullable Integer alpha,
        @Nullable Integer padding,
        @Nullable Boolean enchanted,
        @Nullable Boolean centered,
        @Nullable Boolean paddingFirstLine,
        @Nullable Integer maxLineLength,
        @Nullable String tooltipSide,
        @Nullable Boolean renderBorder,
        @Nullable String texturePack,
        @Nullable String tooltipStyle
    ) {
        type = type == null ? "" : type;
        rarity = StringUtil.isNullOrBlank(rarity) ? "none" : rarity;
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;
        centered = centered != null && centered;
        enchanted = enchanted != null && enchanted;
        paddingFirstLine = paddingFirstLine == null || paddingFirstLine;
        maxLineLength = maxLineLength == null ? MinecraftTooltipGenerator.DEFAULT_MAX_LINE_LENGTH : maxLineLength;
        renderBorder = renderBorder == null || renderBorder;

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();
        PackId packId = StringUtil.isNullOrBlank(texturePack) ? null : PackId.parse(texturePack);
        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withName(itemName)
            .withRarity(Rarity.byName(rarity))
            .withItemLore(itemLore)
            .withType(type)
            .withAlpha(alpha)
            .withPadding(padding)
            .withMaxLineLength(maxLineLength)
            .isTextCentered(centered)
            .hasFirstLinePadding(paddingFirstLine)
            .withRenderBorder(renderBorder)
            .withTooltipStyle(tooltipStyle)
            .withPack(packId);

        if (ShouldApplyHypixelSkyblockTextColor(packId)) {
            tooltipGenerator = tooltipGenerator.withTextColorRemap(InventoryService.SKYBLOCK_TEXT_COLOR_REMAP);
        }

        if (!StringUtil.isNullOrBlank(itemId)) {
            if (itemId.equalsIgnoreCase("player_head")) {
                MinecraftPlayerHeadGenerator.Builder generator = new MinecraftPlayerHeadGenerator.Builder()
                    .withScale(-2);

                if (skinValue != null) {
                    generator.withSkin(skinValue);
                }

                generatorImageBuilder.addGenerator(generator.build());
            } else {
                generatorImageBuilder.addGenerator(new MinecraftItemGenerator.Builder()
                    .withItem(itemId)
                    .isEnchanted(enchanted)
                    // .isBigImage() TODO figure out where this went
                    .build());
            }
        }

        if (!StringUtil.isNullOrBlank(recipe)) {
            generatorImageBuilder.addGenerator(0, new MinecraftInventoryGenerator.Builder()
                .withRows(3)
                .withSlotsPerRow(3)
                .drawBorder(renderBorder)
                .withInventoryString(recipe)
                .build()
            ).build();
        }

        if (tooltipSide != null && MinecraftTooltipGenerator.TooltipSide.valueOf(tooltipSide.toUpperCase()) == MinecraftTooltipGenerator.TooltipSide.LEFT) {
            generatorImageBuilder.addGenerator(0, tooltipGenerator.build());
        } else {
            generatorImageBuilder.addGenerator(tooltipGenerator.build());
        }

        return generatorImageBuilder.build();
    }
}
