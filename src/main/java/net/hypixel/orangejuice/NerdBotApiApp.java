package net.hypixel.orangejuice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hypixel.orangejuice.config.AppConfig;
import net.hypixel.orangejuice.internalapi.Environment;
import net.hypixel.orangejuice.metrics.PrometheusMetrics;
import net.hypixel.orangejuice.util.JsonUtil;
import net.hypixel.orangejuice.util.Util;
import net.hypixel.orangejuice.util.json.adapter.ColorTypeAdapter;
import net.hypixel.orangejuice.util.json.adapter.InstantTypeAdapter;
import net.hypixel.orangejuice.util.json.adapter.UUIDTypeAdapter;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Log4j2
@SpringBootApplication
public class NerdBotApiApp {

    @Getter
    private static AppConfig config;

    private static final String CERT_DIR = "/etc/ssl/certs";
    private static final String KEYSTORE_PATH = "/app/keystore.p12";
    private static final String ALIAS = "ssl-certificate";
    private static final String KEYSTORE_PASSWORD;

    static {
        KEYSTORE_PASSWORD = Util.isNullOrBlank(System.getProperty("server.ssl.key-store-password")) ? "default-password" : System.getProperty("server.ssl.key-store-password");
    }

    public static final Gson GSON = new GsonBuilder() // TODO: check if all adapters are needed (i (socks) dont understand gson)
        .setPrettyPrinting()
        .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
        .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
        .registerTypeAdapter(Color.class, new ColorTypeAdapter())
        .create();

    public static void main(String[] args) {
        loadConfig();

        System.setProperty("swagger.enabled", String.valueOf(config.isSwaggerEnabled()));

        if (config.getMetricsConfig().isEnabled()) {
            PrometheusMetrics.enableMetrics();
        }

        try {
            setupSSL();
        } catch (RuntimeException e) {
            log.warn("Could not set up SSL, continuing without SSL.", e);
        }

        SpringApplication.run(NerdBotApiApp.class, args);
    }

    private static void loadConfig() {
        String fileName;
        if (System.getProperty("api.config") != null) {
            fileName = System.getProperty("api.config");
            log.info("Found config property: " + fileName);
        } else {
            log.info("Config property not defined, going to default path!");
            fileName = Environment.getEnvironment().name().toLowerCase() + ".config.json";
        }

        try {
            log.info("Loading config file from '" + fileName + "'");
            File file = new File(fileName);
            config = (AppConfig) JsonUtil.jsonToObject(file, AppConfig.class);

            log.info("Loaded config from " + file.getAbsolutePath());
        } catch (FileNotFoundException exception) {
            log.error("Could not find config file " + fileName);
            System.exit(-1);
        }
    }

    public static void setupSSL() {
        File fullchain = new File(CERT_DIR, "fullchain.pem");
        File privkey = new File(CERT_DIR, "privkey.pem");
        File chain = new File(CERT_DIR, "chain.pem");

        log.info("keystore pass: " + KEYSTORE_PASSWORD);
        log.info("fullchain can read: " + fullchain.canRead());
        log.info("privkey can read: " + privkey.canRead());
        log.info("chain can read: " + chain.canRead());

        if (fullchain.exists() && privkey.exists() && chain.exists()) {
            log.info("Starting SSL setup");
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                    "openssl", "pkcs12", "-export",
                    "-in", fullchain.getAbsolutePath(),
                    "-inkey", privkey.getAbsolutePath(),
                    "-out", KEYSTORE_PATH,
                    "-name", ALIAS,
                    "-CAfile", chain.getAbsolutePath(),
                    "-caname", "root",
                    "-passout", "pass:" + KEYSTORE_PASSWORD
                );
                processBuilder.inheritIO();
                Process process = processBuilder.start();
                if (process.waitFor() == 0) {
                    System.setProperty("server.ssl.key-store", KEYSTORE_PATH);
                    System.setProperty("server.ssl.key-store-type", "PKCS12");
                    System.setProperty("server.ssl.key-alias", ALIAS);
                } else {
                    throw new RuntimeException("Failed to generate keystore");
                }
                log.info("Finished SSL setup");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error setting up SSL", e);
            }
        } else {
            throw new RuntimeException("Certificate files not found in " + CERT_DIR);
        }
    }
}