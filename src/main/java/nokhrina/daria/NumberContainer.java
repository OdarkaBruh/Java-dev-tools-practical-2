package nokhrina.daria;

public class NumberContainer {
    private Number min;
    private Number max;
    private Number step;

    NumberContainer(){}

    NumberContainer(Number min, Number max, Number step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

//    void verifyNumbers() {
//        if (min == null || max == null || step == null ) throw new IllegalStateException("Values was not set up yep. Cannot verify them");
//
//        if (max < min ) throw new NumberFormatException("Min is larger than Max");
//        else if (min + step > max) throw new NumberFormatException("Step is larger than Max");
//    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getStep() {
        return step;
    }

    public void setStep(Number step) {
        this.step = step;
    }
}
