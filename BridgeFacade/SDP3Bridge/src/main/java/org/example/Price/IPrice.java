package org.example.Price;

import java.util.Locale;

public interface IPrice {
    String format(double amountUsd);

    static String formatAmount(double amount, String currencyCode) {
        return String.format(
                Locale.ROOT,
                "%.2f %s",
                amount,
                currencyCode
        );
    }
}
