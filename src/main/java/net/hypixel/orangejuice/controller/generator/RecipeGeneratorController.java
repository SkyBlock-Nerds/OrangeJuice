package net.hypixel.orangejuice.controller.generator;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;
import net.hypixel.orangejuice.service.generator.RecipeService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.hypixel.orangejuice.requestmodel.generator.RecipeGeneratorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/generator/recipe")
@Tag(name = "Recipe Generator", description = "APIs for recipe generation")
public class RecipeGeneratorController {

    @Operation(summary = "Generate recipe", description = "Generates a recipe image based on the provided request")
    @PostMapping("")
    public ResponseEntity generate(@RequestBody RecipeGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                RecipeService.generate(
                    InventoryItem.toStringFromArray(request.getRecipe()),
                    request.getRenderBackground()
                )
            );
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}