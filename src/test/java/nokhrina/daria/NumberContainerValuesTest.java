package nokhrina.daria;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import nokhrina.daria.NumberContainer.NumberContainer;
import nokhrina.daria.NumberContainer.NumberExceedsTypeLimitSize;
import nokhrina.daria.NumberContainer.Validator;
import nokhrina.daria.constants.TypeConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.CannotProceedException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NumberContainerValuesTest {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(NumberContainerValuesTest.class);
    private final Map<Class, BigDecimal> limitValues = TypeConstants.maxValues;
    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeAll
    static void innitLoggerAppender() {
        Logger loggerContainer = (Logger) LoggerFactory.getLogger(Validator.class);
        listAppender.start();
        loggerContainer.addAppender(listAppender);
    }

    @Test
    @DisplayName("Basic Test - all types doesn't throw")
    void basicTypeTest() {
        for (var entry : limitValues.entrySet()) {
            Assertions.assertDoesNotThrow(() -> {
                        NumberContainer container = new NumberContainer("1", "3", "1", entry.getKey());
                        new Validator(container).checkWhetherValuesAreInLimitsOfType();
                    }
            );
        }
    }

    @Test
    @DisplayName("Basic Test - int values")
    void normalValuesDecimal() {
        NumberContainer[] testValues = {
                new NumberContainer("1", "5", "1", Integer.class),
                new NumberContainer("-5", "5", "1", Integer.class),
                new NumberContainer("-10", "-5", "1", Integer.class)};

        for (NumberContainer container : testValues) {
            Assertions.assertDoesNotThrow(() -> new Validator(container).checkWhetherValuesAreInLimitsOfType());
        }
    }

    @Test
    @DisplayName("Exception test - Overflow the type")
    void warningExceedsType() {
        for (int i = 0; i < 4; i++) {
            boolean exceedsLeft = i < 2;
            boolean exceedsRight = i % 2 == 0;

            logger.trace("warningExceedsType [exceedsLeft: {}, exceedsRight:{}]", exceedsLeft, exceedsRight);
            for (var entry : limitValues.entrySet()) {
                BigDecimal maxValue = entry.getValue();
                if (exceedsLeft || exceedsRight) {
                    Assertions.assertThrows(NumberExceedsTypeLimitSize.class,
                            () -> {
                                NumberContainer container = createContainerLargerThanLimit(maxValue.negate(),
                                        maxValue, entry.getKey(), exceedsLeft, exceedsRight);
                                new Validator(container).checkWhetherValuesAreInLimitsOfType();
                            });
                } else {
                    Assertions.assertDoesNotThrow(() -> {
                        NumberContainer container = createContainerLargerThanLimit(maxValue.negate(),
                                maxValue, entry.getKey(), exceedsLeft, exceedsRight);
                        new Validator(container).checkWhetherValuesAreInLimitsOfType();
                    });
                }
            }
        }
    }

    private NumberContainer createContainerLargerThanLimit(BigDecimal min, BigDecimal max, Class type,
                                                           boolean offsetLeft, boolean offsetRight) {
        if (offsetLeft) min = min.subtract(BigDecimal.ONE);
        if (offsetRight) max = max.add(BigDecimal.ONE);

        return new NumberContainer(min, max, BigDecimal.ONE, type);
    }

    @Test
    @DisplayName("Warning Test - float to int transformation.")
    void warningFloatToInt() {
        NumberContainer container = new NumberContainer("2.7", "20.3", "0.1", Integer.class);
        new Validator(container).checkWhetherValuesAndTypeAreDecimal();

        List<ILoggingEvent> logsList = listAppender.list;
        try {
            for (int i = 0; i < 3; i++) {
                Assertions.assertTrue(logsList.get(i).getFormattedMessage()
                        .matches("Received number \\([a-zA-Z]{3,4}\\) is decimal, but type is not."));
            }
        } catch (Exception e) {
            Assertions.fail(e);
        }

        Assertions.assertEquals(new BigDecimal("27"), container.getMin());
        Assertions.assertEquals(new BigDecimal("203"), container.getMax());
        Assertions.assertEquals(new BigDecimal("1"), container.getStep());
    }

    /**
     * Tests all possible type states:
     * 1) Type is correct
     * 2) Type is not null, but is not Integer/Double/etc. either
     * 3) Type is null
     */
    @Test
    @DisplayName("General Test - float to int transformation.")
    void testAllTypeStates() {
        NumberContainer container1 = new NumberContainer("2.7", "20.3", "0.1", Integer.class);
        Assertions.assertDoesNotThrow(container1::check);

        NumberContainer container2 = new NumberContainer("2.7", "20.3", "0.1", String.class);
        Assertions.assertThrows(IllegalArgumentException.class, container2::check);

        NumberContainer container3 = new NumberContainer("2.7", "20.3", "0.1", null);
        Assertions.assertThrows(CannotProceedException.class, container3::check);
    }
}
