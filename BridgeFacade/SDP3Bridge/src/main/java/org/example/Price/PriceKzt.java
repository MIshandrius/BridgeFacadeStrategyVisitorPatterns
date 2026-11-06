package org.example.Price;

public class PriceKzt implements IPrice{
    private static final double KZT_PER_USD = 460.0;

    @Override
    public String format(double priceUsd) {
        return IPrice.formatAmount(
                priceUsd * KZT_PER_USD,
                "KZT"
        );
    }
}
