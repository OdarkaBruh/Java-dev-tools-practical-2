package nokhrina.daria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestInput {

    @Test
    @DisplayName("Check of all types")
    public void checkAllNumberTypes(){
        InputHandler inputHandler = new InputHandler();
        Assertions.assertInstanceOf(Byte.class, inputHandler.getType("byte"));
        Assertions.assertInstanceOf(Short.class, inputHandler.getType("short"));
        Assertions.assertInstanceOf(Integer.class, inputHandler.getType("int"));
        Assertions.assertInstanceOf(Integer.class, inputHandler.getType("integer"));
        Assertions.assertInstanceOf(Long.class, inputHandler.getType("long"));
        Assertions.assertInstanceOf(Float.class, inputHandler.getType("float"));
        Assertions.assertInstanceOf(Double.class, inputHandler.getType("double"));
    }

    @Test
    @DisplayName("Check default type")
    public void checkDefault(){
        InputHandler inputHandler = new InputHandler();
        Assertions.assertInstanceOf(Integer.class, inputHandler.readType(" "));
    }

    @Test
    @DisplayName("??")
    public void Sandbox(){
        NumberContainer container = new NumberContainer(200, 300, 5);
        InputHandler handler = new InputHandler();
        handler.limitValuesToType(Integer.class, container);
        System.out.println(container.getMin());
        Assertions.assertEquals(Double.valueOf(container.getMin().intValue()), container.getMin().doubleValue());
    }


}
