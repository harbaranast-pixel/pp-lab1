package org.example.pricing;

import java.math.BigDecimal;

public interface PriceCalculator {

    BigDecimal calculateCommercialPrice(BigDecimal basePrice);
}
