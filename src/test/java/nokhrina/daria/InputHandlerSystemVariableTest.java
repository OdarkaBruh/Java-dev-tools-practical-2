package nokhrina.daria;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InputHandlerSystemVariableTest {
    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeAll
    static void innitLoggerAppender() {
        Logger loggerContainer = (Logger) LoggerFactory.getLogger(InputHandler.class);
        listAppender.start();
        loggerContainer.addAppender(listAppender);
    }

    private void runThrows(String name, Class error) {
        try {
            new InputHandler().readType(name);
        } catch (Exception e) {
            if (!e.getClass().equals(error)) Assertions.fail(e);
            else return;
        }
        Assertions.fail("Run didn't throw an error. ");

    }

    private Class runDoesNotThrow(String name) {
        return new InputHandler().readType(name);
    }

    @Test
    @DisplayName("Warning Test - Check all possible states of System variable ")
    public void checkPossibleStates() {
        String gibberishName = "...";
        runDoesNotThrow(gibberishName); //reminder for myself: no, this doesn't need to be saved as a variable
        //Because we only need to check a logger message.

        List<ILoggingEvent> logsList = listAppender.list;
        if (logsList.isEmpty() || !logsList.getFirst().getFormattedMessage()
                .equals("Property containing type has not been read. Default type is assigned.")) {
            Assertions.fail();
        }
        String wrongExistingName = "java.vm.specification.vendor";
        runThrows(wrongExistingName, IllegalArgumentException.class);

        String correctSystemVarName = "type";
        System.setProperty(correctSystemVarName, "int");
        Assertions.assertEquals(Integer.class, runDoesNotThrow(correctSystemVarName));
    }
}
