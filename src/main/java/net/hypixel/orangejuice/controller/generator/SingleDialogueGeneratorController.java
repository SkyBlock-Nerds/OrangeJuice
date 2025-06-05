package net.hypixel.orangejuice.controller.generator;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.service.generator.SingleDialogueService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.hypixel.orangejuice.requestmodel.generator.SingleDialogueGeneratorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/generator/dialogue/single")
@Tag(name = "Single Dialogue Generator", description = "APIs for single dialogue generation")
public class SingleDialogueGeneratorController {

    @Operation(summary = "Generate single dialogue", description = "Generates a single dialogue image based on the provided request")
    @PostMapping("")
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
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}