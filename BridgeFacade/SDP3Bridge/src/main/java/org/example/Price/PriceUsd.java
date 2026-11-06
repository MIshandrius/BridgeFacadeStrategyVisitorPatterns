package org.example.Price;

public class PriceUsd implements IPrice{
    @Override
    public String format(double priceUsd) {
        return IPrice.formatAmount(priceUsd, "USD");
    }
}
