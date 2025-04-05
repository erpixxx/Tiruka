package dev.erpix.tiruka;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bootstrap class to start the main application process with the correct classpath.
 */
public final class TirukaBootstrap {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        String javaBin = System.getProperty("java.home") + "/bin/java";
        String jarPath = new File(
                TirukaBootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

        // Add --add-opens java.base/jdk.internal.loader=ALL-UNNAMED to avoid IllegalAccessError
        // (required for DependencyLoader to work)
        List<String> cmd = new ArrayList<>();
        cmd.add(javaBin);
        cmd.add("--add-opens");
        cmd.add("java.base/jdk.internal.loader=ALL-UNNAMED");
        cmd.add("-cp");
        cmd.add(jarPath);
        cmd.add(TirukaConstants.MAIN_CLASS);
        cmd.addAll(Arrays.asList(args)); // Pass the arguments to the main app
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.inheritIO();

        // Start the main application and wait for it to finish
        Process process = pb.start();
        Runtime.getRuntime().addShutdownHook(new Thread(process::destroy));
        process.waitFor();
    }

}
