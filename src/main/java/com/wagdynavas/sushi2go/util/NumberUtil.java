package com.wagdynavas.sushi2go.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {



    public static BigDecimal calculatePercentage(BigDecimal percentage, BigDecimal value) {
        return value.multiply(percentage).divide(new BigDecimal(100), 2, RoundingMode.CEILING);
    }
}
