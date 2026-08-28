package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.impl.MinecraftInventoryGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class RecipeService extends GeneratorService {

    public static GeneratedObject generate(
        String recipe,
        @Nullable Boolean renderBackground,
        @Nullable String texturePack
    ) {
        renderBackground = renderBackground == null || renderBackground;

        PackId packId = getPackId(texturePack);

        return new GeneratorImageBuilder()
            .addGenerator(new MinecraftInventoryGenerator.Builder()
                .withRows(3)
                .withSlotsPerRow(3)
                .drawBorder(false)
                .drawBackground(renderBackground)
                .withInventoryString(recipe)
                .withPack(packId)
                .build())
            .build();
    }
}
