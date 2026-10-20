package pricing;

import java.math.BigDecimal;

public interface PriceCalculator {

    BigDecimal calculatecommercialPrice(BigDecimal basePrice);
}
