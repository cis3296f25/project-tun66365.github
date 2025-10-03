import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {
    public Main() {
        setTitle("Campus Navigation Demo");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLUE);
                g.fillOval(100, 150, 20, 20);
                g.drawString("Library", 130, 165);
                g.setColor(Color.RED);
                g.fillOval(300, 200, 20, 20);
                g.drawString("Engineering Hall", 330, 215);
            }
        };

        JButton reminderBtn = new JButton("Set 5s Reminder");
        reminderBtn.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Reminder set! You will get a message in 5 seconds.");
            new Timer().schedule(new TimerTask() {
                @Override public void run() {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Class Reminder: Time to leave for Library!")
                    );
                }
            }, 5000);
        });

        add(panel, BorderLayout.CENTER);
        add(reminderBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}