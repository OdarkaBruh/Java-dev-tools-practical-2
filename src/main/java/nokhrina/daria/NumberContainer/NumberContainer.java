package nokhrina.daria.NumberContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.CannotProceedException;
import java.math.BigDecimal;

//TODO: if decimal => result with floats??

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

    /** type of the data (Int, short, byte, etc.) */
    private Class type;
    /** Main logger */
    private Logger logger = LoggerFactory.getLogger(NumberContainer.class);

    public NumberContainer() {}

    public NumberContainer(BigDecimal min, BigDecimal max, BigDecimal step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public NumberContainer(BigDecimal min, BigDecimal max, BigDecimal step, Class type) {
        this.min = min;
        this.max = max;
        this.step = step;

        this.type = type;
    }

    public NumberContainer(String min, String max, String step, Class type) {
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
        this.step = new BigDecimal(step);
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    /** Checks whether input values are valid */
    public void check() throws CannotProceedException {
        new Validator(this).check();
    }

    public BigDecimal getMin() {
        return min;
    }
    public BigDecimal getMax() {
        return max;
    }
    public BigDecimal getStep() {
        return step;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public void setStep(BigDecimal step) {
        this.step = step;
    }
}
