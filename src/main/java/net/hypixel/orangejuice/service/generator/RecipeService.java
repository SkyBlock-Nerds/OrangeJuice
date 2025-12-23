package net.hypixel.orangejuice.service.generator;

import net.hypixel.nerdbot.generator.image.GeneratorImageBuilder;
import net.hypixel.nerdbot.generator.impl.MinecraftInventoryGenerator;
import net.hypixel.nerdbot.generator.item.GeneratedObject;
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
