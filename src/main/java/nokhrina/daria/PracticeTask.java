package nokhrina.daria;

import java.io.IOException;

import static nokhrina.daria.NamesConstants.*;

public class PracticeTask {
    private final PropertyReader propertyReader = new PropertyReader();
    private final InputHandler inputHandler = new InputHandler();

    void runTask() throws IOException {
        Class<? extends Number> typeClass = inputHandler.readType(DEFAULT_NAME_OF_SYSTEM_VARIABLE);
        NumberContainer numbersContainer = inputHandler.getNumberContainer(typeClass);

    }

    void verifyNumbersAreInLimits(double upperLimit, double lowerLimit, NumberContainer numbersContainer) {

    }

    void createTable() {

    }
}
