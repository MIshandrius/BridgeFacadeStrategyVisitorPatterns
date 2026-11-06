package org.example.Art;

import org.example.Visitor.IArtVisitor;

public class Sculpture implements IArt{
    protected String sculpture;
    @Override
    public void detailArt(){
        System.out.println("Sculpture: " + sculpture);
    }
    public Sculpture(String sculpture) {
        this.sculpture = sculpture;
    }

    public String getTitle() {
        return sculpture;
    }

    @Override
    public void accept(IArtVisitor visitor){
        visitor.visit(this);
    }
}
