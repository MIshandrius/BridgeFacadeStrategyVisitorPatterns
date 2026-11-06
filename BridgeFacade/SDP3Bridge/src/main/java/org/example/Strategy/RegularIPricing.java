package org.example.Strategy;

public class RegularIPricing implements IPricingStrategy {
    @Override
    public double calculatedPrice(double basePrice){
        return basePrice;
    }
}
