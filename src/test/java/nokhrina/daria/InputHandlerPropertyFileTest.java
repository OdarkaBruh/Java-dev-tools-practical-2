package nokhrina.daria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 *
 */
public class InputHandlerPropertyFileTest {
    private final InputHandler inputHandler = new InputHandler();
    private static final String PATH = "src/main/resources/JUnitTest/InputHandler/";

    private static final String VALID_FOLDER = "Valid/";
    private static final String NOT_VALID_FOLDER = "NotValid/";

    private void runThrows(String name) {
        try {
            inputHandler.createNumberContainer(name);
        } catch (Exception e) {
            if (!e.getClass().equals(IllegalArgumentException.class)) Assertions.fail(e);
            else return;
        }
        Assertions.fail("Run didn't throw an error. ");
    }

    private NumberContainer runDoesNotThrow(String name) {
        try {
            return inputHandler.createNumberContainer(name);
        } catch (IOException e) {
            Assertions.fail(e);
        }
        Assertions.fail();
        return null;
    }


    //TODO commentar: does not check the result
    @Test
    @DisplayName("Basic Test - Check all possible states of properties file.")
    public void checkPossibleStates() {
        inputHandler.setPath(PATH+VALID_FOLDER);
        runDoesNotThrow("Correct");
        runDoesNotThrow("ExtraProperties");
        runDoesNotThrow("WrongOrder");

        inputHandler.setPath(PATH+NOT_VALID_FOLDER);
        runThrows("EmptyFile");
        runThrows("NotComplete");
        runThrows("NotCompleteExtra");
    }

    private NumberContainer createContainer(String filename) {
        NumberContainer numberContainer = runDoesNotThrow(filename);
        if (numberContainer == null) {
            Assertions.fail();
        }
        return numberContainer;
    }

    @Test
    public void checkWhetherNumberAreReadCorrectly() {
        inputHandler.setPath(PATH+VALID_FOLDER);

        NumberContainer numberContainer = createContainer("Correct");
        Assertions.assertEquals(1, numberContainer.getMin().intValue());
        Assertions.assertEquals(10, numberContainer.getMax().intValue());
        Assertions.assertEquals(2, numberContainer.getStep().intValue());

        //TODO float is behaving weird??
        numberContainer = createContainer("CorrectFloat");
        Assertions.assertEquals(-1.2, numberContainer.getMin().doubleValue());
        Assertions.assertEquals(10.123456789, numberContainer.getMax().doubleValue());
        Assertions.assertEquals(0.0023, numberContainer.getStep().doubleValue());

        //TODO math const?
    }

}
