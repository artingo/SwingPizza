package logik;

import modell.Pizza;
import util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Das BestellSystem stellt Pizza-Daten zur Verfügung und erlaubt die Bestellung einer Pizza nach Nummer und Größe.
 *
 * @author Alfred Walther
 * @version 1.3
 * @since 1.0
 * @see Pizza
 * @see ui.DialogFrontend
 */
public class BestellSystem {
    // TODO: Bestellung integrieren
    /** repräsentiert das Menü der bestellbaren Pizzen */
    public final static List<Pizza> MENÜ = ladeMenü("menü.csv");

    /**
     * Lädt die bestellbaren Pizzen aus einer CSV-Datei
     *
     * @param dateipfad der Pfad zur CSV-Datei
     * @return die Liste der geladenen Pizzen aus dem Menü
     */
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

    // TODO: in Instanz verlagern
    /**
     * Gibt eine Pizza aus dem Menü zurück
     *
     * @param pizzaNr die Menünummer der gewünschten Pizza
     * @param größe   die Größe der gewünschten Pizza
     * @return die bestellte Pizza oder <code>null</code>, falls die <code>pizzaNr</code> nicht im Menü existiert
     */
    public static Pizza bestellePizza(int pizzaNr, String größe) {
        Pizza bestelltePizza = null;

        // TODO: in eine eigene Methode auslagern
        // finde die Pizza mit der entsprechenden pizzaNr
        Optional<Pizza> menüPizza = MENÜ.stream()
                .filter(pizza -> pizza.getNummer() == pizzaNr)
                .findFirst();

        if (menüPizza.isPresent()) {
            // es muss eine Kopie erstellt werden,
            // ansonsten haben alle bestellten Pizzen den letzten Preis!
            bestelltePizza = menüPizza.get().clone();

            // TODO: Größe in eigener Methode prüfen
            bestelltePizza.setGröße(größe);
        }
        return bestelltePizza;
    }
}