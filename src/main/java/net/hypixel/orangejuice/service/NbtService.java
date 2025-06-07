package net.hypixel.orangejuice.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.MinecraftItemGenerator;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.requestmodel.generator.TooltipGeneratorRequest;
import net.hypixel.orangejuice.util.ImageUtil;
import net.hypixel.orangejuice.util.Util;
import net.hypixel.orangejuice.util.exception.NbtParseException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class NbtService {

    public static ParsedNbt parseNbtString(
        String nbt,
        @Nullable Integer alpha,
        @Nullable Integer padding
    ) throws IOException {
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;

        JsonObject jsonObject = JsonParser.parseString(nbt).getAsJsonObject();
        JsonObject tagObject = jsonObject.get("tag").getAsJsonObject();
        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();

        if (jsonObject.get("id").getAsString().contains("skull")) {
            String value = jsonObject.get("id").getAsString();
            value = value.replace("minecraft:", "")
                .replace("skull", "player_head");
            jsonObject.addProperty("id", value);
        }

        if (jsonObject.get("id").getAsString().equalsIgnoreCase("player_head")
            && tagObject.get("SkullOwner") != null) {
            JsonArray textures = tagObject.get("SkullOwner").getAsJsonObject()
                .get("Properties").getAsJsonObject()
                .get("textures").getAsJsonArray();

            if (textures.size() > 1) {
                throw new NbtParseException("Multiple textures found for player head! Please provide a single texture.");
            }

            String base64 = textures.get(0).getAsJsonObject().get("Value").getAsString();

            generatorImageBuilder.addGenerator(new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(base64)
                .build()
            );
        } else {
            generatorImageBuilder.addGenerator(new MinecraftItemGenerator.Builder()
                .withItem(jsonObject.get("id").getAsString())
                //.isEnchanted(enchanted) TODO: determine if the item is enchanted
                .isBigImage()
                .build());
        }

        int maxLineLength = Util.getLongestLine(jsonObject.get("tag").getAsJsonObject()
            .get("display").getAsJsonObject()
            .get("Lore").getAsJsonArray()
            .asList()
            .stream()
            .map(JsonElement::getAsString)
            .toList()).getRight();

        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .parseNbtJson(jsonObject)
            .withAlpha(alpha)
            .withPadding(padding)
            .withRenderBorder(true)
            .isPaddingFirstLine(true)
            .withMaxLineLength(maxLineLength);

        GeneratedObject generatedObject = generatorImageBuilder.addGenerator(tooltipGenerator.build()).build();

        return new ParsedNbt(
            tooltipGenerator.buildTooltipGeneratorRequest(),
            generatedObject.isAnimated() ? generatedObject.getGifData() : ImageUtil.toByteArray(generatedObject.getImage())
        );
    }

    @Getter
    protected static class ParsedNbt {
        private final TooltipGeneratorRequest TooltipGeneratorRequest;
        private final byte[] image; // Can also be a GIF. Yes an GIF is an image, I don't care about your feelings

        public ParsedNbt(TooltipGeneratorRequest tooltipGeneratorRequest, byte[] image) {
            this.TooltipGeneratorRequest = tooltipGeneratorRequest;
            this.image = image;
        }
    }
}