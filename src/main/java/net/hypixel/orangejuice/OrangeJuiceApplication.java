package net.hypixel.orangejuice;

import lombok.extern.slf4j.Slf4j;
import net.aerh.imagegenerator.pack.PackLimits;
import net.aerh.imagegenerator.pack.PackRepository;
import net.aerh.imagegenerator.pack.PackSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class OrangeJuiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeJuiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void AppReady() {

        String pathStr = System.getProperty("McPackFolderPath");
        Path McPackFolderPath = Path.of(pathStr == null ? "/app/packs" : pathStr);
        if (!McPackFolderPath.toFile().exists()) {
            log.warn("McPackFolderPath '{}' does not exist, not loading any packs", McPackFolderPath);
        } else {
            try (Stream<Path> paths = Files.list(McPackFolderPath)) {
                paths.forEach(path -> {
                    if (Files.isDirectory(path)) {
                        try {
                            log.info("Attempting to load pack '{}'", path.getFileName().toString());
                            LoadPack(path);
                        } catch (Exception e) {
                            log.warn("Failed to load pack '{}'. Reason: {}", path.getFileName().toString(), e.getMessage());
                        }
                    } else {
                        log.warn("File '{}' is not a directory, can't load this pack (unzip first if it is a zip)", path.getFileName().toString());
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /// Attempts to load a pack into the {@link PackRepository}. <br/>
    /// Expects pack root folders that have a "name" and "namespace" separated by one of the following chars: '_' or ':'
    private void LoadPack(Path path) throws Exception {
        String dirName = path.getFileName().toString();

        boolean sawSeparator = false;
        for (char c : dirName.toCharArray()) {
            if (c == '_' || c == ':') {
                if (sawSeparator) {
                    throw new Exception("Pack folder has multiple separator characters ('_' or ':')");
                } else {
                    sawSeparator = true;
                }
            }
        }
        if (!sawSeparator) {
            throw new Exception("Pack folder doesn't have any separator characters ('_' or ':')");
        }
        PackRepository.global().register(
            dirName.replace('_', ':'),
            PackSource.directory(
                path,
                new PackLimits(20000, 1024 * 5, 128, 1024 * 1024 * 10)
            )
        );
    }
}
