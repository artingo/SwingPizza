package logik;

import modell.Pizza;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOG = LogManager.getLogger(BestellSystem.class);

    // TODO: Bestellung integrieren

    /** repräsentiert das Menü der bestellbaren Pizzen */
    private final List<Pizza> menü;

    public BestellSystem() {
        menü = ladeMenü("menü.csv");
    }

    /**
     * Lädt die bestellbaren Pizzen aus einer CSV-Datei
     *
     * @param dateipfad der Pfad zur CSV-Datei
     * @return die Liste der geladenen Pizzen aus dem Menü
     */
    List<Pizza> ladeMenü(String dateipfad) {
        LOG.debug("Lade Menü aus '{}'", dateipfad);
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
        LOG.debug("Menü erfolgreich geladen: \n{}", menü);
        return menü;
    }

    public List<Pizza> getMenü() {
        return menü;
    }

    /**
     * Gibt eine Pizza aus dem Menü zurück
     *
     * @param pizzaNr die Menünummer der gewünschten Pizza
     * @param größe   die Größe der gewünschten Pizza
     * @return die bestellte Pizza oder <code>null</code>, falls die <code>pizzaNr</code> nicht im Menü existiert
     */
    public Pizza bestellePizza(int pizzaNr, String größe) {
        LOG.debug("Bestelle Pizza Nr.{}, Größe:{}'", pizzaNr, größe);
        Pizza bestelltePizza = null;

        // TODO: in eine eigene Methode auslagern
        // finde die Pizza mit der entsprechenden pizzaNr
        Optional<Pizza> menüPizza = menü.stream()
                .filter(pizza -> pizza.getNummer() == pizzaNr)
                .findFirst();

        if (menüPizza.isPresent()) {
            // es muss eine Kopie erstellt werden,
            // ansonsten haben alle bestellten Pizzen den letzten Preis!
            bestelltePizza = menüPizza.get().clone();

            // TODO: Größe in eigener Methode prüfen
            bestelltePizza.setGröße(größe);
        }

        if (bestelltePizza != null && größe.isBlank() == false) {
            LOG.debug("{} erfolgreich bestellt.", bestelltePizza);
        }
        return bestelltePizza;
    }
}