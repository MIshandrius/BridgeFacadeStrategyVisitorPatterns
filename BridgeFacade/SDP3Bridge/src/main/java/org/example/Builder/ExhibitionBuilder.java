
package org.example.Builder;

import org.example.Bridge.Art.IArt;
import org.example.Bridge.Exhibition.Gallery;
import org.example.Bridge.ObjectType.IObjectFormat;
import org.example.Bridge.ObjectType.Physical;
import org.example.Bridge.Price.IPrice;
import org.example.Bridge.Price.PriceUsd;
import org.example.Strategy.IPricingStrategy;
import org.example.Strategy.RegularIPricing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ExhibitionBuilder {

    // Required fields
    private String title;
    private final List<IArt> artworks = new ArrayList<>();

    // Optional settings and defaults
    private IObjectFormat format = new Physical("Gallery room");
    private IPrice currency = new PriceUsd();
    private IPricingStrategy pricingStrategy = new RegularIPricing();
    private double basePriceUsd = 0.0;

    public ExhibitionBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ExhibitionBuilder addArtwork(IArt artwork) {
        artworks.add(Objects.requireNonNull(
                artwork, "Artwork must not be null"
        ));
        return this;
    }

    public ExhibitionBuilder format(IObjectFormat format) {
        this.format = Objects.requireNonNull(
                format, "Format must not be null"
        );
        return this;
    }

    public ExhibitionBuilder currency(IPrice currency) {
        this.currency = Objects.requireNonNull(
                currency, "Currency must not be null"
        );
        return this;
    }

    public ExhibitionBuilder pricingStrategy(
            IPricingStrategy pricingStrategy
    ) {
        this.pricingStrategy = Objects.requireNonNull(
                pricingStrategy, "Pricing strategy must not be null"
        );
        return this;
    }

    public ExhibitionBuilder basePriceUsd(double basePriceUsd) {
        if (!Double.isFinite(basePriceUsd) || basePriceUsd < 0) {
            throw new IllegalArgumentException(
                    "Base price must be finite and non-negative"
            );
        }

        this.basePriceUsd = basePriceUsd;
        return this;
    }

    public Gallery build() {
        if (title == null || title.isBlank()) {
            throw new IllegalStateException(
                    "Exhibition title is required"
            );
        }

        if (artworks.isEmpty()) {
            throw new IllegalStateException(
                    "At least one artwork is required"
            );
        }

        return new Gallery(
                title,
                List.copyOf(artworks),
                format,
                basePriceUsd,
                currency,
                pricingStrategy
        );
    }
}
