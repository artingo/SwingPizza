package logik;

import modell.Pizza;
import modell.PizzaGröße;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import util.Tools;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BestellSystemTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/menü.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("ladeMenü(): CSV-Datei intakt")
    void ladeMenü_aus_CSV(int nr, String name, String zutaten, String preis, String bild) {
        assertNotNull(nr);
        assertNotNull(name);
        assertNotNull(zutaten);

        assertNotNull(preis);
        String amerikanischerPreis = preis.replace(',', '.');
        assertDoesNotThrow(() -> Double.parseDouble(amerikanischerPreis));
        double preisDouble = Double.parseDouble(amerikanischerPreis);
        // keine mittlere Pizza soll teurer als 10€ sein.
        assertTrue(preisDouble > 0 && preisDouble < 10.0);

        assertNotNull(bild);
        assertTrue(bild.startsWith("http"));
    }

    @Test
    @DisplayName("ladeMenü(): korrekte Anzahl Pizzen")
    void ladeMenü_anzahlPizzen() {
        List<Pizza> pizzen = BestellSystem.MENÜ;
        List<String[]> csvZeilen = Tools.csvLaden("menü.csv");
        assertEquals(csvZeilen.size(), pizzen.size());
    }

    @Test
    @DisplayName("Pizza Salami in allen Größen")
    void bestellePizza_Salami_alle_Größen() {
        String[] größen = {"k", "m", "g", "x"};
        Pizza salami = BestellSystem.MENÜ.get(0);

        for (String größe : größen) {
            Pizza aktuellePizza = BestellSystem.bestellePizza(salami.getNummer(), größe);
            double größenPreis = salami.getPreis() + PizzaGröße.GRÖSSEN.get(größe).getAufpreis();
            assertEquals(größenPreis, aktuellePizza.getPreis());
        }
    }

    @Test
    @DisplayName("Pizza Salami in falscher Größe")
    void bestellePizza_Salami_falsche_Größe() {
        Pizza salami = BestellSystem.MENÜ.get(0);
        Pizza aktuellePizza = BestellSystem.bestellePizza(salami.getNummer(), "zz");
        assertEquals(aktuellePizza.getPreis(), salami.getPreis());
    }
}