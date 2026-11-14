
package org.example.Bridge.Exhibition;

import org.example.Bridge.Art.IArt;
import org.example.Bridge.ObjectType.IObjectFormat;
import org.example.Bridge.Price.IPrice;
import org.example.Strategy.IPricingStrategy;
import org.example.Strategy.RegularIPricing;

import java.util.List;
import java.util.Objects;

public class Gallery extends Exhibition {

    private IPricingStrategy pricingStrategy =
            new RegularIPricing();

    // Original constructor
    public Gallery(
            IArt artwork,
            IObjectFormat form,
            double priceUsd,
            IPrice price
    ) {
        super(artwork, price, priceUsd, form);
    }

    // New constructor for Builder
    public Gallery(
            String title,
            List<IArt> artworks,
            IObjectFormat form,
            double priceUsd,
            IPrice price,
            IPricingStrategy pricingStrategy
    ) {
        super(title, artworks, form);
        configurePrice(price, priceUsd);
        setPricingStrategy(pricingStrategy);
    }

    public void setPricingStrategy(
            IPricingStrategy pricingStrategy
    ) {
        this.pricingStrategy = Objects.requireNonNull(
                pricingStrategy, "Pricing must not be null"
        );
    }

    @Override
    public void show() {
        System.out.println(
                "Gallery exhibition: " + getTitle()
        );

        System.out.println(
                "This artwork is available for sale"
        );

        showDetails();

        double finalPriceUsd =
                pricingStrategy.calculatedPrice(priceUsd);

        System.out.println(
                "Price: " + price.format(finalPriceUsd)
        );
    }
}
