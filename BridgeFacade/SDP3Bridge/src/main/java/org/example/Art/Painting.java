package org.example.Art;


import org.example.Visitor.IArtVisitor;

public class Painting implements IArt {
    protected String painting;

    public Painting(String painting) {
        this.painting = painting;
    }

    public String getTitle() {
        return painting;
    }

    @Override
    public void detailArt(){
        System.out.println("Painting: " + painting);
    }

    @Override
    public void accept(IArtVisitor visitor){
        visitor.visit(this);
    }
}
