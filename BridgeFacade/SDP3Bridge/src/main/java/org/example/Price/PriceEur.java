package org.example.Price;

public class PriceEur implements IPrice{
    private static final double EUR_PER_USD = 0.92;

    @Override
    public String format(double priceUsd) {
        return IPrice.formatAmount(
                priceUsd * EUR_PER_USD,
                "EUR"
        );
    }
}
