package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.*;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request model for text generation")
@Data
public class TextGeneratorRequest {

    @Schema(description = "Text to generate", example = "Hello, Hydar?", requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

    @Schema(description = "Whether the text should be centered", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean centered;

    @Schema(description = ALPHA_DESCRIPTION, example = ALPHA_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer alpha;

    @Schema(description = PADDING_DESCRIPTION, example = PADDING_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer padding;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer maxLineLength;

    @Schema(description = RENDER_BORDER_DESCRIPTION, example = RENDER_BORDER_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean renderBorder;
}