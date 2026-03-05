package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.impl.MinecraftInventoryGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class RecipeService {

    public static GeneratedObject generate(
        String recipe,
        @Nullable Boolean renderBackground
    ) {
        renderBackground = renderBackground == null || renderBackground;

        return new GeneratorImageBuilder()
            .addGenerator(new MinecraftInventoryGenerator.Builder()
                .withRows(3)
                .withSlotsPerRow(3)
                .drawBorder(false)
                .drawBackground(renderBackground)
                .withInventoryString(recipe)
                .build())
            .build();
    }
}
