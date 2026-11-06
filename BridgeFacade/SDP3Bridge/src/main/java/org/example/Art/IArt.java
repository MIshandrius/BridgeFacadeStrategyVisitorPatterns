package org.example.Art;

import org.example.Visitor.IArtVisitor;

public interface IArt {
    default void detailArt(){
        System.out.println("Material");
    }

    void accept(IArtVisitor visitor);


}
