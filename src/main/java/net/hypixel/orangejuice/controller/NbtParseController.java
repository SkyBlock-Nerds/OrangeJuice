package net.hypixel.orangejuice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.requestmodel.generator.TooltipGeneratorRequest;
import net.hypixel.orangejuice.service.NbtService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/nbtparse")
@Tag(name = "NBT Parse", description = "APIs for parsing NBT strings into images")
public class NbtParseController {

    @Operation(summary = "Parse NBT string", description = "Parses an NBT string and returns an image representation of it")
    @PostMapping("")
    public ResponseEntity nbtParse(
        @RequestBody String nbt,
        @RequestParam(required = false) @Nullable Integer alpha,
        @RequestParam(required = false) @Nullable Integer padding
    ) {
        try {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(NbtService.parseNbtString(nbt, alpha, padding));
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}