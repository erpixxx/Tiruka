package dev.erpix.tiruka;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public final class Tiruka {

    private static final Logger logger = LoggerFactory.getLogger(Tiruka.class);

    public static void main(String[] args) {
        try {
            OptionParser optionParser = new OptionParser() {{
                acceptsAll(Arrays.asList("?", "help"));

                acceptsAll(Arrays.asList("t", "token", "app-token"), "Specifies the application token")
                        .withRequiredArg()
                        .ofType(String.class);

                accepts("logger.level", "Specifies the logger level")
                        .withRequiredArg()
                        .ofType(String.class)
                        .defaultsTo("INFO");
            }};

            try {
                OptionSet options = optionParser.parse(args);
                if (options.has("?")) {
                    try {
                        optionParser.printHelpOn(System.out);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                else if (options.has("logger.level")) {
                    String loggerLevel = (String) options.valueOf("logger.level");
                    Level level = Level.toLevel(loggerLevel, Level.INFO);

                    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                    ch.qos.logback.classic.Logger logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
                    logger.setLevel(level);
                }

                new TirukaApp(options);
            } catch (OptionException e) {
                logger.error("There's problem with specified arguments", e);
            }
        } catch (Throwable t) {
            logger.error("An unexpected error occurred:", t);
        }
    }

}
