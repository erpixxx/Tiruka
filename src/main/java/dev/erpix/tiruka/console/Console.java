package dev.erpix.tiruka.console;

import dev.erpix.tiruka.command.console.CommandResult;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.erpix.tiruka.TirukaApp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Console extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private static final String PROMPT = "> ";
    private static final String UNKNOWN_COMMAND_OUTPUT = new AttributedStringBuilder()
            .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
            .append("Unknown command. Type 'help' for a list of available commands.")
            .toAnsi();

    public static final Terminal TERMINAL;
    public static final LineReader LINE_READER;

    static {
        try {
            TERMINAL = TerminalBuilder.builder()
                    .encoding(StandardCharsets.UTF_8)
                    .system(true)
                    .build();
            LINE_READER = LineReaderBuilder.builder()
                    .terminal(TERMINAL)
                    .appName("Tiruka Console")
                    .history(new DefaultHistory())
                    .highlighter(new DefaultHighlighter())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize terminal", e);
        }
    }

    private final ConsoleCommandProcessor commandProcessor = new ConsoleCommandProcessor();
    private volatile boolean running = false;

    public Console() {
        setName("ConsoleThread");
    }

    public static void print(String message) {
        TERMINAL.writer().print(message);
    }

    public static void print(String message, Object... args) {
        TERMINAL.writer().print(String.format(message, args));
    }

    public static void println() {
        TERMINAL.writer().println();
    }

    public static void println(String message) {
        TERMINAL.writer().println(message);
    }

    public static void println(String message, Object... args) {
        TERMINAL.writer().println(String.format(message, args));
    }

    @Override
    public void run() {
        running = true;
        logger.info("Started {} ({})", TERMINAL.getName(), TERMINAL.getType());

        if (TERMINAL.getType().equals("windows-vtp"))
            logger.warn("Windows CMD/Powershell detected. Some features (like emoji) may not work correctly.");

        try {
            String cmd;
            while (running && (cmd = LINE_READER.readLine(PROMPT)) != null) {
                CommandResult result = commandProcessor.process(cmd);
                if (result.isUnknownCommand()) {
                    println(UNKNOWN_COMMAND_OUTPUT);
                }
                else if (result.hasErrors()) {
                    result.getErrors().forEach(Console::println);
                }
            }
        } catch (UserInterruptException e) {
            TirukaApp.getInstance().shutdownAwait();
        }
    }

    public void shutdown() {
        running = false;
    }

    public ConsoleCommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

}
