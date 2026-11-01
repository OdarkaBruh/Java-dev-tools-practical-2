package nokhrina.daria.MultiplicationLogic;

import nokhrina.daria.NumberContainer.NumberContainer;
import nokhrina.daria.constants.TypeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class MultiplicationHandler {
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(MultiplicationHandler.class);

    NumberContainer numberContainer;
    MultiplierOutputHandler outputHandler = new MultiplierOutputHandler();

    /** Result of the last multiplication */
    private BigDecimal lastResult;
    private boolean lastResultIsOverflowed;

    private BigDecimal number1;
    private BigDecimal number2;


    public MultiplicationHandler(NumberContainer numberContainer) {
        this.numberContainer = numberContainer;

        this.number1 = numberContainer.getMin();
        this.number2 = numberContainer.getMin();
    }

    public void createTable() {
        boolean resultLargerThanType = false;

        while (number1.compareTo(numberContainer.getMax()) < 0) {
            while (number2.compareTo(numberContainer.getMax()) < 0) {
                outputHandler.addOperation(number1.toString(), number2.toString(), multiplyOnce().toString(),
                        lastResultIsOverflowed);
                resultLargerThanType = resultLargerThanType || lastResultIsOverflowed;
            }
            switchToNextRow();
        }

        if (resultLargerThanType) logger.warn("The result displayed below exceeded the limit for this type.");
        outputHandler.printOutput();
    }

    public BigDecimal multiplyOnce() {
        if (number2.compareTo(numberContainer.getMax()) > 0) {
            logger.warn("The multiplier exceeded the maximum value.");
        }

        lastResult = number1.multiply(number2);
        number2 = number2.add(numberContainer.getStep());

        if (TypeConstants.maxValues.get(numberContainer.getType()).compareTo(lastResult) < 0) {
            logger.warn("The result ({}) exceeded the maximum value for this type ({})",
                    lastResult.toString(), numberContainer.getType());
            lastResultIsOverflowed = true;
        } else lastResultIsOverflowed = false;

        return lastResult;
    }

    private void switchToNextRow() {
        outputHandler.switchToTheNextRow();
        increaseFirstNumber();
        resetSecondNumber();
    }

    private void resetSecondNumber() {
        number2 = number1;
    }

    private void increaseFirstNumber() {
        number1 = number1.add(numberContainer.getStep());
    }

    public BigDecimal getLastResult() {
        return lastResult;
    }
}
