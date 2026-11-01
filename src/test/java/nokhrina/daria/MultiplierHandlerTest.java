package nokhrina.daria;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import nokhrina.daria.MultiplicationLogic.MultiplicationHandler;
import nokhrina.daria.NumberContainer.NumberContainer;
import nokhrina.daria.NumberContainer.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import java.util.List;

public class MultiplierHandlerTest {
    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    private List<ILoggingEvent> logsList;

    @BeforeAll
    static void innitLoggerAppender() {
        Logger loggerContainer = (Logger) LoggerFactory.getLogger(MultiplicationHandler.class);
        listAppender.start();
        loggerContainer.addAppender(listAppender);
    }

    private void run(NumberContainer numberContainer) {
        new Validator(numberContainer).checkWhetherValuesAndTypeAreDecimal();
        new MultiplicationHandler(numberContainer).createTable();
        logsList = listAppender.list;
    }

    @Test
    @DisplayName("Basic test - check result to the screenshot example")
    public void screenshotTest() {
        run(new NumberContainer("10", "25", "4", Integer.class));

        Assertions.assertEquals("\n10 * 10 = 100 ‖ 10 * 14 = 140 ‖ 10 * 18 = 180 ‖ 10 * 22 = 220 ‖ \n" +
                        "14 * 14 = 196 ‖ 14 * 18 = 252 ‖ 14 * 22 = 308 ‖ \n" +
                        "18 * 18 = 324 ‖ 18 * 22 = 396 ‖ \n" +
                        "22 * 22 = 484 ‖ \n",
                logsList.getLast().getMessage());
    }

    @Test
    @DisplayName("Basic test - check result of the float values")
    public void floatTest() {
        run(new NumberContainer("-1.123", "2", "0.5", Double.class));
        Assertions.assertEquals(
                "\n-1.123 * -1.123 = 1.261129 ‖ -1.123 * -0.623 = 0.699629 ‖ -1.123 * -0.123 = 0.138129 ‖ -1.123 * 0.377 = -0.423371 ‖ -1.123 * 0.877 = -0.984871 ‖ -1.123 * 1.377 = -1.546371 ‖ -1.123 * 1.877 = -2.107871 ‖ \n" +
                        "-0.623 * -0.623 = 0.388129 ‖ -0.623 * -0.123 = 0.076629 ‖ -0.623 * 0.377 = -0.234871 ‖ -0.623 * 0.877 = -0.546371 ‖ -0.623 * 1.377 = -0.857871 ‖ -0.623 * 1.877 = -1.169371 ‖ \n" +
                        "-0.123 * -0.123 = 0.015129 ‖ -0.123 * 0.377 = -0.046371 ‖ -0.123 * 0.877 = -0.107871 ‖ -0.123 * 1.377 = -0.169371 ‖ -0.123 * 1.877 = -0.230871 ‖ \n" +
                        "0.377 * 0.377 = 0.142129   ‖ 0.377 * 0.877 = 0.330629   ‖ 0.377 * 1.377 = 0.519129   ‖ 0.377 * 1.877 = 0.707629   ‖ \n" +
                        "0.877 * 0.877 = 0.769129   ‖ 0.877 * 1.377 = 1.207629   ‖ 0.877 * 1.877 = 1.646129   ‖ \n" +
                        "1.377 * 1.377 = 1.896129   ‖ 1.377 * 1.877 = 2.584629   ‖ \n" +
                        "1.877 * 1.877 = 3.523129   ‖ \n",
                logsList.getLast().getMessage());

        run(new NumberContainer("-1.1", "2", "0.5", Double.class));
        Assertions.assertEquals(
                "\n-1.1 * -1.1 = 1.21 ‖ -1.1 * -0.6 = 0.66 ‖ -1.1 * -0.1 = 0.11 ‖ -1.1 * 0.4 = -0.44 ‖ -1.1 * 0.9 = -0.99 ‖ -1.1 * 1.4 = -1.54 ‖ -1.1 * 1.9 = -2.09 ‖ \n" +
                        "-0.6 * -0.6 = 0.36 ‖ -0.6 * -0.1 = 0.06 ‖ -0.6 * 0.4 = -0.24 ‖ -0.6 * 0.9 = -0.54 ‖ -0.6 * 1.4 = -0.84 ‖ -0.6 * 1.9 = -1.14 ‖ \n" +
                        "-0.1 * -0.1 = 0.01 ‖ -0.1 * 0.4 = -0.04 ‖ -0.1 * 0.9 = -0.09 ‖ -0.1 * 1.4 = -0.14 ‖ -0.1 * 1.9 = -0.19 ‖ \n" +
                        "0.4 * 0.4 = 0.16   ‖ 0.4 * 0.9 = 0.36   ‖ 0.4 * 1.4 = 0.56   ‖ 0.4 * 1.9 = 0.76   ‖ \n" +
                        "0.9 * 0.9 = 0.81   ‖ 0.9 * 1.4 = 1.26   ‖ 0.9 * 1.9 = 1.71   ‖ \n" +
                        "1.4 * 1.4 = 1.96   ‖ 1.4 * 1.9 = 2.66   ‖ \n" +
                        "1.9 * 1.9 = 3.61   ‖ \n",
                logsList.getLast().getMessage());
    }

    @Test
    @DisplayName("Basic Warn Test - Result exceeds the type")
    void warnAboutResultExceedsTest() {
        NumberContainer numberContainer = new NumberContainer("1", "200", "50", Byte.class);
        run(numberContainer);
        Assertions.assertEquals("The result displayed below exceeded the limit for this type.",
                logsList.get(logsList.size()-2).getMessage());
        Assertions.assertEquals("\n" +
                        "1 * 1 = 1            ‖ 1 * 51 = 51          ‖ 1 * 101 = 101        ‖ 1 * 151 = 151(!)     ‖ \n" +
                        "51 * 51 = 2601(!)    ‖ 51 * 101 = 5151(!)   ‖ 51 * 151 = 7701(!)   ‖ \n" +
                        "101 * 101 = 10201(!) ‖ 101 * 151 = 15251(!) ‖ \n" +
                        "151 * 151 = 22801(!) ‖ \n",
                logsList.getLast().getMessage());
    }

}
