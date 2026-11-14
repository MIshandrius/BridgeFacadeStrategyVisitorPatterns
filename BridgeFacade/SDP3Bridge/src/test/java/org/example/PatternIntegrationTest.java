
package org.example;

import org.example.Builder.ExhibitionBuilder;
import org.example.Bridge.Art.Painting;
import org.example.Bridge.Art.Sculpture;
import org.example.Bridge.Exhibition.Gallery;
import org.example.Bridge.ObjectType.Virtual;
import org.example.Bridge.Price.PriceUsd;
import org.example.Observer.ActivityLogObserver;
import org.example.Observer.DisplayCounterObserver;
import org.example.Strategy.DiscountIPricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternIntegrationTest {

    // Test 1: Exhibition must have a title
    @Test
    void builderRequiresTitle() {
        assertThrows(
                IllegalStateException.class,
                () -> new ExhibitionBuilder()
                        .addArtwork(new Painting("A"))
                        .build()
        );
    }

    // Test 2: Exhibition must contain an artwork
    @Test
    void builderRequiresArtwork() {
        assertThrows(
                IllegalStateException.class,
                () -> new ExhibitionBuilder()
                        .title("Exhibition")
                        .build()
        );
    }

    // Test 3: Price cannot be negative or NaN
    @Test
    void builderRejectsInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExhibitionBuilder()
                        .basePriceUsd(-1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new ExhibitionBuilder()
                        .basePriceUsd(Double.NaN)
        );
    }

    // Test 4: Building again must not modify previous exhibitions
    @Test
    void builderCreatesIndependentExhibitions() {
        ExhibitionBuilder builder = new ExhibitionBuilder()
                .title("Modern Art")
                .addArtwork(new Painting("Sunflowers"))
                .format(new Virtual("3D Gallery"))
                .currency(new PriceUsd())
                .pricingStrategy(new DiscountIPricing(10))
                .basePriceUsd(100);

        Gallery first = builder.build();

        builder.addArtwork(new Sculpture("David"));

        Gallery second = builder.build();

        assertEquals(1, first.getArtworkCount());
        assertEquals(2, second.getArtworkCount());
    }

    // Test 5: Observers receive events and can unsubscribe
    @Test
    void observersReceiveEventsAndCanUnsubscribe() {
        Gallery gallery = new ExhibitionBuilder()
                .title("Art Gallery")
                .addArtwork(new Painting("Starry Night"))
                .build();

        ActivityLogObserver log = new ActivityLogObserver();

        DisplayCounterObserver counter =
                new DisplayCounterObserver(
                        gallery.getArtworkCount()
                );

        gallery.registerObserver(log);
        gallery.registerObserver(counter);

        // Registering the same observer twice should have no effect
        gallery.registerObserver(counter);

        gallery.addArtwork(new Sculpture("David"));

        assertEquals(1, log.getEvents().size());
        assertEquals(2, counter.getCount());

        assertEquals(
                "Art Gallery",
                log.getEvents().get(0).exhibitionTitle()
        );

        // Remove the logger
        gallery.removeObserver(log);

        gallery.addArtwork(new Painting("Water Lilies"));

        assertEquals(1, log.getEvents().size());
        assertEquals(3, counter.getCount());
    }

    // Test 6: Reject null artworks without publishing an event
    @Test
    void rejectedArtworkDoesNotCreateEvent() {
        Gallery gallery = new ExhibitionBuilder()
                .title("Test Gallery")
                .addArtwork(new Painting("Sunflowers"))
                .build();

        ActivityLogObserver log = new ActivityLogObserver();

        gallery.registerObserver(log);

        assertThrows(
                NullPointerException.class,
                () -> gallery.addArtwork(null)
        );

        assertEquals(1, gallery.getArtworkCount());
        assertTrue(log.getEvents().isEmpty());

        // The returned list must not be modifiable
        assertThrows(
                UnsupportedOperationException.class,
                () -> gallery.getArtworks().clear()
        );
    }
}
