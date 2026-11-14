
package org.example.Observer;

public final class DisplayCounterObserver
        implements ArtworkObserver {

    private int count;

    public DisplayCounterObserver(int initialCount) {
        if (initialCount < 0) {
            throw new IllegalArgumentException(
                    "Count cannot be negative"
            );
        }

        count = initialCount;
    }

    @Override
    public void onArtworkAdded(
            ArtworkAddedEvent event
    ) {
        count = event.artworkCount();

        System.out.println(
                "Display counter: " +
                        count + " artwork(s)"
        );
    }

    public int getCount() {
        return count;
    }
}
