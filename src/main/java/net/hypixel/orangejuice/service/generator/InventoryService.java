package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.MinecraftInventoryGenerator;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class InventoryService {

    public static GeneratedObject generate(
        @Nullable String inventoryString,
        int rows,
        int slotsPerRow,
        @Nullable String hoveredItemString,
        @Nullable String containerName,
        @Nullable Boolean drawBorder
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
            MinecraftTooltipGenerator tooltipGenerator = new MinecraftTooltipGenerator.Builder()
                .withItemLore(hoveredItemString)
                .withAlpha(MinecraftTooltip.DEFAULT_ALPHA)
                .withPadding(MinecraftTooltip.DEFAULT_PADDING)
                .hasFirstLinePadding(false)
                // TODO find correct way for below
                //.disableRarityLineBreak(false)
                .withRenderBorder(true)
                .build();

            generatedObject.addGenerator(tooltipGenerator);
        }

        return generatedObject.build();
    }
}