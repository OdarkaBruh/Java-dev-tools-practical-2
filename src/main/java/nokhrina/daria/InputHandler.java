package nokhrina.daria;

import java.io.IOException;
import java.util.Properties;

import static nokhrina.daria.NamesConstants.PROPERTY_FILE_NAME;

public class InputHandler {
    private final PropertyReader propertyReader = new PropertyReader();

    private final String DEFAULT_TYPE = "int";

    Class<? extends Number> readType(String variableName) {
        String type = propertyReader.getSystem(variableName);
        if (type == null) type = DEFAULT_TYPE;
        return getType(type);
    }

    Class<? extends Number> getType(String type) {
        switch (type.toLowerCase()) {
            case "byte" -> {
                return Byte.class;
            }
            case "short" -> {
                return Short.class;
            }
            case "int", "integer" -> {
                return Integer.class;
            }
            case "long" -> {
                return Long.class;
            }
            case "float" -> {
                return Float.class;
            }
            case "double" -> {
                return Double.class;
            }
            default -> throw new IllegalArgumentException("Specified type doesn't exist");
        }
    }

    public NumberContainer getNumberContainer(Class<? extends Number> classType) throws IOException {
        Properties allProperties = propertyReader.getFromFile(PROPERTY_FILE_NAME);

        NumberContainer container = new NumberContainer(
                getNumberFromProperty(NamesConstants.DEFAULT_NAME_OF_MIN, allProperties),
                getNumberFromProperty(NamesConstants.DEFAULT_NAME_OF_MAX, allProperties),
                getNumberFromProperty(NamesConstants.DEFAULT_NAME_OF_STEP, allProperties));

        return limitValuesToType(classType, container);
    }

    private Number getNumberFromProperty(String name, Properties properties) {
        String num = properties.getProperty(name);

        if (num == null) throw new IllegalArgumentException("Property with name \"" + name + "\" doesn't exist.");
        return Double.valueOf(num);
    }

    NumberContainer limitValuesToType(Class<? extends Number> classType, NumberContainer container) {
        if (classType == Byte.class) {
            if (Double.valueOf(container.getMin().byteValue()) != container.getMin().doubleValue() ||
                    Double.valueOf(container.getMax().byteValue()) != container.getMax().doubleValue() ||
                    Double.valueOf(container.getStep().byteValue()) != container.getStep().doubleValue())
                throw new NumberFormatException("Number is larger than byte");
        } else if (classType == Short.class) {
            if (Double.valueOf(container.getMin().shortValue()) != container.getMin().doubleValue() ||
                    Double.valueOf(container.getMax().shortValue()) != container.getMax().doubleValue() ||
                    Double.valueOf(container.getStep().shortValue()) != container.getStep().doubleValue())
                throw new NumberFormatException("Number is larger than short");
            //TODO: BELOW NEW COMPARISON METHOD??
        } else if (classType == Integer.class) {
            container.setMin(container.getMin().intValue());
            container.setMax(container.getMax().intValue());
            container.setStep(container.getMax().intValue());
        } else if (classType == Double.class) {
            container.setMin(container.getMin().doubleValue());
            container.setMax(container.getMax().doubleValue());
            container.setStep(container.getMax().doubleValue());
        } else if (classType == Long.class) {
            container.setMin(container.getMin().longValue());
            container.setMax(container.getMax().longValue());
            container.setStep(container.getMax().longValue());
        } else if (classType == Float.class) {
            container.setMin(container.getMin().floatValue());
            container.setMax(container.getMax().floatValue());
            container.setStep(container.getMax().floatValue());
        }
        else throw new RuntimeException();
        return container;
    }

//     NumberContainer limitValuesToType(Class<? extends Number> classType, NumberContainer container) {
//        if (classType == Byte.class) {
//            container.setMin(container.getMin().byteValue());
//            container.setMax(container.getMax().byteValue());
//            container.setStep(container.getMax().byteValue());
//        } else if (classType == Short.class) {
//            container.setMin(container.getMin().shortValue());
//            container.setMax(container.getMax().shortValue());
//            container.setStep(container.getMax().shortValue());
//        } else if (classType == Integer.class) {
//            container.setMin(container.getMin().intValue());
//            container.setMax(container.getMax().intValue());
//            container.setStep(container.getMax().intValue());
//        } else if (classType == Double.class) {
//            container.setMin(container.getMin().doubleValue());
//            container.setMax(container.getMax().doubleValue());
//            container.setStep(container.getMax().doubleValue());
//        } else if (classType == Long.class) {
//            container.setMin(container.getMin().longValue());
//            container.setMax(container.getMax().longValue());
//            container.setStep(container.getMax().longValue());
//        } else if (classType == Float.class) {
//            container.setMin(container.getMin().floatValue());
//            container.setMax(container.getMax().floatValue());
//            container.setStep(container.getMax().floatValue());
//        }
//        else throw new RuntimeException();
//        return container;
//    }
}
