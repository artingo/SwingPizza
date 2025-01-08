package modell;

import static modell.PizzaGröße.GRÖSSEN;

/**
 * Repräsentiert eine Pizza mit Größe und Belägen
 */
public class Pizza implements Cloneable {
    private static final String PIZZA_IMG = "https://www.foodandwine.com/thmb/4qg95tjf0mgdHqez5OLLYc0PNT4=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/classic-cheese-pizza-FT-RECIPE0422-31a2c938fc2546c9a07b7011658cfd05.jpg";

    final private int nummer;
    final private String name;
    private String beläge;
    final private double grundpreis;
    private double preis;
    private String größe;
    final private String bild;

    public Pizza(int nummer, String name, String beläge, double grundpreis, String bild) {
        this.nummer = nummer;
        this.name = name;
        this.beläge = beläge;
        this.grundpreis = grundpreis;
        this.größe = "m";
        this.bild = bild;
    }

    public int getNummer() {
        return nummer;
    }

    public String getName() {
        return name;
    }

    public String getBeläge() {
        return beläge;
    }

    public double getPreis() {
        preis = grundpreis;
        // der Aufpreis wird nur verwendet, wenn es diese Größe auch gibt
        if (GRÖSSEN.containsKey(größe)) {
            preis += GRÖSSEN.get(größe).getAufpreis();
        }
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getGröße() {
        return größe;
    }

    public void setGröße(String größe) {
        größe = größe.toLowerCase();
        if (GRÖSSEN.containsKey(größe)) {
            this.größe = größe;
        }
    }

    public String getBild() {
        return bild;
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
        return GRÖSSEN.get(größe).getBezeichnung() + " Pizza '" + name + "' " + beläge + ", " + getPreis() + "€";
    }

}