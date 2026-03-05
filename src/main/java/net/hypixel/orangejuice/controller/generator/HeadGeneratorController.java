package net.hypixel.orangejuice.controller.generator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.requestmodel.generator.HeadGeneratorRequest;
import net.hypixel.orangejuice.service.generator.HeadService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/generator/head")
@Tag(name = "Head Generator", description = "APIs for head generation")
public class HeadGeneratorController {

    @Operation(summary = "Generate head", description = "Generates a head image based on the provided request")
    @PostMapping
    public ResponseEntity generate(@RequestBody HeadGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                    HeadService.generate(request.getSkinValue())
            );
        } catch (Exception exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}
