package modell;

import java.util.List;

/**
 * Repräsentiert eine Bestellung mit Gesamtkosten und Liste der Pizzen
 *
 * @author Alfred Walther
 * @version 1.3
 * @since 1.0
 */
public class Bestellung {
    private List<Pizza> pizzen;
    private double gesamtKosten;

    public Bestellung(List<Pizza> pizzen) {
        this.pizzen = pizzen;
    }

    /**
     * Durchläuft die Liste der Pizzen und fügt den jeweiligen Pizza-Preis hinzu
     */
    public void berechneGesamtkosten() {
        for (Pizza pizza : pizzen) {
            gesamtKosten += pizza.getPreis();
        }
    }

    /**
     * Liefert die <code>gesamtKosten</code> mit 2 Kommastellen und Eurozeichen
     * @return
     */
    public String getGesamtKosten() {
        return String.format("%.2f€", gesamtKosten);
    }

    /**
     * Gibt diese Bestellung formatiert aus
     * @return formatierte Textdarstellung der Bestellung
     */
    public String getBestellÜbersicht() {
        berechneGesamtkosten();
        StringBuilder sb = new StringBuilder("\n<b>Bestellübersicht:</b>\n");
        for (Pizza pizza : pizzen) {
            sb.append(pizza.toString() + "\n");
        }
        sb.append("\n<b>Gesamtpreis: ").append(getGesamtKosten());
        return sb.toString();
    }
}