package nokhrina.daria;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NumberContainerMultiplyTest {
    Logger logger = LoggerFactory.getLogger(NumberContainerMultiplyTest.class);

    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    
    @BeforeAll
    static void innitLoggerAppender() {
        ch.qos.logback.classic.Logger loggerContainer = (ch.qos.logback.classic.Logger)
                LoggerFactory.getLogger(NumberContainer.class);
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
            container.setIsDecimal(false);
            for (var i = 0; i < 4; i++) {
                container.multiply();
                Assertions.assertEquals(container.getLastResult().intValue(), result[i]);
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
            container.setIsDecimal(false);
            for (var i = 0; i < 4; i++) {
                container.multiply();
                Assertions.assertEquals(result[i], container.getLastResult().intValue());
            }
        }
    }

    @Test
    @DisplayName("Basic Test - warn test")
    void basicCheckMultiplyWarningTest() {
        NumberContainer container =  new NumberContainer("-1", "10", "2", Integer.class);
        container.setIsDecimal(false);

        for (var i = 0; i < 7; i++) {
            container.multiply();
        }

        List<ILoggingEvent> logsList = listAppender.list;

        Assertions.assertEquals("The multiplier exceeded the maximum value.", logsList.getLast().getMessage());
        Assertions.assertEquals(1, logsList.size());

        container =  new NumberContainer("120", "130", "10", Byte.class);
        container.setIsDecimal(false);
        for (var i = 0; i < 3; i++) {
            container.multiply();
        }

        Assertions.assertTrue(logsList.get(1).getMessage().matches(".*exceeded the maximum value for this type.*"));
        Assertions.assertEquals("The multiplier exceeded the maximum value.",
                logsList.get(logsList.size()-2).getMessage());
        Assertions.assertEquals(5, logsList.size());
    }
}
