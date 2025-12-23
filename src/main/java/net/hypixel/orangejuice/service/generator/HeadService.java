package net.hypixel.orangejuice.service.generator;

import net.hypixel.nerdbot.generator.image.GeneratorImageBuilder;
import net.hypixel.nerdbot.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.nerdbot.generator.item.GeneratedObject;

public class HeadService {

    public static GeneratedObject generate(String skinValue) {
        return new GeneratorImageBuilder()
            .addGenerator(new MinecraftPlayerHeadGenerator.Builder().withSkin(skinValue).build())
            .build();
    }
}