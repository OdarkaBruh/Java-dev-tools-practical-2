package nokhrina.daria;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import nokhrina.daria.MultiplicationLogic.MultiplicationHandler;
import nokhrina.daria.NumberContainer.NumberContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NumberContainerMultiplyTest {
    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    
    @BeforeAll
    static void innitLoggerAppender() {
        ch.qos.logback.classic.Logger loggerContainer = (ch.qos.logback.classic.Logger)
                LoggerFactory.getLogger(MultiplicationHandler.class);
        listAppender.start();
        loggerContainer.addAppender(listAppender);
    }

    @Test
    @DisplayName("Basic Multiply Test - Screenshot")
    void screenshotTest() {
        NumberContainer[] testValues = {
                new NumberContainer("10", "25", "4", Integer.class)};

        int[] result = {100, 140, 180, 220};
        for (NumberContainer container : testValues) {
            MultiplicationHandler handler = new MultiplicationHandler(container);
            for (var i = 0; i < 4; i++) {
                handler.multiplyOnce();
                Assertions.assertEquals(handler.getLastResult().intValue(), result[i]);
            }
        }
    }

    @Test
    @DisplayName("Basic Test - integer negative values")
    void basicNegativeTest() {
        NumberContainer[] testValues = {
                new NumberContainer("-1", "10", "2", Integer.class)};

        int[] result = {1, -1, -3, -5};
        for (NumberContainer container : testValues) {
            MultiplicationHandler handler = new MultiplicationHandler(container);
            for (var i = 0; i < 4; i++) {
                handler.multiplyOnce();
                Assertions.assertEquals(result[i], handler.getLastResult().intValue());
            }
        }
    }

    @Test
    @DisplayName("Basic Test - warn test")
    void basicCheckMultiplyWarningTest() {
        NumberContainer container =  new NumberContainer("-1", "10", "2", Integer.class);
        MultiplicationHandler handler = new MultiplicationHandler(container);

        for (var i = 0; i < 7; i++) handler.multiplyOnce();

        List<ILoggingEvent> logsList = listAppender.list;

        Assertions.assertEquals("The multiplier exceeded the maximum value.", logsList.getLast().getMessage());
        Assertions.assertEquals(1, logsList.size());

        container =  new NumberContainer("120", "130", "10", Byte.class);
        handler = new MultiplicationHandler(container);
        for (var i = 0; i < 3; i++) handler.multiplyOnce();

        Assertions.assertTrue(logsList.get(1).getMessage().matches(".*exceeded the maximum value for this type.*"));
        Assertions.assertEquals("The multiplier exceeded the maximum value.",
                logsList.get(logsList.size()-2).getMessage());
        Assertions.assertEquals(5, logsList.size());
    }
}
