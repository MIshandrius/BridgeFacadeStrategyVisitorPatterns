package org.example.Visitor;

import org.example.Art.Painting;
import org.example.Art.Sculpture;

import java.awt.*;

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
