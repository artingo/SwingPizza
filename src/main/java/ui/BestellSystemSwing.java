package ui;

import logik.BestellSystem;
import modell.Bestellung;
import modell.Pizza;
import modell.PizzaGröße;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Zeigt einen {@link BestellDialog} an und steuert das {@link BestellSystem}.
 *
 * @author Alfred Walther
 * @version 1.1
 * @since 1.1
 * @see BestellDialog
 * @see BestellSystem
 */
public class BestellSystemSwing {
    // TODO: Page mit Lieferadresse
    // TODO: Page mit Zahlungsabwicklung
    // TODO: Bestellungen als CSV speichern
    // TODO: Umsatzsteuer-Protokollierung
    // TODO: Wochen- und Monatsübersicht
    // TODO: Rendite-Berechnung
    public static final String HINTERGRUND_BILD = "https://media.istockphoto.com/id/121689858/de/foto/pizzeria.jpg?s=612x612&w=0&k=20&c=tvbBNTK2ekMz0kkLA4TPOOTEk2KvjztcigCb60zMsmE=";
    private static List<Pizza> bestelltePizzen;
    private static Bestellung bestellung;
    private static BestellDialog dialog;

    public static void main(String[] args) throws Exception {
        BestellSystemSwing bs = new BestellSystemSwing();
        bestelltePizzen = new ArrayList<>();
        JFrame fenster = new JFrame("Pizza Bestellsystem");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog = new BestellDialog("Willkommen zu Alfredos Pizzeria!", BestellSystem.MENÜ, new URL(HINTERGRUND_BILD));
        fenster.add(dialog);
        fenster.pack();
        fenster.setVisible(true);

        dialog.getHinzufügenButton().addActionListener(event -> bs.pizzaHinzufügen());
        dialog.getBestellenButton().addActionListener(event -> bs.bestellungAufgeben());
    }

    /**
     * Durchläuft die Checkboxen und fügt die entsprechenden Pizzen anhand ihres Indexes zur Bestellung hinzu
     */
    void pizzaHinzufügen() {
        List<Boolean> gewähltePizzen = dialog.getAuswahl();
        for (int i = 0; i < BestellSystem.MENÜ.size(); i++) {
            // das korrespondierende Boolean ist true --> Pizza ausgewählt
            if (gewähltePizzen.get(i) == true) {
                Pizza gewähltePizza = BestellSystem.bestellePizza(i, PizzaGröße.STANDARD);
                bestelltePizzen.add(gewähltePizza);
                dialog.ausgeben(gewähltePizza + " hinzugefügt\n");
            }
        }
    }

    /**
     * Gibt die Bestellübersicht aus bzw. eine Fehlermeldung, wenn keine Pizza ausgewählt wurde
     */
    void bestellungAufgeben() {
        if (bestelltePizzen.size() > 0) {
            bestellung = new Bestellung(bestelltePizzen);
            String bestätigung = bestellung.getBestellÜbersicht();
            bestätigung = bestätigung.replace("\n", "<br>");
            dialog.ausgeben(bestätigung);

            bestelltePizzen.clear();
            bestellung = null;
        } else {
            dialog.ausgeben("Bitte wählen Sie mindestens eine Pizza aus!");
        }
    }
}