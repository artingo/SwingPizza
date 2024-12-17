package modell;

import util.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaGröße {
    public final static Map<String, PizzaGröße> GRÖSSEN = ladeGrößen("resources/größen.csv");

    private String bezeichnung;
    private double aufpreis;

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