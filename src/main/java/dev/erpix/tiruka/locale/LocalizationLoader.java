package dev.erpix.tiruka.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.erpix.tiruka.Tiruka;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Stream;

// TODO: Catch FileNotFoundException
public final class LocalizationLoader {

    private static final Logger logger = LoggerFactory.getLogger(LocalizationLoader.class);

    public static void load(String locales) {
        ClassLoader classLoader = LocalizationLoader.class.getClassLoader();

        Path localesDir = new File(System.getProperty("user.dir")).toPath().resolve("locales");
        Path languageFile = localesDir.resolve(locales + ".properties");

        if (!Files.exists(localesDir)) {
            try {
                Files.createDirectory(localesDir);

                URI uri = classLoader.getResource("locales/").toURI();
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                Path sourcePath = Path.of(uri);

                try (Stream<Path> paths = Files.walk(sourcePath, 1)) {
                    paths.filter(Files::isRegularFile)
                            .forEach(f -> {
                                try {
                                    Path destinationFile = localesDir.resolve(f.getFileName().toString());

                                    try (InputStream in = Files.newInputStream(f)) {
                                        Files.copy(in, destinationFile);
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException("Error copying locales files", e);
                                }
                            });
                }

                fileSystem.close();
                logger.info("Deployed default locales files");
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException("Failed to create locales directory", e);
            }
        }

        try (Reader reader = new FileReader(languageFile.toFile())) {
            Properties properties = new Properties();
            properties.load(reader);
            Language.load(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
