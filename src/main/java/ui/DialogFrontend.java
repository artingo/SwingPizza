package ui;

import logik.BestellSystem;
import modell.Bestellung;
import modell.Pizza;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

import static modell.PizzaGröße.GRÖSSEN;

public class DialogFrontend {
    private static final Logger LOG = LogManager.getLogger(DialogFrontend.class);
    private BestellSystem bs;

    public static void main(String[] args) {
        LOG.info("BestellSystem gestartet");
        DialogFrontend df = new DialogFrontend();
        df.bs = new BestellSystem();
        List<Pizza> pizzen = df.bestellSchleife();
        Bestellung bestellung = new Bestellung(pizzen);
        String bestätigung = bestellung.getBestellÜbersicht();

        LOG.info("Bestellung:{}", bestätigung);
        Tools.ausgeben(bestätigung);
        LOG.info("BestellSystem beendet");
    }

    /**
     * Wird so lange durchlaufen, bis der Benutzer die Bestellung mit '0' beendet
     * @return Liste der bestellten Pizzen
     */
    private List<Pizza> bestellSchleife() {
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

            Pizza pizza = bs.bestellePizza(pizzaNr, "");
            if (pizza == null) {
                LOG.debug("Pizza mit Nr.{} nicht vorhanden", pizzaNr);
                Tools.ausgeben("Leider gibt es keine Pizza mit der Nummer " + pizzaNr);
                continue;
            }

            // 2. Größe eingeben
            String größe = Tools.textEingeben("Welche Größe? (k)lein, (m)ittel, (g)roß oder e(x)tra groß? ");
            if (!GRÖSSEN.containsKey(größe.toLowerCase())) {
                LOG.debug("Größe {} nicht vorhanden", größe);
                Tools.ausgeben("Leider gibt es die Größe '" + größe + "' nicht.");
                continue;
            }
            pizza = bs.bestellePizza(pizzaNr, größe);

            // 3. Pizza zur Bestellung hinzufügen
            pizzen.add(pizza);
            Tools.ausgeben(pizza + ", zur Bestellung hinzugefügt.");
            LOG.info("{}, zur Bestellung hinzugefügt.", pizza);

        } while (pizzaNr != 0);
        return pizzen;
    }

    private String getMenü() {
        int bestellNr = 0;
        StringBuilder sb = new StringBuilder("<html><pre>");
        sb.append(String.format("\nNr Name%-14sBeläge%-34sPreis\n", "", ""));
        sb.append("=".repeat(66));

        for (Pizza pizza : bs.getMenü()) {
            String name = String.format("%-18s", pizza.getName());
            String beläge = String.format("%-40s", pizza.getBeläge());
            String preis = String.format("%.2f€", pizza.getPreis());

            sb.append("\n" + (++bestellNr) + ". " + name + beläge + preis);
        }
        sb.append("\nBitte wählen Sie eine Pizza-Nummer aus. '0' zum Beenden.</pre></html>");
        return sb.toString();
    }

}
