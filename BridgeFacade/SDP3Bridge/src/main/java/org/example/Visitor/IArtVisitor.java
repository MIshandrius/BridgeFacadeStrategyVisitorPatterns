package org.example.Visitor;

import org.example.Bridge.Art.Painting;
import org.example.Bridge.Art.Sculpture;

public interface IArtVisitor {
    void visit(Painting painting);

    void visit(Sculpture sculpture);
}
