package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.image.MinecraftTooltip;
import net.aerh.imagegenerator.impl.MinecraftPlayerHeadGenerator;
import net.aerh.imagegenerator.impl.tooltip.MinecraftTooltipGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.aerh.imagegenerator.text.TextColorRemap;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GeneratorService {
    public static final TextColorRemap SKYBLOCK_TEXT_COLOR_REMAP =
        TextColorRemap.builder()
            .remap(Integer.parseInt("AA0000", 16), Integer.parseInt("D13228", 16))
            .remap(Integer.parseInt("FFAA00", 16), Integer.parseInt("FF9000", 16))
            .remap(Integer.parseInt("FFFF55", 16), Integer.parseInt("FFDE2F", 16))
            .remap(Integer.parseInt("0000AA", 16), Integer.parseInt("353FCE", 16))
            .remap(Integer.parseInt("5555FF", 16), Integer.parseInt("459BFF", 16))
            .remap(Integer.parseInt("AA00AA", 16), Integer.parseInt("A335EE", 16))
            .remap(Integer.parseInt("AAAAAA", 16), Integer.parseInt("A8BFD2", 16))
            .remap(Integer.parseInt("555555", 16), Integer.parseInt("707592", 16))
            .build();

    public static boolean ShouldApplyHypixelSkyblockTextColor(@Nullable PackId packId) {
        if (packId == null || StringUtil.isNullOrBlank(packId.toString())) return false;
        return packId.equals(new PackId("hypixel", "skyblock"));
    }

    protected static GeneratedObject internalGenerateDialogue(String[] dialogue, @NotNull Integer maxLineLength, @Nullable String skinValue, @Nullable String texturePack) {
        PackId packId = StringUtil.isNullOrBlank(texturePack) ? null : PackId.parse(texturePack);
        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withItemLore(String.join("\n", dialogue))
            .withAlpha(0)
            .withRenderBorder(false)
            .withPadding(MinecraftTooltip.DEFAULT_PADDING)
            .hasFirstLinePadding(false)
            .withMaxLineLength(maxLineLength)
            .withPack(packId)
            .bypassMaxLineLength(true);

        if (ShouldApplyHypixelSkyblockTextColor(packId)) {
            tooltipGenerator.withTextColorRemap(SKYBLOCK_TEXT_COLOR_REMAP);
        }

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder()
            .addGenerator(tooltipGenerator.build());

        if (skinValue != null) {
            MinecraftPlayerHeadGenerator playerHeadGenerator = new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(skinValue)
                .withScale(-2)
                .build();
            generatorImageBuilder.addGenerator(0, playerHeadGenerator);
        }

        return generatorImageBuilder.build();
    }
}
