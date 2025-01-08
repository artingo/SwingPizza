package logik;

import modell.Bestellung;
import modell.Pizza;
import ui.BestellDialog;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// TODO: static-Methoden durch Instanz-Methoden ersetzen
// TODO: Page mit Lieferadresse
// TODO: Page mit Zahlungsabwicklung
// TODO: Bestellungen als CSV speichern
// TODO: Umsatzsteuer-Protokollierung
// TODO: Wochen- und Monatsübersicht
// TODO: Rendite-Berechnung
public class BestellSystemSwing {
    public final static List<Pizza> MENÜ = BestellSystem.ladeMenü("menü.csv");
    private static List<Pizza> bestelltePizzen;
    private static Bestellung bestellung;
    private static BestellDialog dialog;

    public static void main(String[] args) throws Exception {
        bestelltePizzen = new ArrayList<>();
        JFrame fenster = new JFrame("Pizza Bestellsystem");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog = new BestellDialog("Willkommen zu Alfredos Pizzeria!", MENÜ,
                new URL("https://media.istockphoto.com/id/121689858/de/foto/pizzeria.jpg?s=612x612&w=0&k=20&c=tvbBNTK2ekMz0kkLA4TPOOTEk2KvjztcigCb60zMsmE="));
        fenster.add(dialog);
        fenster.pack();
        fenster.setVisible(true);

        dialog.getHinzufügenButton().addActionListener(event -> pizzaHinzufügen());
        dialog.getBestellenButton().addActionListener(event -> bestellungAufgeben());
    }

    private static void pizzaHinzufügen() {
        List<Boolean> gewähltePizzen = dialog.getAuswahl();
        for (int i = 0; i < MENÜ.size(); i++) {
            // das korrespondierende Boolean ist true == Pizza ausgewählt
            if (gewähltePizzen.get(i)) {
                Pizza gewähltePizza = MENÜ.get(i);
                bestelltePizzen.add(gewähltePizza);
                dialog.ausgeben(gewähltePizza + " hinzugefügt\n");
            }
        }
    }

    private static void bestellungAufgeben() {
        // FIXME: leere 'bestelltePizzen' abfangen
        bestellung = new Bestellung(bestelltePizzen);
        String bestätigung = bestellung.getBestellÜbersicht();
        bestätigung = bestätigung.replace("\n", "<br>");
        dialog.ausgeben(bestätigung);

        bestelltePizzen.clear();
        bestellung = null;
    }
}