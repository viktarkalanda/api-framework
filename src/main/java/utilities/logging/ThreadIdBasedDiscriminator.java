package utilities.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.Discriminator;

public class ThreadIdBasedDiscriminator implements Discriminator<ILoggingEvent> {
    private static final String KEY = "threadId";
    private static final String DEFAULT_POSTFIX = "common";
    private boolean started;

    @Override
    public String getDiscriminatingValue(ILoggingEvent iLoggingEvent) {
        String dateTimePostfix = iLoggingEvent.getMDCPropertyMap().get(String.valueOf(Thread.currentThread().getId()));
        return dateTimePostfix == null ? DEFAULT_POSTFIX : dateTimePostfix;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;
    }

    public boolean isStarted() {
        return started;
    }
}
