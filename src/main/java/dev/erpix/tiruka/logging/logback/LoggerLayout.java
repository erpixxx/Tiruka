package dev.erpix.tiruka.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import dev.erpix.tiruka.utils.Colors;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LoggerLayout extends PatternLayout {

    @Override
    public String doLayout(ILoggingEvent event) {
        return time(event.getTimeStamp()) +
                loggerAndThread(event.getLoggerName(), event.getThreadName()) +
                level(event.getLevel(), event.getFormattedMessage(), event.getThrowableProxy());
    }

    private String time(long timestamp) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return new AttributedStringBuilder()
                .style(AttributedStyle.BOLD.foregroundRgb(Colors.CONSOLE_TIME))
                .append("[")
                .append(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .append("] ")
                .toAnsi();
    }

    private String loggerAndThread(String loggerName, String threadName) {
        AttributedStyle style = AttributedStyle.BOLD.foregroundRgb(Colors.CONSOLE_LOGGER_THREAD);
        String className = loggerName.substring(loggerName.lastIndexOf('.') + 1);

        return new AttributedStringBuilder()
                .style(style)
                .append("[").append(className).append("/").append(threadName).append("] ")
                .toAnsi();
    }

    private String level(Level level, String text, IThrowableProxy throwable) {
        AttributedStyle levelStyle = switch (level.toInt()) {
            case Level.INFO_INT -> AttributedStyle.BOLD.foreground(AttributedStyle.BLUE);
            case Level.WARN_INT -> AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
            case Level.ERROR_INT -> AttributedStyle.DEFAULT.foreground(AttributedStyle.RED);
            case Level.DEBUG_INT -> AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN);
            case Level.TRACE_INT -> AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN);
            default -> AttributedStyle.DEFAULT;
        };
        AttributedStyle messageStyle = switch (level.toInt()) {
            case Level.WARN_INT -> AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW);
            case Level.ERROR_INT -> AttributedStyle.BOLD.foreground(AttributedStyle.RED);
            default -> AttributedStyle.DEFAULT;
        };

        AttributedStringBuilder result = new AttributedStringBuilder()
                .append(new AttributedStringBuilder().style(levelStyle)
                        .append("[").append(level.toString()).append("]: "))
                .append(new AttributedStringBuilder().style(messageStyle).append(text));

        if (throwable != null) {
            result.append(new AttributedStringBuilder().style(messageStyle)
                    .append("\n").append(ThrowableProxyUtil.asString(throwable)));
        }

        return result.toAnsi();
    }

}
