package util;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Diese Klasse stellt Hilfsmethoden zur Ein- und Ausgabe zur Verfügung
 *
 * @author Name Vorname
 * @version 1.1
 */
public class Tools {
    public static final String CSV_SEPARATOR = ";";
    private static final ClassLoader LOADER = Tools.class.getClassLoader();


    /**
     * Zeigt einen Eingabedialog und gibt die Benutzereingabe als 'int' zurück
     *
     * @param nachricht as String
     * @return benutzerEingabe as int
     */
    public static int zahlEingeben(String nachricht) {
        return (int) zahlEingeben(nachricht, Integer::parseInt);
    }

    /**
     * Zeigt einen Eingabedialog und gibt die Benutzereingabe als 'double' zurück
     *
     * @param nachricht as String
     * @return benutzerEingabe as double
     */
    public static double kommazahlEingeben(String nachricht) {
        return (double) zahlEingeben(nachricht, Double::parseDouble);
    }

    /**
     * Wiederholt den Eingabedialog so lange, bis der Benutzer eine korrekte Zahl eingibt
     *
     * @param nachricht     as String
     * @param parseFunktion as Function
     * @return eingegebeneZahl as Number
     */
    public static Number zahlEingeben(String nachricht, Function<String, Number> parseFunktion) {
        Number zahlEingabe = 0.0;
        boolean istUngültig = false;

        do {
            String fehlermeldung = istUngültig ? "Bitte geben Sie eine gültige Zahl ein!\n" : "";
            String eingabe = JOptionPane.showInputDialog(fehlermeldung + nachricht);

            if (eingabe == null) {
                ausgeben("Das Programm wird beendet.");
                System.exit(0);
            }

            // ersetze das deutsche Zahlenformat durch das amerikanische Zahlenformat
            eingabe = eingabe.replace(",", ".");

            try {
                // rufe die übergebene parseFunktion auf
                zahlEingabe = parseFunktion.apply(eingabe);
                istUngültig = false;
            } catch (Exception e) {
                istUngültig = true;
            }
        } while (istUngültig);

        return zahlEingabe;
    }

    /**
     * Zeigt einen Eingabedialog und gibt die Benutzereingabe als 'String' zurück
     *
     * @param nachricht as String
     * @return benutzerEingabe as String
     */
    public static String textEingeben(String nachricht) {
        String eingabe = JOptionPane.showInputDialog(nachricht);
        return (eingabe == null || eingabe.isBlank()) ? "" : eingabe;
    }

    /**
     * Gibt eine Nachricht als MessageDialog aus.
     *
     * @param nachricht as String
     */
    public static void ausgeben(String nachricht) {
        JOptionPane.showMessageDialog(null, nachricht, "Auswahl", JOptionPane.QUESTION_MESSAGE);
    }

    public static List<String[]> csvLaden(String dateipfad) {
        List<String[]> daten = new ArrayList<>();
        List<String> zeilen;
        Path pfad = null;
        try {
            pfad = Paths.get(Objects.requireNonNull(LOADER.getResource(dateipfad)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (Files.exists(pfad)) {
            try {
                zeilen = Files.readAllLines(pfad);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 1; i < zeilen.size(); i++) {
                String zeile = zeilen.get(i);
                if (zeile.isBlank()) {
                    continue;
                }
                String[] spalten = zeile.split(CSV_SEPARATOR);
                if (spalten.length == 0) {
                    continue;
                }
                daten.add(spalten);
            }
        } else {
            throw new RuntimeException(dateipfad + " wurde nicht gefunden!");
        }
        return daten;
    }
}
