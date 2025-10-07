
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import model.Building;
import ui.MapPanel;
import util.Reminder;

public class Main extends JFrame {

    private final List<Building> buildings = new ArrayList<>();
    private MapPanel mapPanel;
    private JTextField inputField;
    private JLabel statusLabel;

    public Main() {
        setTitle("Campus Navigation Demo — v2.1 (Direction Edition)");
        setSize(840, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        seedBuildings();

        mapPanel = new MapPanel(buildings);
        add(mapPanel, BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Destination:"));
        inputField = new JTextField(24);
        top.add(inputField);
        JButton goBtn = new JButton("Go");
        JButton clearBtn = new JButton("Clear");
        JButton remindBtn = new JButton("Set 5s Reminder");
        top.add(goBtn);
        top.add(clearBtn);
        top.add(remindBtn);
        add(top, BorderLayout.NORTH);

        statusLabel = new JLabel("Tip: Type e.g. 'Engineering', 'Student Center', 'Tuttleman', 'Tyler' then press Go.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        add(statusLabel, BorderLayout.SOUTH);

        goBtn.addActionListener((ActionEvent e) -> handleGo());
        clearBtn.addActionListener((ActionEvent e) -> {
            mapPanel.clearRoute();
            statusLabel.setText("Route cleared.");
        });
        remindBtn.addActionListener((ActionEvent e) -> {
            Reminder.schedule(5000, () -> SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this,
                            "Class Reminder: Time to leave now!",
                            "Reminder",
                            JOptionPane.INFORMATION_MESSAGE)
            ));
            statusLabel.setText("Reminder set for 5 seconds from now.");
        });
    }

    private void handleGo() {
        String q = inputField.getText().trim();
        if (q.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please type a destination name.", "Input Needed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Building start = findByName("Library"); // fixed start
        Building dest = fuzzyFind(q);

        if (start == null) {
            JOptionPane.showMessageDialog(this, "Start 'Library' not found in data.", "Data Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dest == null) {
            JOptionPane.showMessageDialog(this, "Destination not found. Try: Engineering, Student Center, Tuttleman, Tyler.", "Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }

        mapPanel.setRoute(start, dest);

        // pixel distance -> rough meters (demo only; 1px ≈ 2m)
        double distPx = distance(start.getX(), start.getY(), dest.getX(), dest.getY());
        int meters = (int)Math.round(distPx * 2);

        String dir = bearingText(dest.getX() - start.getX(), dest.getY() - start.getY());
        String instruction = "From " + start.getName() + ", head " + dir + " towards " + dest.getName() + ".";
        statusLabel.setText(String.format("Route: %s \u2192 %s  |  Distance: %dm  |  Direction: %s  |  %s",
                start.getName(), dest.getName(), meters, dir, instruction));
    }

    private static double distance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // Turn dx,dy into human-readable direction
    private static String bearingText(int dx, int dy) {
        // y axis goes downwards, so invert for geographic sense
        double angle = Math.toDegrees(Math.atan2(-dy, dx)); // right=0°, up=90°
        if (angle < 0) angle += 360;
        String[] names = {"east", "northeast", "north", "northwest", "west", "southwest", "south", "southeast"};
        int idx = (int)Math.round(angle / 45.0) % 8;
        return names[idx];
    }

    private Building fuzzyFind(String q) {
        String qq = q.toLowerCase();
        for (Building b : buildings) {
            if (b.getName().toLowerCase().contains(qq)) return b;
        }
        return null;
    }

    private Building findByName(String name) {
        for (Building b : buildings) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }
        return null;
    }

    private void seedBuildings() {
        // Panel coordinates tuned for ~820x540 drawable area
        buildings.add(new Building("Library", 180, 300, "Charles Library"));
        buildings.add(new Building("Engineering Building", 540, 360, "College of Engineering"));
        buildings.add(new Building("Student Center", 380, 170, "Howard Gittis Student Center"));
        buildings.add(new Building("Tuttleman Learning Center", 300, 340, "Tuttleman"));
        buildings.add(new Building("Tyler School of Art", 620, 220, "Tyler"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
