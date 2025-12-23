package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.*;

@Schema(description = "Request model for tooltip generation")
@Data
public class TooltipGeneratorRequest {
    @Schema(description = "Item name", example = "Sword of Legends", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String itemName;

    @Schema(description = "Item lore", example = "A legendary sword", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String itemLore;

    @Schema(description = "Item type", example = "SWORD", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String type;

    @Schema(description = "Item rarity", example = "legendary", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String rarity;

    @Schema(description = ITEM_ID_DESCRIPTION, example = ITEM_ID_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String itemId;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String skinValue;

    @Schema(description = RECIPE_DESCRIPTION, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private InventoryItem[] recipe;

    @Schema(description = ALPHA_DESCRIPTION, example = ALPHA_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer alpha;

    @Schema(description = PADDING_DESCRIPTION, example = PADDING_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer padding;

    @Schema(description = "Disable rarity line break", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean disableRarityLineBreak;

    @Schema(description = "Is enchanted", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean enchanted;

    @Schema(description = "Is centered", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean centered;

    @Schema(description = "Padding for the first line", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean paddingFirstLine;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer maxLineLength;

    @Schema(description = "Tooltip side", example = "left", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String tooltipSide;

    @Schema(description = "Render border", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean renderBorder;
}