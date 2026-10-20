package org.example;

import java.math.BigDecimal;

public final class Constants {
    public static final int MIN_MANUFACTURING_YEAR = 1886;

    public static final BigDecimal MIN_COST = new BigDecimal("500.00");
    public static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("10000.00");
    public static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    private Constants() {}
}
