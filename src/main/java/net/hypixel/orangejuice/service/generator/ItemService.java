package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.impl.MinecraftItemGenerator;
import net.aerh.imagegenerator.impl.MinecraftPlayerHeadGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;
import net.aerh.imagegenerator.pack.PackId;
import net.hypixel.orangejuice.util.StringUtil;
import org.jetbrains.annotations.Nullable;

public class ItemService extends GeneratorService {

    public static GeneratedObject generate(
        String itemId,
        @Nullable String data,
        @Nullable Boolean enchanted,
        @Nullable Boolean hoverEffect,
        @Nullable String skinValue,
        @Nullable String texturePack
    ) {
        enchanted = enchanted != null && enchanted;
        hoverEffect = hoverEffect != null && hoverEffect;

        GeneratorImageBuilder item = new GeneratorImageBuilder();

        if (itemId.equalsIgnoreCase("player_head") && skinValue != null) {
            item.addGenerator(new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(skinValue)
                .build());
        } else {
            PackId packId = getPackId(texturePack);
            MinecraftItemGenerator.Builder itemGenerator = new MinecraftItemGenerator.Builder()
                .withItem(itemId)
                .withData(data)
                .isEnchanted(enchanted)
                .withHoverEffect(hoverEffect)
                // .isBigImage() TODO figure out where this went
                .withPack(packId);

            item.addGenerator(itemGenerator.build());
        }

        return item.build();
    }
}
