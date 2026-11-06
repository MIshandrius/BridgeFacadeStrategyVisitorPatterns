package org.example.Visitor;

import org.example.Art.Painting;
import org.example.Art.Sculpture;

public class CatalogVisitor implements IArtVisitor{
    @Override
    public void visit(Painting painting){
        System.out.println("Catalog | Painting | " + painting.getTitle());
    }

    @Override
    public void visit(Sculpture sculpture) {
        System.out.println(
                "Catalog | Sculpture | " + sculpture.getTitle()
        );
    }
}
