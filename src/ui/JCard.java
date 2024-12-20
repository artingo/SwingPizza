package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class JCard extends JPanel {
    public static final int WIDTH = 240;
    public static final int HEIGHT = 150;
    private JLabel bild;
    // TODO: Radio-Button oder Slider zur Größenauswahl
    private JCheckBox checkbox;
    private JLabel beschreibung;

    public JCard(String bildUrl, String label, String beschreibung) {
        this(bildUrl, label, beschreibung, WIDTH, HEIGHT);
    }

    public JCard(String bildUrl, String label, String beschreibung, int width, int height) {
        // ordne die Komponenten vertikal an
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        bild = ladeBild(bildUrl, width, height);
        add(bild);

        checkbox = new JCheckBox(label);
        checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkbox.setForeground(Color.WHITE);
        checkbox.setOpaque(false);
        add(checkbox);

        // Mehrzeilige Beschreibungen werden mit HTML dargestellt
        this.beschreibung = new JLabel("<html><p>" + beschreibung + "</p></html>");
        this.beschreibung.setPreferredSize(new Dimension(width, 26));
        this.beschreibung.setForeground(Color.WHITE);
        this.beschreibung.setBorder(BorderFactory.createEmptyBorder(0,4,0,0));
        this.add(this.beschreibung);

    }

    public JCheckBox getCheckbox() {
        return checkbox;
    }

    private JLabel ladeBild(String bildUrl, int width, int height) {
        JLabel neuesBild = new JLabel();
        try {
            URL url = new URL(bildUrl);
            BufferedImage internetBild = ImageIO.read(url);
            Image skaliertesBild = internetBild.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            neuesBild.setIcon(new ImageIcon(skaliertesBild));
        } catch (IOException e) {
            System.err.println("Fehler beim Laden dieser URL: " + bildUrl);
        }

        neuesBild.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        neuesBild.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                checkbox.setSelected(!checkbox.isSelected());
            }
        });
        return neuesBild;
    }
}
