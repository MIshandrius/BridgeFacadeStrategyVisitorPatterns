
package org.example.Bridge.Exhibition;

import org.example.Bridge.Art.IArt;
import org.example.Bridge.ObjectType.IObjectFormat;

import java.util.List;

public class Museum extends Exhibition {

    // Original constructor
    public Museum(
            IArt artwork,
            IObjectFormat form
    ) {
        super(artwork, form);
    }

    // New constructor for multiple artworks
    public Museum(
            String title,
            List<IArt> artworks,
            IObjectFormat form
    ) {
        super(title, artworks, form);
    }

    @Override
    public void show() {
        System.out.println(
                "Museum exhibition: " + getTitle()
        );

        System.out.println(
                "This artwork is NOT available for sale"
        );

        showDetails();
    }
}
