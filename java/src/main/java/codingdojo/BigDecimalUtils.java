package codingdojo;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static boolean isALessThanOrEqualToB(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean isALessThanB(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }
}
