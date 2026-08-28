package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.MinecraftInventoryGenerator;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class InventoryService extends GeneratorService {

    public static GeneratedObject generate(
        @Nullable String inventoryString,
        int rows,
        int slotsPerRow,
        @Nullable String hoveredItemString,
        @Nullable String containerName,
        @Nullable Boolean drawBorder,
        @Nullable String texturePack,
        @Nullable String tooltipStyle
    ) {
        drawBorder = drawBorder == null || drawBorder;
        inventoryString = inventoryString == null ? "" : inventoryString;

        GeneratorImageBuilder generatedObject = new GeneratorImageBuilder()
            .addGenerator(new MinecraftInventoryGenerator.Builder()
                .withRows(rows)
                .withSlotsPerRow(slotsPerRow)
                .drawBorder(drawBorder)
                .drawBackground(true)
                .withContainerTitle(containerName)
                .withInventoryString(inventoryString)
                .build());

        if (hoveredItemString != null) {
            PackId packId = getPackId(texturePack);
            MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
                .withItemLore(hoveredItemString)
                .withAlpha(MinecraftTooltip.DEFAULT_ALPHA)
                .withPadding(MinecraftTooltip.DEFAULT_PADDING)
                .hasFirstLinePadding(false)
                .withRenderBorder(true)
                .withTooltipStyle(tooltipStyle)
                .withPack(packId);

            if (ShouldApplyHypixelSkyblockTextColor(packId)) {
                tooltipGenerator = tooltipGenerator.withTextColorRemap(InventoryService.SKYBLOCK_TEXT_COLOR_REMAP);
            }

            generatedObject.addGenerator(tooltipGenerator.build());
        }

        return generatedObject.build();
    }
}
