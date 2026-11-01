package nokhrina.daria.NumberContainer;

import nokhrina.daria.constants.TypeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.CannotProceedException;
import java.math.BigDecimal;

public class Validator {
    private final NumberContainer container;
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(Validator.class);

    public Validator(NumberContainer container)
    {
        this.container = container;
    }

    /** Checks whether input values are valid */
    void check() throws CannotProceedException {
        checkValuesAreNotNull();
        checkWhetherValuesAreInLimitsOfType();
        checkWhetherMinMaxStepAreLogical();
    }

    // TODO: CHECK
    private void checkWhetherMinMaxStepAreLogical() {
        if (container.getStep().compareTo(BigDecimal.ZERO) == 0) {
            logger.error("what the heck");
            throw new IllegalArgumentException("step is 0.");
        } else if ((container.getMin().compareTo(container.getMax()) < 0 && container.getStep().compareTo(BigDecimal.ZERO) < 0) ||
                (container.getMin().compareTo(container.getMax()) > 0 && container.getStep().compareTo(BigDecimal.ZERO) > 0)) {
            logger.error("Infinite loop");
            throw new IllegalArgumentException("loop");
        }

    }

    public void checkWhetherValuesAreInLimitsOfType() {
        BigDecimal comparator = TypeConstants.maxValues.get(container.getType());
        if (comparator == null) throw new IllegalArgumentException("checkType() - Type doesn't exist");

        // TODO : what about step?
        if (container.getMax().compareTo(comparator) > 0) throw new NumberExceedsTypeLimitSize(container.getType(), "max");
        else if (container.getMin().abs().compareTo(comparator) > 0) throw new NumberExceedsTypeLimitSize(container.getType(), "min");
    }

    private void checkValuesAreNotNull() throws CannotProceedException {
        if (container.getMin() == null || container.getMax() == null || container.getStep() == null)
            throw new CannotProceedException("Values have not been read yet.");
        else if (container.getType() == null) throw new CannotProceedException("Type has not been read yet.");
    }

    private boolean checkIfNumberIsDecimal(BigDecimal number, String variableSpecifier) {
        if (number.doubleValue() != number.unscaledValue().doubleValue()) {
            logger.warn("Received number ({}) is decimal, but type is not.", variableSpecifier);
            return true;
        }
        return false;
    }

    public void checkWhetherValuesAndTypeAreDecimal() {
        if (container.getType() == Float.class || container.getType() == Double.class || container.getType() == Long.class) return;

        if (checkIfNumberIsDecimal(container.getMin(), "min")) container.setMin(new BigDecimal(container.getMin().unscaledValue()));
        if (checkIfNumberIsDecimal(container.getMax(), "max")) container.setMax(new BigDecimal(container.getMax().unscaledValue()));
        if (checkIfNumberIsDecimal(container.getStep(), "step")) container.setStep(new BigDecimal(container.getStep().unscaledValue()));

    }
}
