package nokhrina.daria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Program is started.");
        try {
            PracticeTask task = new PracticeTask();
            task.runTask();
            logger.debug("Program is finished successfully.");
        } catch (NumberFormatException e) {
            //TODO: CHECK
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}