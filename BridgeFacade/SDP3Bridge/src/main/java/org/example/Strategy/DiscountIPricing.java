package org.example.Strategy;

public class DiscountIPricing implements IPricingStrategy {
    private final double discountPercent;

    public DiscountIPricing(double discountPercent){
        if(!Double.isFinite(discountPercent) || discountPercent < 0 || discountPercent > 100){
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }

        this.discountPercent = discountPercent;
    }

    @Override
    public double calculatedPrice(double basePrice){
        return basePrice * (1 - discountPercent / 100.00);
    }
}
