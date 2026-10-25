package org.example.pricing;

import org.example.Constants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardPricingCalculatorTest {

    private final StandardPricingCalculator calculator = new StandardPricingCalculator();

    @ParameterizedTest
    @MethodSource("providePrices")
    void calculateCommercialPrice_ShouldApplyCorrectRules(BigDecimal basePrice, BigDecimal expectedPrice) {

        BigDecimal actualPrice = calculator.calculateCommercialPrice(basePrice);

        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }
}
