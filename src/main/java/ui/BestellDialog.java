package ui;

import modell.Pizza;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.awt.BorderLayout.*;

/**
 * Stellt den das Pizza-Bestellsystem grafisch mithilfe von {@link JCard} dar
 *
 * @author Alfred Walther
 * @version 1.0
 * @since 1.0
 */
public class BestellDialog extends JPanel {
    private Image hintergrundbild;
    private JLabel titelLabel;
    private List<JCard> cards;
    private JTextPane ausgabe;
    private JButton hinzufügenButton;
    private JButton bestellenButton;

    public BestellDialog(String titel, List<Pizza> pizzen, URL hintergrundURL) {
        try {
            hintergrundbild = ImageIO.read(hintergrundURL);
        } catch (IOException e) {
            System.err.println("Konnte Hintergrundbild nicht laden: \n" + hintergrundURL);
        }

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        int zeilen = pizzen.size() / 3;
        int höhe = pizzen.size() / 3 * 255;
        setPreferredSize(new Dimension(1080, höhe));

        // TODO: Abschnitte in eigene Methoden auslagern
        titelLabel = new JLabel(titel);
        // TODO: Font, Farbe und Opaque zentralisieren
        // TODO: Fonts, Farben, Abstände, Hintergrund in properties auslagern
        titelLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titelLabel.setForeground(Color.WHITE);
        titelLabel.setOpaque(false);
        add(titelLabel, NORTH);

        cards = new ArrayList<>(pizzen.size());
        JPanel linkesPanel = new JPanel();
        linkesPanel.setLayout(new BoxLayout(linkesPanel, BoxLayout.PAGE_AXIS));
        linkesPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        linkesPanel.setOpaque(false);

        JLabel auswahlLabel = new JLabel("Bitte wählen Sie Ihre Pizzen aus:");
        auswahlLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        auswahlLabel.setForeground(Color.WHITE);
        linkesPanel.add(auswahlLabel);

        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setLayout(new GridLayout(zeilen, 3, 10, 20));
        pizzaPanel.setOpaque(false);

        for (Pizza pizza : pizzen) {
            JCard card = new JCard(pizza.getBild(), pizza.getName(), pizza.getBeläge());
            cards.add(card);
            pizzaPanel.add(card);
        }
        linkesPanel.add(pizzaPanel);
        hinzufügenButton = new JButton("Hinzufügen");

        JButton neuButton = new JButton("Neue Bestellung");
        // TODO: Checkboxen zurücksetzen
        neuButton.addActionListener(event -> ausgabe.setText(""));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        buttonPanel.add(neuButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(hinzufügenButton);

        linkesPanel.add(buttonPanel);
        add(linkesPanel, WEST);

        JPanel rechtesPanel = new JPanel();
        rechtesPanel.setLayout(new BoxLayout(rechtesPanel, BoxLayout.Y_AXIS));
        rechtesPanel.setOpaque(false);

        JLabel übersichtLabel = new JLabel("Bestellübersicht");
        übersichtLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        übersichtLabel.setForeground(Color.WHITE);
        rechtesPanel.add(übersichtLabel);

        ausgabe = new JTextPane();
        ausgabe.setContentType("text/html");
        ausgabe.setEditable(false);
        ausgabe.setMargin(new Insets(0, 5, 0, 5));
        rechtesPanel.add(new JScrollPane(ausgabe));

        bestellenButton = new JButton("Bestellen");
        rechtesPanel.add(bestellenButton);
        add(rechtesPanel, CENTER);
    }

    public JButton getHinzufügenButton() {
        return hinzufügenButton;
    }

    public JButton getBestellenButton() {
        return bestellenButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hintergrundbild != null) {
            g.drawImage(hintergrundbild, 0, 0, getWidth(), getHeight(), null);
        } else {
            setBackground(Color.BLACK);
        }
    }

    /**
     * Durchläuft die Checkboxen und befüllt die <code>List&lt;Boolean&gt;</code>
     *
     * @return List der angehakten Checkboxen
     */
    public List<Boolean> getAuswahl() {
        List<Boolean> auswahl = new ArrayList<>();
        for (JCard card : cards) {
            JCheckBox checkbox = card.getCheckbox();
            auswahl.add(checkbox.isSelected());
            checkbox.setSelected(false);
        }
        return auswahl;
    }

    /**
     * Fügt die <code>nachricht</code> auf der <code>ausgabe</code> hinzu.
     *
     * @param nachricht die neue Nachricht
     */
    public void ausgeben(String nachricht) {
        HTMLDocument doc = (HTMLDocument) ausgabe.getStyledDocument();
        try {
            doc.insertAfterEnd(
                    doc.getCharacterElement(doc.getLength()),
                    // notwendig, um den Standard-Font "Times Roman" zu umgehen
                    "<p style='font-family: Arial, Helvetica, sans-serif'>" + nachricht);
        } catch (BadLocationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
