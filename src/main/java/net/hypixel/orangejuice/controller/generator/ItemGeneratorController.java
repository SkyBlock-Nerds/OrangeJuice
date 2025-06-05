package net.hypixel.orangejuice.controller.generator;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.requestmodel.generator.ItemGeneratorRequest;
import net.hypixel.orangejuice.service.generator.ItemService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/generator/item")
@Tag(name = "Item Generator", description = "APIs for item generation")
public class ItemGeneratorController {

    @Operation(summary = "Generate item", description = "Generates an item display image based on the provided request")
    @PostMapping("")
    public ResponseEntity generate(@RequestBody ItemGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                ItemService.generate(
                    request.getItemId(),
                    request.getData(),
                    request.getEnchanted(),
                    request.getHoverEffect(),
                    request.getSkinValue()
                )
            );
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating an item display", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}