package org.example.pricing;

import org.example.Constants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class StandardPricingCalculatorTest {

    private final StandardPricingCalculator calculator = new StandardPricingCalculator();

    private static final BigDecimal PRICE_BELOW_MIN = new BigDecimal("300.00");
    private static final BigDecimal NORMAL_PRICE = new BigDecimal("5000.00");
    private static final BigDecimal PRICE_ABOVE_THRESHOLD = new BigDecimal("11000.00");
    private static final BigDecimal EXPECTED_DISCOUNTED_PRICE = new BigDecimal("9900.00");
    private static final BigDecimal ZERO_PRICE = BigDecimal.ZERO;

    @ParameterizedTest
    @MethodSource("providePrices")
    void calculateCommercialPrice_ShouldApplyCorrectRules(BigDecimal basePrice, BigDecimal expectedPrice) {
        BigDecimal actualPrice = calculator.calculateCommercialPrice(basePrice);

        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }

    private static Stream<Arguments> providePrices() {
        return Stream.of(

                Arguments.of(ZERO_PRICE, Constants.MIN_COST),
                Arguments.of(PRICE_BELOW_MIN, Constants.MIN_COST),
                Arguments.of(Constants.MIN_COST, Constants.MIN_COST),
                Arguments.of(NORMAL_PRICE, NORMAL_PRICE),
                Arguments.of(Constants.DISCOUNT_THRESHOLD, Constants.DISCOUNT_THRESHOLD),
                Arguments.of(PRICE_ABOVE_THRESHOLD, EXPECTED_DISCOUNTED_PRICE)
        );

    }
}
