package org.example.Visitor;

import org.example.Art.Painting;
import org.example.Art.Sculpture;

public class ConservationVisitor implements IArtVisitor {

    @Override
    public void visit(Painting painting) {
        System.out.println(
                painting.getTitle()
                        + ": protect from direct sunlight."
        );
    }

    @Override
    public void visit(Sculpture sculpture) {
        System.out.println(
                sculpture.getTitle()
                        + ": check the stability of the base."
        );
    }
}