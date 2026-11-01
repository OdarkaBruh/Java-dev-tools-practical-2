package nokhrina.daria;

import nokhrina.daria.IO.PropertyReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReaderTest {
    private static PropertyReader propertyReader = new PropertyReader();

    @BeforeAll
    static void init(){
        propertyReader.setPath("src/main/resources/JUnitTest/InputHandler/Valid/");
    }

    @Test
    @DisplayName("Hello world")
    void getFromFileGeneralTest() {
        String notExistingFile = "IDontExist";
        Assertions.assertThrows(FileNotFoundException.class, () -> propertyReader.getFromFile(notExistingFile));

        String existingFile = "Correct";
        try {
            Properties properties = propertyReader.getFromFile(existingFile);
            Assertions.assertEquals(1, Integer.parseInt(properties.getProperty("min")));
            Assertions.assertEquals(2, Integer.parseInt(properties.getProperty("step")));
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Hello world")
    void getSystemGeneralTest() {
        String notExistingProp = "IDontExist";
        Assertions.assertNull(propertyReader.getSystem(notExistingProp));

        String existingProp = "testProperty";
        String existingPropValue = "helloWorld";

        System.setProperty(existingProp, existingPropValue);
        Assertions.assertEquals(existingPropValue, propertyReader.getSystem(existingProp));
    }
}
