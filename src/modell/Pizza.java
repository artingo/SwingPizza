package modell;

import static modell.PizzaGröße.GRÖSSEN;

/**
 * Repräsentiert eine Pizza mit Größe und Belägen
 */
public class Pizza implements Cloneable {
    private static final String PIZZA_IMG = "https://www.foodandwine.com/thmb/4qg95tjf0mgdHqez5OLLYc0PNT4=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/classic-cheese-pizza-FT-RECIPE0422-31a2c938fc2546c9a07b7011658cfd05.jpg";

    private String name;
    private String beläge;
    private double preis;
    private String größe;
    private String bild;

    public Pizza(String name, String beläge, double preis) {
        this(name, beläge, preis, PIZZA_IMG);
    }

    public Pizza(String name, String beläge, double preis, String bild) {
        this.name = name;
        this.beläge = beläge;
        this.preis = preis;
        this.größe = "m";
        this.bild = bild;
    }

    public String getName() {
        return name;
    }

    public String getBeläge() {
        return beläge;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getGröße() {
        return größe;
    }

    public void setGröße(String größe) {
        this.größe = größe;
    }

    @Override
    public Pizza clone() {
        try {
            return (Pizza) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return GRÖSSEN.get(größe).getBezeichnung() + " Pizza '" + name + "' mit " + beläge + ", " + preis + "€\n";
    }
}