
package org.example.Observer;

import java.util.ArrayList;
import java.util.List;

public final class ActivityLogObserver
        implements ArtworkObserver {

    private final List<ArtworkAddedEvent> events =
            new ArrayList<>();

    @Override
    public void onArtworkAdded(
            ArtworkAddedEvent event
    ) {
        events.add(event);

        System.out.println(
                "Activity log: artwork added to '" +
                        event.exhibitionTitle() + "'"
        );
    }

    public List<ArtworkAddedEvent> getEvents() {
        return List.copyOf(events);
    }
}
