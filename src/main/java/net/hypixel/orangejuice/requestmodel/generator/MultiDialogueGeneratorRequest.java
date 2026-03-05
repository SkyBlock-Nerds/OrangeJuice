package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.hypixel.orangejuice.requestmodel.generator.submodels.MultiDialogueLine;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.*;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate multi npc dialogue")
@Data
public class MultiDialogueGeneratorRequest {

    @Schema(description = "Names of the npcs", example = "[\"Your best friend\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private String[] npcNames;

    @Schema(description = DIALOGUE_DESCRIPTION, requiredMode = Schema.RequiredMode.REQUIRED)
    private MultiDialogueLine[] dialogue;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer maxLineLength;

    @Schema(description = ABIPHONE_DESCRIPTION, example = ABIPHONE_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean abiphone;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String skinValue;
}