package nokhrina.daria.constants;

import java.math.BigDecimal;
import java.util.Map;

/** Information about types */
public class TypeConstants {
    /** Map of types and their upper limits */
    public static Map<Class, BigDecimal> maxValues = Map.of(
            Byte.class, new BigDecimal(Byte.MAX_VALUE),
            Short.class, new BigDecimal(Short.MAX_VALUE),
            Integer.class, new BigDecimal(Integer.MAX_VALUE),
            Float.class, new BigDecimal(Float.MAX_VALUE),
            Long.class, new BigDecimal(Long.MAX_VALUE),
            Double.class, new BigDecimal(Double.MAX_VALUE)
    );
}
