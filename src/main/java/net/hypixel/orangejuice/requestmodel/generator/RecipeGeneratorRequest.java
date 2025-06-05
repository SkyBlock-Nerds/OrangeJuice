package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.RECIPE_DESCRIPTION;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate a recipe")
@Data
public class RecipeGeneratorRequest {

    @Schema(description = RECIPE_DESCRIPTION, requiredMode = Schema.RequiredMode.REQUIRED)
    private InventoryItem[] recipe;

    @Schema(description = "Whether to render the background", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean renderBackground;
}