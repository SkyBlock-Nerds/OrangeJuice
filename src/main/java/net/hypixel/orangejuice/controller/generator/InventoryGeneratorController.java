package net.hypixel.orangejuice.controller.generator;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;
import net.hypixel.orangejuice.service.generator.InventoryService;
import net.hypixel.orangejuice.util.HttpUtil;
import net.hypixel.orangejuice.requestmodel.generator.InventoryGeneratorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/generator/inventory")
@Tag(name = "Inventory Generator", description = "APIs for inventory generation")
public class InventoryGeneratorController {

    @Operation(summary = "Generate inventory", description = "Generates an inventory image based on the provided request")
    @PostMapping("")
    public ResponseEntity generate(@RequestBody InventoryGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                InventoryService.generate(
                    InventoryItem.toStringFromArray(request.getInventoryItems()), // TODO: this should be done lower down the stack but too lazy to do it now (also dont understand the code fully)
                    request.getRows(),
                    request.getColumns(),
                    request.getHoveredItemString(),
                    request.getContainerName(),
                    request.getRenderBorder()
                )
            );
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}