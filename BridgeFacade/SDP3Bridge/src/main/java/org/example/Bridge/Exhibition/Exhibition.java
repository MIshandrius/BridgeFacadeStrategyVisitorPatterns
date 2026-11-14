
package org.example.Bridge.Exhibition;

import org.example.Bridge.Art.IArt;
import org.example.Bridge.ObjectType.IObjectFormat;
import org.example.Bridge.Price.IPrice;
import org.example.Visitor.IArtVisitor;
import org.example.Observer.ArtworkAddedEvent;
import org.example.Observer.ArtworkObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Exhibition {

    private final String title;
    private final List<IArt> artworks;
    private final List<ArtworkObserver> observers =
            new ArrayList<>();

    protected IPrice price;
    protected double priceUsd;
    protected IObjectFormat form;

    protected Exhibition(
            String title,
            List<IArt> artworks,
            IObjectFormat form
    ) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Exhibition title is required"
            );
        }

        Objects.requireNonNull(
                artworks, "Artworks must not be null"
        );

        if (artworks.isEmpty() ||
                artworks.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "Exhibition must have non-null artwork(s)"
            );
        }

        this.title = title.trim();
        this.artworks = new ArrayList<>(artworks);
        this.form = Objects.requireNonNull(
                form, "Format must not be null"
        );
    }

    protected Exhibition(
            IArt artwork,
            IPrice price,
            double priceUsd,
            IObjectFormat form
    ) {
        this("Untitled exhibition", List.of(artwork), form);
        configurePrice(price, priceUsd);
    }

    protected Exhibition(
            IArt artwork,
            IObjectFormat form
    ) {
        this("Untitled exhibition", List.of(artwork), form);
    }

    protected final void configurePrice(
            IPrice price,
            double priceUsd
    ) {
        if (!Double.isFinite(priceUsd) || priceUsd < 0) {
            throw new IllegalArgumentException(
                    "Price must be finite and non-negative"
            );
        }

        this.price = Objects.requireNonNull(
                price, "Currency formatter must not be null"
        );
        this.priceUsd = priceUsd;
    }

    public String getTitle() {
        return title;
    }

    public List<IArt> getArtworks() {
        return List.copyOf(artworks);
    }

    public int getArtworkCount() {
        return artworks.size();
    }

    public void registerObserver(ArtworkObserver observer) {
        Objects.requireNonNull(
                observer,
                "Observer must not be null"
        );

        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ArtworkObserver observer) {
        observers.remove(observer);
    }

    public void addArtwork(IArt artwork) {

        artworks.add(
                Objects.requireNonNull(
                        artwork,
                        "Artwork must not be null"
                )
        );

        ArtworkAddedEvent event =
                new ArtworkAddedEvent(
                        title,
                        artwork,
                        artworks.size()
                );

        for (ArtworkObserver observer :
                List.copyOf(observers)) {

            observer.onArtworkAdded(event);
        }
    }


    public void setFormat(IObjectFormat form) {
        this.form = Objects.requireNonNull(
                form, "Format must not be null"
        );
    }

    protected void showDetails() {
        for (IArt artwork : artworks) {
            artwork.detailArt();
        }

        form.artFormat();
    }

    public void applyVisitor(IArtVisitor visitor) {
        Objects.requireNonNull(
                visitor, "Visitor must not be null"
        );

        for (IArt artwork : artworks) {
            artwork.accept(visitor);
        }
    }

    public abstract void show();
}
