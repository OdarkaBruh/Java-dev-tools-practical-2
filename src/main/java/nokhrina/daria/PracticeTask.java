package nokhrina.daria;

import nokhrina.daria.MultiplicationLogic.MultiplicationHandler;
import nokhrina.daria.NumberContainer.NumberContainer;
import nokhrina.daria.constants.NameConstants;
import nokhrina.daria.IO.InputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.CannotProceedException;
import java.io.IOException;

public class PracticeTask {
    /** Main logger */
    private final Logger logger = LoggerFactory.getLogger(PracticeTask.class);
    /** Class which will handle receiving information from property file and system properties */
    private final InputHandler inputHandler = new InputHandler(NameConstants.PROPERTY_FILE_PATH);

    void runTask(String propertyFileName, String nameOfSystemVariable) {
        try {
            NumberContainer numbersContainer = inputHandler.createNumberContainer(propertyFileName);
            numbersContainer.setType(inputHandler.readType(nameOfSystemVariable));
            numbersContainer.check();
            new MultiplicationHandler(numbersContainer).createTable();
        } catch (NumberFormatException e) {
            //TODO: CHECK
        } catch (IllegalArgumentException e) {
            if (e.getMessage().matches("Specified type .* doesn't exist")) {
                logger.error("The specified data type does not exist. " +
                        "The type must be byte, short, int, long, float, or double.", e);
            } else if (e.getMessage().matches("Property with name .* doesn't exist")) {
                logger.error("Unable to read the values for “min”, “max”, or “step” due to the absence of a variable " +
                        "with that name in the provided file.", e);
            }
            //TODO: only input handler??
        } catch (CannotProceedException e) {
            //TODO
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
