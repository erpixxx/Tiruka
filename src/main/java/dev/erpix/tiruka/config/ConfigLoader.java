package dev.erpix.tiruka.config;

import dev.erpix.tiruka.TirukaApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    private static final String CONFIG_NAME = "config.yml";

    public static ConfigSchema load() {
        Path configFile = TirukaApp.getInstance().getDataDirectory().resolve(CONFIG_NAME);

        if (!Files.exists(configFile)) {
            try (InputStream resource = TirukaApp.getInstance().getResourceAsStream(CONFIG_NAME)) {
                if (resource == null)
                    throw new ConfigLoadException("Default resource '" + CONFIG_NAME + "' not found.");
                Files.copy(resource, configFile);
                logger.info("Deployed default config file");
            } catch (IOException e) {
                throw new ConfigLoadException("Failed to deploy default config file", e);
            }
        }

        try (FileReader reader = new FileReader(configFile.toFile())) {
            Constructor constructor = new Constructor(ConfigSchema.class, new LoaderOptions());
            constructor.setPropertyUtils(new PropertyUtils() {
                @Override
                public Property getProperty(Class<?> type, String name) {
                    setSkipMissingProperties(true);
                    name = YamlPropertyToFieldConverter.convert(name);
                    return super.getProperty(type, name);
                }
            });
            Yaml yaml = new Yaml(constructor);

            try {
                ConfigSchema config = yaml.load(reader);
                logger.info("Successfully loaded config");

                return config;
            } catch (ConstructorException e) {
                throw new ConfigLoadException("Failed to load configuration file.", e);
            }
        } catch (IOException e) {
            throw new ConfigLoadException("Failed to load configuration file.", e);
        }

    }

}
