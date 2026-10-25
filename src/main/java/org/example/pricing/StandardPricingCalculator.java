package org.example.pricing;

import org.example.Constants;

import java.math.BigDecimal;

public class StandardPricingCalculator implements PriceCalculator {

    @Override
    public BigDecimal calculateCommercialPrice(BigDecimal basePrice) {
        if(basePrice.compareTo(Constants.MIN_COST)<0){
            return Constants.MIN_COST;
        }

        if(basePrice.compareTo(Constants.DISCOUNT_THRESHOLD)>0){
            BigDecimal discount = basePrice.multiply(Constants.DISCOUNT_RATE);
            return basePrice.subtract(discount);
        }
        return basePrice;
    }
}
