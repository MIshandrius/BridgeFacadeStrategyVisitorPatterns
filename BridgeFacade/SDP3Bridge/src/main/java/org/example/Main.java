package org.example;

import org.example.Facade.ArtCollectionFacade;
import org.example.Facade.GalleryFacade;
import org.example.Facade.MuseumFacade;
import org.example.Strategy.RegularIPricing;
import org.example.Strategy.DiscountIPricing;
import org.example.Strategy.CommissionIPricing;
import org.example.Strategy.FixedDiscountIPricing;

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



    }


}
