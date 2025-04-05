package dev.erpix.tiruka.logging.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import dev.erpix.tiruka.console.Console;

public class TerminalAppender extends AppenderBase<ILoggingEvent> {

    private final Layout<ILoggingEvent> layout = new LoggerLayout();

    @Override
    protected void append(ILoggingEvent event) {
        Console.LINE_READER.printAbove(layout.doLayout(event));
    }

}
