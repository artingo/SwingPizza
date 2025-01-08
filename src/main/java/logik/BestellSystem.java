package logik;

import modell.Pizza;
import util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BestellSystem {
    public final static List<Pizza> MENÜ = ladeMenü("menü.csv");

    public static List<Pizza> ladeMenü(String dateipfad) {
        List<String[]> zeilen = Tools.csvLaden(dateipfad);
        List<Pizza> menü = new ArrayList<>(zeilen.size());

        for (String[] spalten : zeilen) {
            int nummer = Integer.parseInt(spalten[0]);
            String name = spalten[1];
            String zutaten = spalten[2];
            double preis = Double.parseDouble(spalten[3].replace(',', '.'));
            String bild = spalten[4];
            menü.add(new Pizza(nummer, name, zutaten, preis, bild));
        }
        return menü;
    }

    public static Pizza bestellePizza(int pizzaNr, String größe) {
        Pizza bestelltePizza = null;

        // finde die Pizza mit der entsprechenden pizzaNr
        Optional<Pizza> menüPizza = MENÜ.stream()
                .filter(pizza -> pizza.getNummer() == pizzaNr)
                .findFirst();

        if (menüPizza.isPresent()) {
            bestelltePizza = menüPizza.get().clone();
            bestelltePizza.setGröße(größe);
        }
        return bestelltePizza;
    }
}