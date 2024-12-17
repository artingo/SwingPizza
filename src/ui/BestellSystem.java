package ui;

import modell.Bestellung;
import modell.Pizza;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

import static modell.PizzaGröße.GRÖSSEN;

public class BestellSystem {
    public final static List<Pizza> MENÜ = ladeMenü("resources/menü.csv");

    public static void main(String[] args) {
        List<Pizza> pizzen = bestellSchleife();
        Bestellung bestellung = new Bestellung(pizzen);
        String bestätigung = bestellung.getBestellÜbersicht();
        Tools.ausgeben(bestätigung);
    }

    private static List<Pizza> ladeMenü(String dateipfad) {
        List<String[]> zeilen = Tools.csvLaden(dateipfad);
        List<Pizza> menü = new ArrayList<>(zeilen.size());

        for (String[] spalten : zeilen) {
            String name = spalten[1];
            String zutaten = spalten[2];
            double preis = Double.parseDouble(spalten[3].replace(',', '.'));
            String bild = spalten[4];
            menü.add(new Pizza(name, zutaten, preis, bild));
        }
        return menü;
    }

    private static String getMenü() {
        int bestellNr = 0;
        StringBuilder sb = new StringBuilder("<html><pre>");
        sb.append(String.format("\nNr Name%-14sBeläge%-34sPreis\n", "", ""));
        sb.append("=".repeat(66));

        for (Pizza pizza : MENÜ) {
            String name = String.format("%-18s", pizza.getName());
            String beläge = String.format("%-40s", pizza.getBeläge());
            String preis = String.format("%.2f€", pizza.getPreis());

            sb.append("\n" + (++bestellNr) + ". " + name + beläge + preis);
        }
        sb.append("\nBitte wählen Sie eine Pizza-Nummer aus. '0' zum Beenden.</pre></html>");
        return sb.toString();
    }

    private static List<Pizza> bestellSchleife() {
        List<Pizza> pizzen = new ArrayList<>();
        int pizzaNr;

        // Bestell-Schleife
        do {
            Pizza pizza = null;
            pizzaNr = Tools.zahlEingeben(getMenü());
            try {
                if (pizzaNr == 0) {
                    break;
                }
                // Pizza aus dem Menü kopieren
                pizza = MENÜ.get(pizzaNr - 1).clone();
            } catch (IndexOutOfBoundsException e) {
                Tools.ausgeben("Leider gibt es keine Pizza mit der Nummer " + pizzaNr);
                continue;
            }

            // Größe eingeben
            String größe = Tools.textEingeben("Welche Größe? (k)lein, (m)ittel, (g)roß oder e(x)tra groß? ");
            größe = größe.toLowerCase();
            if (GRÖSSEN.containsKey(größe)) {
                pizza.setGröße(größe);
                pizza.setPreis(pizza.getPreis() + GRÖSSEN.get(größe).getAufpreis());
            } else {
                Tools.ausgeben("Leider gibt es die Größe '" + größe + "' nicht.");
                continue;
            }

            // Pizza zur Bestellung hinzufügen
            pizzen.add(pizza);
            Tools.ausgeben(pizza + " zur Bestellung hinzugefügt.");

        } while (pizzaNr != 0);
        return pizzen;
    }
}