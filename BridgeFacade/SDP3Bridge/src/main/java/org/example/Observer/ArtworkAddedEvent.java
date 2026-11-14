
package org.example.Observer;

import org.example.Bridge.Art.IArt;
import java.util.Objects;

public record ArtworkAddedEvent(
        String exhibitionTitle,
        IArt artwork,
        int artworkCount
) {
    public ArtworkAddedEvent {
        Objects.requireNonNull(
                exhibitionTitle,
                "Exhibition title must not be null"
        );

        Objects.requireNonNull(
                artwork,
                "Artwork must not be null"
        );

        if (artworkCount < 1) {
            throw new IllegalArgumentException(
                    "Artwork count must be positive"
            );
        }
    }
}
