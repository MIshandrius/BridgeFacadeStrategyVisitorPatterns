package org.example.Visitor;

import org.example.Bridge.Art.Painting;
import org.example.Bridge.Art.Sculpture;

public class PackingVisitor implements IArtVisitor {
    @Override
    public void visit(Painting painting){
        System.out.println("Painting Package: Protect the frame and use flat crate ");
    }

    @Override
    public void visit(Sculpture sculpture){
        System.out.println("Sculpture Package: Secure the base and use rigid crate");
    }
}
