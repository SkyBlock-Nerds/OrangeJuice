package net.hypixel.orangejuice.service.generator;

import net.aerh.imagegenerator.image.GeneratorImageBuilder;
import net.aerh.imagegenerator.impl.MinecraftPlayerHeadGenerator;
import net.aerh.imagegenerator.item.GeneratedObject;

public class HeadService {

    public static GeneratedObject generate(String skinValue) {
        return new GeneratorImageBuilder()
            .addGenerator(new MinecraftPlayerHeadGenerator.Builder().withSkin(skinValue).build())
            .build();
    }
}