package nokhrina.daria.NumberContainer;

public class NumberExceedsTypeLimitSize extends RuntimeException{
    NumberExceedsTypeLimitSize(Class type) {
        super(String.format("Specified number exceeds the size limit of desired type (%s)", type));
    }

    NumberExceedsTypeLimitSize(Class type, String variable) {
        super(String.format("Variable {%s} exceeds the size limit of desired type (%s)", variable, type));
    }
}
