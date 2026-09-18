package nokhrina.daria;

import nokhrina.daria.constants.TypeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/** Creates a table and outputs it */
public class MultiplierOutputHandler {
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(MultiplierOutputHandler.class);

    /**
     * Creates string representation for each multiplication.
     *
     * @param container the container with min, max, step, etc.
     */
    void createMultiplyTable(NumberContainer container) {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        boolean resultLargerThanType = false;

        while (container.getMin().compareTo(container.getMax()) < 0) {
            ArrayList<String> outputLine = new ArrayList<>();

            while (container.getNextMultiplier().compareTo(container.getMax()) < 0) {
                StringBuilder sb = new StringBuilder();

                sb.append(container.getMin()).append(" * ").append(container.getNextMultiplier())
                        .append(" = ").append(container.multiply());

                if (TypeConstants.maxValues.get(container.getType()).compareTo(container.getLastResult()) < 0) {
                    sb.append("(!)");
                    resultLargerThanType = true;
                }

                if (!sb.isEmpty()) outputLine.add(String.valueOf(sb));
            }

            if (!outputLine.isEmpty()) output.add(outputLine);
            container.multiplySwitchToNextRow();
        }

        if (resultLargerThanType) logger.warn("The result displayed below exceeded the limit for this type.");
        printOutput(output);
    }

    /**
     * Outputs result into the logger by converting the list of results into a table
     *
     * @param output A list containing sublists with the results of each iteration
     *               So if min = "10", max = "25" and step = "4", then:
     *               [["10 * 10 = 100", "10 * 14 = 140", "10 * 18 = 180", "10 * 22 = 220"],
     *               ["14 * 14 = 196", "14 * 18 = 252", "14 * 22 = 308"],
     *               ["18 * 18 = 324", "18 * 22 = 396"],
     *               ["22 * 22 = 484"]]
     */
    void printOutput(ArrayList<ArrayList<String>> output) {
        int maxSize = 0;
        for (var i = 0; i < output.size(); i++) {
            for (var j = 0; j < output.get(i).size(); j++) {
                if (maxSize < output.get(i).get(j).length()) maxSize = output.get(i).get(j).length();
            }
        }

        String formatTemplate = STR."%-\{maxSize}s";
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < output.size(); i++) {
            sb.append("\n");
            for (var j = 0; j < output.get(i).size(); j++) {
                sb.append(String.format(formatTemplate, output.get(i).get(j)));
                sb.append(" ‖ ");
            }
        }
        logger.info(sb.toString());

    }
}
