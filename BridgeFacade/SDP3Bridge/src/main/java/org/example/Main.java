package org.example;

import org.example.Facade.ArtCollectionFacade;
import org.example.Facade.GalleryFacade;
import org.example.Facade.MuseumFacade;
import org.example.Strategy.RegularIPricing;
import org.example.Strategy.DiscountIPricing;
import org.example.Strategy.CommissionIPricing;
import org.example.Strategy.FixedDiscountIPricing;
import org.example.Builder.ExhibitionBuilder;
import org.example.Bridge.Art.Painting;
import org.example.Bridge.Art.Sculpture;
import org.example.Bridge.Exhibition.Gallery;
import org.example.Bridge.ObjectType.Virtual;
import org.example.Bridge.Price.PriceKzt;
import org.example.Observer.ActivityLogObserver;
import org.example.Observer.DisplayCounterObserver;

public class Main {
    public static void main(String[] args){
        GalleryFacade gallery = new GalleryFacade("Starry Night", 100000, "KZT");

        gallery.showGallery();

        System.out.println("-------------------");

        gallery.presentVirtualGallery();
        gallery.presentPhysicalGallery();

        System.out.println("-------------------");

        gallery.presentWithPricing(new RegularIPricing());
        gallery.presentWithPricing(new DiscountIPricing(10));
        gallery.presentWithPricing(new CommissionIPricing(15));
        gallery.presentWithPricing(new FixedDiscountIPricing(5000));
        gallery.presentWithPricing(new RegularIPricing());

        System.out.println("-------------------");

        MuseumFacade museum = new MuseumFacade("DAVID");
        museum.showMuseum();

        System.out.println("-------------------");

        museum.presentPhysicalMuseum();
        museum.presentVirtualMuseum();

        System.out.println("-------------------");

        ArtCollectionFacade collection = new ArtCollectionFacade();

        collection.packAll();
        collection.showCatalog();

        System.out.println("-------------------");

        collection.showConservationAdvice();

        System.out.println(
                "------------------- BUILDER + OBSERVER -------------------"
        );

// 1. Create an exhibition using Builder
        Gallery newExhibition = new ExhibitionBuilder()
                .title("Modern Masterpieces")
                .addArtwork(new Painting("Sunflowers"))
                .addArtwork(new Sculpture("The Thinker"))
                .format(new Virtual("Interactive 3D exhibition"))
                .currency(new PriceKzt())
                .basePriceUsd(2000)
                .pricingStrategy(new DiscountIPricing(10))
                .build();

// 2. Create two observers
        ActivityLogObserver log = new ActivityLogObserver();

        DisplayCounterObserver counter =
                new DisplayCounterObserver(
                        newExhibition.getArtworkCount()
                );

// 3. Register the observers
        newExhibition.registerObserver(log);
        newExhibition.registerObserver(counter);

// 4. Display the exhibition
        newExhibition.show();

// 5. Add another artwork (notifies both observers)
        newExhibition.addArtwork(
                new Painting("Water Lilies")
        );

        System.out.println(
                "Artworks after addition: " + counter.getCount()
        );

// 6. Remove the activity log observer
        newExhibition.removeObserver(log);

// 7. Add another artwork (only counter is notified)
        newExhibition.addArtwork(
                new Sculpture("New Sculpture")
        );

        System.out.println(
                "Log entries after unregistering logger: " +
                        log.getEvents().size()
        );





    }


}
