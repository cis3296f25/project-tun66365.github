import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import data.DataLoader;
import model.Building;
import ui.MapPanel;
import util.Reminder;

public class Main extends JFrame {

    private MapPanel mapPanel;
    private List<Building> buildings;

    public Main() {
        setTitle("Campus Navigation Demo (Advanced)");
        setSize(720, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            buildings = DataLoader.loadBuildings("assets/campus_buildings.json");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load buildings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            buildings = java.util.Collections.emptyList();
        }

        mapPanel = new MapPanel(buildings);
        add(mapPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton routeBtn = new JButton("Route: Library \u2192 Engineering");
        JButton reminderBtn = new JButton("Set 5s Reminder for 'Library'");
        JButton clearBtn = new JButton("Clear Route");

        routeBtn.addActionListener((ActionEvent e) -> {
            Building lib = findByName("Library");
            Building eng = findByName("Engineering Hall");
            if (lib != null && eng != null) {
                mapPanel.setRoute(lib, eng);
            } else {
                JOptionPane.showMessageDialog(this, "Demo buildings not found.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        clearBtn.addActionListener((ActionEvent e) -> mapPanel.clearRoute());

        reminderBtn.addActionListener((ActionEvent e) -> {
            Reminder.schedule(5000, () -> SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this, "Class Reminder: Time to leave for Library!", "Reminder", JOptionPane.INFORMATION_MESSAGE)
            ));
            JOptionPane.showMessageDialog(this, "Reminder set! You will get a message in 5 seconds.");
        });

        controls.add(routeBtn);
        controls.add(clearBtn);
        controls.add(reminderBtn);
        add(controls, BorderLayout.SOUTH);
    }

    private Building findByName(String name) {
        for (Building b : buildings) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}