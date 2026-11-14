package org.example.Bridge.Exhibition;

import org.example.Bridge.Art.IArt;
import org.example.Bridge.ObjectType.IObjectFormat;

public class Museum extends Exhibition{
    public Museum(
            IArt artwork,
            IObjectFormat form
    ) {
        super(artwork, form);
    }

    @Override
    public void show() {
        System.out.println("This artwork is NOT available for sale");
        showDetails();
    }
}
