package nokhrina.daria;

import nokhrina.daria.constants.NameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;


/** Transforms information inside Property files and System properties into usable data:
 * reads type, creates container from a file, etc. */
public class InputHandler {
    /** Main class logger */
    private final Logger logger = LoggerFactory.getLogger(InputHandler.class);
    /** Main property reader to read system parameters / from file */
    private final PropertyReader propertyReader = new PropertyReader();

    /** default value for a container, which will be used if System.Properties doesn't contain one */
    private static final String DEFAULT_TYPE = "int";

    InputHandler() {};
    InputHandler(String path) {
        setPath(path);
    }
    /**
     * Reads system property with the specified name and returns the corresponding class. If system property doesn't
     * exist - returns default value (DEFAULT_TYPE).
     *
     * @param variableName System Property name
     * @return Сlass that defines a data type (int / short / float / etc)
     */
    Class readType(String variableName) {
        String typeString = propertyReader.getSystem(variableName);
        if (typeString == null) {
            typeString = DEFAULT_TYPE;
            logger.warn("Property containing type has not been read. Default type is assigned.");
        }
        Class type = getType(typeString);
        logger.info(String.format("Type is %s", type.getName()));
        return type;
    }

    /**
     * Turns string with a type to corresponding class and returns it.
     *
     * @param type the name of the type (without .class)
     * @return the corresponding class for the type
     */
    Class getType(String type) {
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
            default -> throw new IllegalArgumentException(String.format("Specified type \"%s\" doesn't exist", type));
        }
    }

    /**
     * Creates Number Container instance based on a file with specified name.
     *
     * @param propertyFileName name of the file to read
     * @return created instance
     * @throws IOException if file doesn't exist.
     */
    NumberContainer createNumberContainer(String propertyFileName) throws IOException {
        Properties allProperties = propertyReader.getFromFile(propertyFileName);

        NumberContainer container = new NumberContainer(
                readValueFromProperties(NameConstants.DEFAULT_NAME_OF_MIN, allProperties),
                readValueFromProperties(NameConstants.DEFAULT_NAME_OF_MAX, allProperties),
                readValueFromProperties(NameConstants.DEFAULT_NAME_OF_STEP, allProperties));

        return container;
    }

    /**
     * Sets the default path for the property reader.
     *
     * @param path new default path
     */
    void setPath(String path) {
        propertyReader.setPath(path);
    }

    /**
     * Reads the property with the specified name from the Properties object
     *
     * @param name       the property name
     * @param properties all properties
     * @return the value of this property as a BigDecimal
     * @throws IllegalArgumentException if property is null
     */
    private BigDecimal readValueFromProperties(String name, Properties properties) {
        String number = properties.getProperty(name);
        if (number == null) throw new IllegalArgumentException("Property with name \"" + name + "\" doesn't exist.");
        return new BigDecimal(number);
    }
}
