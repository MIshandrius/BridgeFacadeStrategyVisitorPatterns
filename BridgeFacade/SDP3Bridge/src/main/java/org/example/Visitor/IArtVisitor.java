package org.example.Visitor;

import org.example.Art.Painting;
import org.example.Art.Sculpture;

public interface IArtVisitor {
    void visit(Painting painting);

    void visit(Sculpture sculpture);
}
