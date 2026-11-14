
package org.example.Observer;

@FunctionalInterface
public interface ArtworkObserver {

    void onArtworkAdded(ArtworkAddedEvent event);

}
