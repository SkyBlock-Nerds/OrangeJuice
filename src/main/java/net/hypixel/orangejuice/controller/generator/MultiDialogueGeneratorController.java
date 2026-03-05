package net.hypixel.orangejuice.controller.generator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.requestmodel.generator.MultiDialogueGeneratorRequest;
import net.hypixel.orangejuice.requestmodel.generator.SingleDialogueGeneratorRequest;
import net.hypixel.orangejuice.service.generator.MultiDialogueService;
import net.hypixel.orangejuice.service.generator.SingleDialogueService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/generator/dialogue")
@Tag(name = "Dialogue Generator", description = "APIs for dialogue generation")
public class MultiDialogueGeneratorController {

    @Operation(summary = "Generate single dialogue", description = "Generates a single dialogue image based on the provided request")
    @PostMapping("/single")
    public ResponseEntity generate(@RequestBody SingleDialogueGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                    SingleDialogueService.generate(
                            request.getNpcName(),
                            request.getDialogue(),
                            request.getMaxLineLength(),
                            request.getAbiphone(),
                            request.getSkinValue()
                    )
            );
        } catch (Exception exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }

    @Operation(summary = "Generate multi dialogue", description = "Generates a multi dialogue image based on the provided request")
    @PostMapping("/multi")
    public ResponseEntity generate(@RequestBody MultiDialogueGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                MultiDialogueService.generate(
                    request.getNpcNames(),
                    request.getDialogue(),
                    request.getMaxLineLength(),
                    request.getAbiphone(),
                    request.getSkinValue()
                )
            );
        } catch (Exception exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}