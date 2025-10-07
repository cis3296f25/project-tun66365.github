
package ui;

import model.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class MapPanel extends JPanel {

    private final List<Building> buildings;
    private Building from;
    private Building to;

    // Scale assumptions for the demo:
    // 1 px ≈ 2 m, scale bar length = 50 px ≈ 100m
    private static final int SCALE_BAR_PX = 50;
    private static final String SCALE_BAR_LABEL = "\u2248100m";

    public MapPanel(List<Building> buildings) {
        this.buildings = buildings;
        setBackground(Color.WHITE);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintBackground(g2);
        paintGrid(g2);
        paintBuildings(g2);
        paintRoute(g2);
        paintCompass(g2);
        paintScaleBar(g2);

        g2.dispose();
    }

    private void paintBackground(Graphics2D g2) {
        Color top = new Color(245, 246, 248);
        Color bottom = new Color(230, 232, 236);
        GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintGrid(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 20));
        for (int i = 0; i < getWidth(); i += 40) g2.drawLine(i, 0, i, getHeight());
        for (int j = 0; j < getHeight(); j += 40) g2.drawLine(0, j, getWidth(), j);
    }

    private void paintBuildings(Graphics2D g2) {
        for (Building b : buildings) {
            Paint old = g2.getPaint();
            GradientPaint grad = new GradientPaint(
                    b.getX()-10, b.getY()-10, new Color(88, 163, 255),
                    b.getX()+10, b.getY()+10, new Color(26, 115, 232));
            g2.setPaint(grad);
            g2.fillOval(b.getX()-9, b.getY()-9, 18, 18);
            g2.setPaint(old);

            g2.setColor(new Color(255,255,255,180));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(b.getX()-9, b.getY()-9, 18, 18);

            g2.setColor(new Color(35, 35, 35));
            g2.drawString(b.getName(), b.getX()+12, b.getY()+5);
        }
    }

    private void paintRoute(Graphics2D g2) {
        if (from == null || to == null) return;

        // shadow
        g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(0, 0, 0, 40));
        g2.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

        // main line
        g2.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(220, 20, 60, 220));
        g2.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

        // endpoints
        g2.setColor(new Color(34,139,34)); // start
        g2.fillOval(from.getX()-6, from.getY()-6, 12, 12);
        g2.setColor(new Color(178,34,34)); // end
        g2.fillOval(to.getX()-6, to.getY()-6, 12, 12);

        // direction arrow at mid-point
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double angle = Math.atan2(dy, dx);
        int midX = (from.getX() + to.getX()) / 2;
        int midY = (from.getY() + to.getY()) / 2;
        int arrowLength = 16;

        Graphics2D gArrow = (Graphics2D) g2.create();
        gArrow.setColor(new Color(220, 20, 60, 200));
        gArrow.setStroke(new BasicStroke(2f));
        AffineTransform at = AffineTransform.getTranslateInstance(midX, midY);
        at.rotate(angle);
        gArrow.setTransform(at);
        Polygon arrow = new Polygon();
        arrow.addPoint(0, 0);
        arrow.addPoint(-arrowLength, 5);
        arrow.addPoint(-arrowLength, -5);
        gArrow.fill(arrow);
        gArrow.dispose();
    }

    private void paintCompass(Graphics2D g2) {
        int radius = 34;
        int cx = getWidth() - 70; // shifted inward
        int cy = 70;              // shifted downward

        g2.setColor(new Color(255,255,255,210));
        g2.fillOval(cx - radius, cy - radius, radius*2, radius*2);
        g2.setColor(new Color(0,0,0,110));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(cx - radius, cy - radius, radius*2, radius*2);

        g2.drawLine(cx - radius + 6, cy, cx + radius - 6, cy);
        g2.drawLine(cx, cy - radius + 6, cx, cy + radius - 6);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 11f));
        g2.drawString("N", cx - 4, cy - radius + 14);
        g2.drawString("S", cx - 3, cy + radius - 6);
        g2.drawString("W", cx - radius + 6, cy + 4);
        g2.drawString("E", cx + radius - 12, cy + 4);

        Polygon p = new Polygon();
        p.addPoint(cx, cy - radius + 8);
        p.addPoint(cx - 6, cy);
        p.addPoint(cx + 6, cy);
        g2.setColor(new Color(220,20,60,200));
        g2.fillPolygon(p);
        g2.setColor(new Color(0,0,0,120));
        g2.drawPolygon(p);
    }

    private void paintScaleBar(Graphics2D g2) {
        int margin = 14;
        int x = getWidth() - SCALE_BAR_PX - margin - 20; // slight shift for balance
        int y = getHeight() - 30;

        g2.setColor(new Color(255,255,255,210));
        g2.fillRoundRect(x - 10, y - 16, SCALE_BAR_PX + 20, 28, 10, 10);
        g2.setColor(new Color(0,0,0,110));
        g2.drawRoundRect(x - 10, y - 16, SCALE_BAR_PX + 20, 28, 10, 10);

        int seg = SCALE_BAR_PX / 4;
        for (int i = 0; i < 4; i++) {
            g2.setColor((i % 2 == 0) ? Color.BLACK : Color.WHITE);
            g2.fillRect(x + i*seg, y - 8, seg, 8);
            g2.setColor(Color.BLACK);
            g2.drawRect(x + i*seg, y - 8, seg, 8);
        }

        g2.setColor(new Color(30, 30, 30));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 11f));
        g2.drawString(SCALE_BAR_LABEL, x + SCALE_BAR_PX/2 - 18, y - 12);
    }
}
