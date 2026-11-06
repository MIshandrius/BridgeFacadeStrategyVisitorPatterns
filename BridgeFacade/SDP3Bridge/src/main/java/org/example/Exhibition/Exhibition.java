package org.example.Exhibition;

import org.example.Art.IArt;
import org.example.Price.IPrice;
import org.example.ObjectType.IObjectFormat;
import org.example.Visitor.IArtVisitor;

import java.util.Objects;

public abstract class Exhibition {
    protected IArt artwork;
    protected IPrice price;
    protected double priceUsd;
    protected IObjectFormat form;


    protected Exhibition(
            IArt artwork,
            IPrice price,
            double priceUsd,
            IObjectFormat form
    ) {
        if (!Double.isFinite(priceUsd) || priceUsd < 0) {
            throw new IllegalArgumentException(
                    "Price must be a finite, non-negative number"
            );
        }

        this.artwork = Objects.requireNonNull(
                artwork, "Artwork must not be null"
        );
        this.price = Objects.requireNonNull(
                price, "Currency formatter must not be null"
        );
        this.priceUsd = priceUsd;
        this.form = Objects.requireNonNull(
                form, "Format must not be null"
        );
    }

    protected Exhibition(
            IArt artwork,
            IObjectFormat form
    ){
        this.artwork = Objects.requireNonNull(
                artwork, "Artwork must not be null"
        );
        this.form = Objects.requireNonNull(
                form, "Format must not be null");

    }

    public void setFormat(IObjectFormat form) {
        this.form = Objects.requireNonNull(
                form, "Format must not be null"
        );
    }

    protected void showDetails() {
        artwork.detailArt();
        form.artFormat();
    }

    public void applyVisitor(IArtVisitor visitor){
        Objects.requireNonNull(visitor, "Visitor must not be null");
        artwork.accept(visitor);
    }

    public abstract void show();
}
