package com.wagdynavas.sushi2go.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtil {

    public static BigDecimal calculatePercentage(BigDecimal percentage, BigDecimal value) {
        return value.multiply(percentage).divide(new BigDecimal(100), 2, RoundingMode.CEILING);
    }
}
