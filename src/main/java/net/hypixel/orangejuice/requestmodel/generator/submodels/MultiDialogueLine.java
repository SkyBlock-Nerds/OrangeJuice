package net.hypixel.orangejuice.requestmodel.generator.submodels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MultiDialogueLine {

    @Schema(description = "Npc index according to which you first put in", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer npcIndex;

    @Schema(description = "The dialogue line", example = "Hello, how are you?", requiredMode = Schema.RequiredMode.REQUIRED)
    private String line;
}