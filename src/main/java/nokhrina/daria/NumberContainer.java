package nokhrina.daria;

import nokhrina.daria.constants.TypeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.OperationsException;
import javax.naming.CannotProceedException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;

//TODO: if decimal => result with floats??
//TODO: SMASH MULTIPLICATION INTO ANOTHER FILE.

/**
 * Container which holds min, max, step, type and will perform multiplication.
 */
public class NumberContainer {
    /** Starting value (inclusive) */
    private BigDecimal min;
    /** Finishing value (inclusive) */
    private BigDecimal max;
    /** Step between operations */
    private BigDecimal step;

    /** Result of the last multiplication */
    private BigDecimal lastResult;
    /** Second multiplier (first is last result) */
    private BigDecimal nextMultiplier;

    /** type of the data (Int, short, byte, etc.) */
    private Class type;
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(NumberContainer.class);

    NumberContainer() {}

    NumberContainer(BigDecimal min, BigDecimal max, BigDecimal step) {
        this.min = min;
        this.max = max;
        this.step = step;

        this.nextMultiplier = new BigDecimal(this.min.toString());
        this.lastResult = new BigDecimal(this.min.toString());
    }

    NumberContainer(BigDecimal min, BigDecimal max, BigDecimal step, Class type) {
        this.min = min;
        this.max = max;
        this.step = step;

        this.nextMultiplier = new BigDecimal(this.min.toString());
        this.lastResult = new BigDecimal(this.min.toString());
        this.type = type;
    }

    NumberContainer(String min, String max, String step, Class type) {
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
        this.step = new BigDecimal(step);
        this.type = type;

        this.nextMultiplier = new BigDecimal(this.min.toString());
        this.lastResult = new BigDecimal(this.min.toString());
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    /** Checks whether input values are valid*/
    void check() throws CannotProceedException {
        checkValuesAreNotNull();
        checkWhetherValuesAreInLimitsOfType();
        checkWhetherMinMaxStepAreLogical();
    }

    // TODO: CHECK
    private void checkWhetherMinMaxStepAreLogical() {
        if (step.compareTo(BigDecimal.ZERO) == 0) {
            logger.error("what the heck");
            throw new IllegalArgumentException("step is 0.");
        }
        else if ((min.compareTo(max) < 0 && step.compareTo(BigDecimal.ZERO) < 0) ||
                (min.compareTo(max) > 0 && step.compareTo(BigDecimal.ZERO) > 0)) {
            logger.error("Infinite loop");
            throw new IllegalArgumentException("loop");
        }

    }

    public void checkWhetherValuesAreInLimitsOfType() {
        BigDecimal comparator = TypeConstants.maxValues.get(type);
        if (comparator == null) throw new IllegalArgumentException("checkType() - Type doesn't exist");

        // TODO : what about step?
        if (max.compareTo(comparator) > 0) throw new NumberExceedsTypeLimitSize(type, "max");
        else if (min.abs().compareTo(comparator) > 0) throw new NumberExceedsTypeLimitSize(type, "min");
    }

    private void checkValuesAreNotNull() throws CannotProceedException {
        if (min == null || max == null || step == null)
            throw new CannotProceedException("Values have not been read yet.");
        else if (type == null) throw new CannotProceedException("Type has not been read yet.");
    }

    public BigDecimal getLastResult() {
        return lastResult;
    }

    private boolean checkIfNumberIsDecimal(BigDecimal number, String variableSpecifier) {
        if (number.doubleValue() != number.unscaledValue().doubleValue()) {
            logger.warn("Received number ({}) is decimal, but type is not.", variableSpecifier);
            return true;
        }
        return false;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getStep() {
        return step;
    }

    public void setStep(BigDecimal step) {
        this.step = step;
    }

    public BigDecimal getNextMultiplier() {
        return nextMultiplier;
    }

    public BigDecimal multiply() {
        if (nextMultiplier.compareTo(max) > 0) logger.warn("The multiplier exceeded the maximum value.");

        lastResult = min.multiply(nextMultiplier);
        nextMultiplier = nextMultiplier.add(step);

        if (TypeConstants.maxValues.get(type).compareTo(lastResult) < 0)
            logger.warn("The result ({}) exceeded the maximum value for this type ({})", lastResult.toString(), type);

        return lastResult;
    }

    public void multiplySwitchToNextRow() {
        increaseMinByStep();
        resetMultiplier();
    }

    private void resetMultiplier() {
        nextMultiplier = min;
    }

    private void increaseMinByStep() {
        min = min.add(step);
    }
}
