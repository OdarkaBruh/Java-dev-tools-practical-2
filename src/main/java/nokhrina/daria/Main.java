package nokhrina.daria;


import nokhrina.daria.constants.NameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This program:
 * 1. Reads type of numbers from a System Property {@value NameConstants#DEFAULT_NAME_OF_SYSTEM_VARIABLE}.
 * 2. Reads min, max and step from a property file  {@value NameConstants#PROPERTY_FILE_NAME} with a
 * path {@value NameConstants#PROPERTY_FILE_PATH}
 * 3. Checks them.
 * 4. Outputs multiplication table of the range.
 */
public class Main {
    /** Main logger */
    static final Logger logger = LoggerFactory.getLogger(Main.class);

    /** Runs the task described above. */
    public static void main(String[] args) {
        logger.debug("Program is started.");

        try {
            PracticeTask task = new PracticeTask();
            task.runTask(NameConstants.PROPERTY_FILE_NAME, NameConstants.DEFAULT_NAME_OF_SYSTEM_VARIABLE);
            logger.debug("Program is finished successfully.");
        } catch (Exception e) {
            logger.error("Program wasn't finished.", e);
        }

    }
}