package ui;

import model.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MapPanel extends JPanel {

    private final List<Building> buildings;
    private Building from;
    private Building to;
    private Building hover;

    public MapPanel(List<Building> buildings) {
        this.buildings = buildings;
        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hover = hit(e.getX(), e.getY());
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Building b = hit(e.getX(), e.getY());
                if (b != null) {
                    if (from == null) from = b;
                    else if (to == null) to = b;
                    else { from = b; to = null; }
                    repaint();
                    String role = (to == null) ? "From" : "To";
                    JOptionPane.showMessageDialog(MapPanel.this,
                            role + " set: " + b.getName(),
                            "Selection", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void setRoute(Building from, Building to) {
        this.from = from;
        this.to = to;
        repaint();
    }

    public void clearRoute() {
        this.from = null;
        this.to = null;
        repaint();
    }

    private Building hit(int x, int y) {
        for (Building b : buildings) {
            int dx = x - b.getX();
            int dy = y - b.getY();
            if (dx*dx + dy*dy <= 12*12) return b;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(240,240,240));
        for (int i=0;i<getWidth();i+=40) g.drawLine(i,0,i,getHeight());
        for (int j=0;j<getHeight();j+=40) g.drawLine(0,j,getWidth(),j);

        for (Building b : buildings) {
            if (b == hover) g.setColor(new Color(0,120,255));
            else g.setColor(new Color(30,144,255));
            g.fillOval(b.getX()-8, b.getY()-8, 16, 16);
            g.setColor(Color.BLACK);
            g.drawString(b.getName(), b.getX()+10, b.getY()+4);
        }

        if (from != null && to != null) {
            g.setColor(new Color(220,20,60));
            ((Graphics2D)g).setStroke(new BasicStroke(3f));
            g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
            g.setColor(Color.DARK_GRAY);
            g.drawString("Route: " + from.getName() + " -> " + to.getName(), 12, 18);
        }
    }
}