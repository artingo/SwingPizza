package modell;

import util.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repräsentiert eine Liste von Pizza-Größen mit Bezeichnung und Aufpreis.
 *
 * @author Alfred Walther
 * @version 1.3
 * @since 1.0
 */
public class PizzaGröße {
    /** repräsentiert die verfügbaren Pizza-Größen */
    public final static Map<String, PizzaGröße> GRÖSSEN = ladeGrößen("größen.csv");
    public final static String STANDARD = "m";

    final private String bezeichnung;
    final private double aufpreis;

    public PizzaGröße(String bezeichnung, double aufpreis) {
        this.bezeichnung = bezeichnung;
        this.aufpreis = aufpreis;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public double getAufpreis() {
        return aufpreis;
    }

    /**
     * Lädt die verfügbaren Pizza-Größen aus einer CSV-Datei
     * @param dateipfad die CSV-Datei mit den Größen
     * @return die verfügbaren Größen als <code>Map&lt;String, PizzaGröße&gt;</code>
     */
    private static Map<String, PizzaGröße> ladeGrößen(String dateipfad) {
        List<String[]> zeilen = Tools.csvLaden(dateipfad);
        Map<String, PizzaGröße> größen = new HashMap<>(zeilen.size());

        for (String[] spalten : zeilen) {
            String kürzel = spalten[0];
            String bezeichnung = spalten[1];
            double aufpreis = Double.parseDouble(spalten[2].replace(',', '.'));
            größen.put(kürzel, new PizzaGröße(bezeichnung, aufpreis));
        }
        return größen;
    }
}