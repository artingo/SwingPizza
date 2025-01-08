package ui;

import logik.BestellSystem;
import modell.Bestellung;
import modell.Pizza;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

import static modell.PizzaGröße.GRÖSSEN;

public class DialogFrontend {
    public static void main(String[] args) {
        List<Pizza> pizzen = bestellSchleife();
        Bestellung bestellung = new Bestellung(pizzen);
        String bestätigung = bestellung.getBestellÜbersicht();
        Tools.ausgeben(bestätigung);
    }

    private static List<Pizza> bestellSchleife() {
        List<Pizza> pizzen = new ArrayList<>();
        int pizzaNr;

        // Bestell-Schleife
        do {
            // 1. Pizza-Nummer eingeben
            pizzaNr = Tools.zahlEingeben(getMenü());

            // mit '0' wird die Bestellung beendet
            if (pizzaNr == 0) {
                break;
            }

            Pizza pizza = BestellSystem.bestellePizza(pizzaNr, "");
            if (pizza == null) {
                Tools.ausgeben("Leider gibt es keine Pizza mit der Nummer " + pizzaNr);
                continue;
            }

            // 2. Größe eingeben
            String größe = Tools.textEingeben("Welche Größe? (k)lein, (m)ittel, (g)roß oder e(x)tra groß? ");
            if (!GRÖSSEN.containsKey(größe.toLowerCase())) {
                Tools.ausgeben("Leider gibt es die Größe '" + größe + "' nicht.");
                continue;
            }
            pizza = BestellSystem.bestellePizza(pizzaNr, größe);

            // 3. Pizza zur Bestellung hinzufügen
            pizzen.add(pizza);
            Tools.ausgeben(pizza + ", zur Bestellung hinzugefügt.");

        } while (pizzaNr != 0);
        return pizzen;
    }

    static String getMenü() {
        int bestellNr = 0;
        StringBuilder sb = new StringBuilder("<html><pre>");
        sb.append(String.format("\nNr Name%-14sBeläge%-34sPreis\n", "", ""));
        sb.append("=".repeat(66));

        for (Pizza pizza : BestellSystem.MENÜ) {
            String name = String.format("%-18s", pizza.getName());
            String beläge = String.format("%-40s", pizza.getBeläge());
            String preis = String.format("%.2f€", pizza.getPreis());

            sb.append("\n" + (++bestellNr) + ". " + name + beläge + preis);
        }
        sb.append("\nBitte wählen Sie eine Pizza-Nummer aus. '0' zum Beenden.</pre></html>");
        return sb.toString();
    }

}
