package dev.erpix.tiruka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class responsible for dynamically loading dependencies at runtime
 * and appending them to the system classloader
 */
public class DependencyLoader {

    private static final Logger logger = LoggerFactory.getLogger(DependencyLoader.class);

    /**
     * Load the specified dependency into the system classloader
     *
     * @param dependency The dependency to load
     */
    public void load(Dependency dependency) {
        String artifactId = dependency.getArtifactId();
        String groupId = dependency.getGroupId();
        String version = dependency.getVersion();


        File libs = TirukaApp.getInstance().getDataDirectory().resolve("libs").toFile();
        if (!libs.exists()) {
            try {
                logger.info("Missing libs directory, creating...");
                Files.createDirectory(libs.toPath());
            } catch (IOException e) {
                logger.error("Failed to create libs directory", e);
            }
        }

        File jar = libs.toPath().resolve(artifactId + "-" + version + ".jar").toFile();
        if (!jar.exists()) {
            logger.info("Downloading missing dependency from Maven Central: {}-{}.jar", artifactId, version);
            String mavenUrl = getMavenUrl(groupId, artifactId, version);
            try {
                Files.copy(new URI(mavenUrl).toURL().openStream(), jar.toPath());
            } catch (IOException | URISyntaxException e) {
                logger.error("Failed to download dependencies", e);
            }
        }

        // Hack to open internal package for reflection access in JDK 17 and above
        // Used to append classpath to system classloader for dynamic loading
        try {
            // Since Java 9, the system classloader is a BuiltinClassLoader
            // We need to access the appendClassPath method via reflection
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> builtinClassLoader = classLoader.getClass().getSuperclass();

            // Lookup the appendClassPath method
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(builtinClassLoader, MethodHandles.lookup());
            MethodHandle appendClassPath = lookup.findVirtual(
                    builtinClassLoader, "appendClassPath", MethodType.methodType(void.class, String.class));

            // Invoke the appendClassPath method to load the dependency to the internal URLClassLoader
            appendClassPath.invoke(classLoader, jar.getAbsolutePath());
        } catch (Throwable t) {
            logger.error("Failed to append classpath to system classloader", t);
        }
    }

    /**
     * Load all the specified dependencies into the system classloader
     *
     * @param dependencies The dependencies to load
     */
    public void loadAll(Dependency... dependencies) {
        for (Dependency dependency : dependencies) {
            load(dependency);
        }
    }

    /**
     * Get the Maven Central URL for the specified dependency
     *
     * @param groupId    The group ID of the dependency
     * @param artifactId The artifact ID of the dependency
     * @param version    The version of the dependency
     * @return The Maven Central URL for the dependency
     */
    private String getMavenUrl(String groupId, String artifactId, String version) {
        return "https://repo1.maven.org/maven2/" +
                groupId.replace(".", "/") +
                "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar";
    }

}
