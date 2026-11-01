package nokhrina.daria.MultiplicationLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/** Creates a table and outputs it */
public class MultiplierOutputHandler {
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(MultiplicationHandler.class);
    private ArrayList<ArrayList<String>> output = new ArrayList<>();

    MultiplierOutputHandler() {
        output.add(new ArrayList<>());
    }

    void addOperation(String n1, String n2, String result, boolean isOverflowed) {
        StringBuilder sb = new StringBuilder();
        sb.append(n1).append(" * ").append(n2).append(" = ").append(result);
        if (isOverflowed) sb.append("(!)");
        output.getLast().add(sb.toString());
    }

    void switchToTheNextRow() {
        if (!output.getLast().isEmpty()) output.add(new ArrayList<>());
    }

    void printOutput() {
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
