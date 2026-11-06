package org.example.Facade;

import org.example.Art.Sculpture;
import org.example.Exhibition.Exhibition;
import org.example.Exhibition.Museum;
import org.example.ObjectType.Physical;
import org.example.ObjectType.Virtual;
import org.example.Visitor.PackingVisitor;

public class MuseumFacade {
    Exhibition museum = new Museum(
            new Sculpture("David"),
            new Physical("Marble statue"));

    public MuseumFacade(String sculptureName){
        this.museum = new Museum(
          new Sculpture(sculptureName),
          new Physical("Marble statue")
        );
    }

    public void switchFormToVirtual(){
        museum.setFormat(new Virtual("3D sculpture model"));
    }

    public void switchFormToPhysical(){
        museum.setFormat(new Physical("marble statue"));
    }

    public void showMuseum() {
        museum.show();
    }

    public void presentVirtualMuseum(){
        switchFormToVirtual();
        showMuseum();
    }

    public void presentPhysicalMuseum(){
        switchFormToPhysical();
        showMuseum();
    }

    public void packArtwork(){
        museum.applyVisitor(new PackingVisitor());
    }
}
