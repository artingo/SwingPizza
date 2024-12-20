package modell;

import java.util.List;

/**
 * Repräsentiert eine Bestellung mit Gesamtkosten und Liste der Pizzen
 */
public class Bestellung {
    private List<Pizza> pizzen;
    private double gesamtKosten;

    public Bestellung(List<Pizza> pizzen) {
        this.pizzen = pizzen;
    }

    public void berechneGesamtkosten() {
        for (Pizza pizza : pizzen) {
            gesamtKosten += pizza.getPreis();
        }
    }

    public String getGesamtKosten() {
        return String.format("%.2f€", gesamtKosten);
    }

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