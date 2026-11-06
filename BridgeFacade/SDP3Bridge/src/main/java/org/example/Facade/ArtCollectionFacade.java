package org.example.Facade;

import java.util.List;

import org.example.Art.IArt;
import org.example.Art.Painting;
import org.example.Art.Sculpture;
import org.example.Visitor.IArtVisitor;
import org.example.Visitor.PackingVisitor;
import org.example.Visitor.CatalogVisitor;
import org.example.Visitor.ConservationVisitor;

public class ArtCollectionFacade {
    private final List<IArt> artworks;

    public ArtCollectionFacade() {
        artworks = List.of(
                new Painting("Starry Night"),
                new Sculpture("David")
        );
    }

    public void packAll() {
        applyVisitor(new PackingVisitor());
    }

    public void showCatalog() {
        applyVisitor(new CatalogVisitor());
    }

    private void applyVisitor(IArtVisitor visitor) {
        for (IArt artwork : artworks) {
            artwork.accept(visitor);
        }
    }

    public void showConservationAdvice() {
        applyVisitor(new ConservationVisitor());
    }
}