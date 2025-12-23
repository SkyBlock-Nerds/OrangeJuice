package net.hypixel.orangejuice.service.generator;

import net.hypixel.nerdbot.generator.image.GeneratorImageBuilder;
import net.hypixel.nerdbot.generator.impl.MinecraftItemGenerator;
import net.hypixel.nerdbot.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.nerdbot.generator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class ItemService {

    public static GeneratedObject generate(
        String itemId,
        @Nullable String data,
        @Nullable Boolean enchanted,
        @Nullable Boolean hoverEffect,
        @Nullable String skinValue
    ) {
        enchanted = enchanted != null && enchanted;
        hoverEffect = hoverEffect != null && hoverEffect;

        GeneratorImageBuilder item = new GeneratorImageBuilder();

        if (itemId.equalsIgnoreCase("player_head") && skinValue != null) {
            item.addGenerator(new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(skinValue)
                .build());
        } else {
            item.addGenerator(new MinecraftItemGenerator.Builder()
                .withItem(itemId)
                .withData(data)
                .isEnchanted(enchanted)
                .withHoverEffect(hoverEffect)
                .isBigImage()
                .build()
            );
        }

        return item.build();
    }
}
