package org.example.Facade;

import org.example.Art.Painting;
import org.example.Exhibition.Gallery;
import org.example.ObjectType.Physical;
import org.example.ObjectType.Virtual;
import org.example.Price.IPrice;
import org.example.Price.PriceEur;
import org.example.Price.PriceKzt;
import org.example.Price.PriceUsd;

import org.example.Strategy.IPricingStrategy;
import org.example.Visitor.PackingVisitor;

public class GalleryFacade {
    private final Gallery gallery;

    public GalleryFacade(String title, double amount, String currency){
        IPrice selectPrice = getCurrency(currency);

        this.gallery = new Gallery(
          new Painting(title),
          new Physical("Canvas"),
          amount,
          selectPrice
        );
    }
    public void showGallery(){
        gallery.show();
    }

    public void switchFormToVirtual(){
        gallery.setFormat(new Virtual("3D artwork model"));
    }

    public void switchFormToPhysical(){
        gallery.setFormat(new Physical("Canvas"));
    }

    public void presentVirtualGallery(){
        switchFormToVirtual();
        showGallery();
    }

    public void presentPhysicalGallery(){
        switchFormToPhysical();
        showGallery();
    }

    private IPrice getCurrency(String currency){
        return switch (currency.toUpperCase()){
            case "USD" -> new PriceUsd();
            case "EUR" -> new PriceEur();
            case "KZT" -> new PriceKzt();
            default -> throw new IllegalArgumentException(
                    "Unsupported currency: " + currency
            );
        };
    }

    public void setPricingStrategy(IPricingStrategy pricingStrategy) {
        gallery.setPricingStrategy(pricingStrategy);
    }

    public void presentWithPricing(IPricingStrategy pricingStrategy) {
        gallery.setPricingStrategy(pricingStrategy);
        gallery.show();
    }

    public void packArtwork(){
        gallery.applyVisitor(new PackingVisitor());
    }





}
