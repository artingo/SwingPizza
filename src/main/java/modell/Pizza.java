package modell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static modell.PizzaGröße.GRÖSSEN;

/**
 * Repräsentiert eine Pizza mit Größe und Belägen
 *
 * @author Alfred Walther
 * @version 1.3
 * @since 1.0
 */
public class Pizza implements Cloneable {
    private static final Logger LOG = LogManager.getLogger(Pizza.class);

    /** die Menünummer der Pizza */
    final private int nummer;
    final private String name;
    /** die Pizza-Beläge als Text */
    private String beläge;
    /** der Grundpreis aus der CSV-Datei */
    final private double grundpreis;
    /** der Preis abhängig von der Größe */
    private double preis;
    // TODO: auf PizzaGröße umstellen
    /** normalerweise einer aus k, m, g, x */
    private String größe;
    /** URL der Pizza-Abbildung */
    final private String bild;

    public Pizza(int nummer, String name, String beläge, double grundpreis, String bild) {
        this.nummer = nummer;
        this.name = name;
        this.beläge = beläge;
        this.grundpreis = grundpreis;
        this.größe = PizzaGröße.STANDARD;
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

    /**
     * Falls die Pizza eine gültige Größe hat, wird der Aufpreis aus {@link PizzaGröße.GRÖSSEN} ermittelt
     * @return der Preis, abhängig von der Größe
     */
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

    /**
     * Die neue <code>größe</code> wird nur gesetzt, wenn sie in der GRÖSSEN-Liste vorkommt
     * @see PizzaGröße.GRÖSSEN
     * @param größe die neue Pizzagröße
     */
    public void setGröße(String größe) {
        größe = größe.toLowerCase();
        if (GRÖSSEN.containsKey(größe)) {
            this.größe = größe;
        } else {
            LOG.warn("Falsche Größe: '{}'", größe);
        }
    }

    public String getBild() {
        return bild;
    }

    /**
     * Wird benötigt, um aus dem Menü eine neue Pizza mit individueller Größe und Preis zu generieren
     * @return die neue, bestellte Pizza
     */
    @Override
    public Pizza clone() {
        try {
            return (Pizza) super.clone();
        } catch (CloneNotSupportedException e) {
            LOG.error("Fehler beim Klonen der Pizza '{}':\n{}", name, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return GRÖSSEN.get(größe).getBezeichnung() + " Pizza '" + name + "' " + beläge + ", " + getPreis() + "€";
    }

}