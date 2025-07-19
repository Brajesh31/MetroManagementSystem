
package Views;

import Controllers.LogInOptionsController;
import utilities.ColorPalette;
import utilities.Styling;

import javax.swing.*;
import java.awt.*;

public class SplashWindowView extends JFrame {
    JProgressBar bar;
    double internetSpeed;
    Timer textAnimationTimer;
    int currentStateIndex = 0;

    public SplashWindowView(double internetSpeed) {
        this.internetSpeed = internetSpeed;
        setTitle("Loading...");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen window
        setSize(500, 500);
        getContentPane().setBackground(ColorPalette.BLUE);
        setLocationRelativeTo(null);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());

        // Header Image
        JLabel image = new JLabel(new ImageIcon("src/Images/Metro Header.jpg"));
        image.setPreferredSize(new Dimension(800, 500));

        // Modern Progress Bar
        bar = new JProgressBar(0, 100);
        bar.setFont(Styling.mainHeadingFont);
        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(500, 50));
        bar.setUI(new ModernProgressBarUI());
        bar.setForeground(ColorPalette.BLUE);
        bar.setBackground(ColorPalette.BLUE);

        // Text below the progress bar
        JLabel metroText = new JLabel("METRO MANAGEMENT SYSTEM ™");
        metroText.setFont(Styling.mainHeadingFont); // Increased font size for better visibility
        metroText.setForeground(Color.YELLOW); // Changed text color to white for better contrast
        metroText.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the text

        // Center Panel
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(ColorPalette.BLUE);
                g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);
            }
        };
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension(500, 300));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Add progress bar at the top
        centerPanel.add(bar);
        centerPanel.add(Box.createVerticalStrut(50)); // Space between progress bar and text

        // Add text, centered in the remaining space
        centerPanel.add(metroText);
        centerPanel.add(Box.createVerticalGlue()); // This will push the text towards the center between the bar and the bottom of the window

        add(image, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);

        // Fill Progress Bar
        fillProgressBar();
    }




    private void fillProgressBar() {
        for (int i = 0; i <= 100; i++) {
            bar.setValue(i);
            bar.setString(i + "%");

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
        bar.setString("Done...");
        new LogInOptionsController();
        dispose();
    }
}

class ModernProgressBarUI extends javax.swing.plaf.basic.BasicProgressBarUI {
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barWidth = progressBar.getWidth();
        int barHeight = progressBar.getHeight();
        int fillWidth = (int) (progressBar.getPercentComplete() * barWidth);

        g2d.setColor(progressBar.getBackground());
        g2d.fillRoundRect(0, 0, barWidth, barHeight, 30, 30);

        float progress = (float) progressBar.getPercentComplete();
        Color startColor = ColorPalette.BLUE;
        Color endColor = ColorPalette.YELLOW;
        Color dynamicColor = blendColors(startColor, endColor, progress);
        GradientPaint gradient = new GradientPaint(0, 0, startColor, fillWidth, 0, dynamicColor);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, fillWidth, barHeight, 30, 30);
        if (progressBar.isStringPainted()) {
            paintString(g2d, 0, 0, barWidth, barHeight, fillWidth, null);
        }
    }

    @Override
    protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
        if (progressBar.isStringPainted()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2d.setFont(Styling.headingFont);
            g2d.setColor(Color.WHITE);

            String progressString = progressBar.getString();
            FontMetrics metrics = g2d.getFontMetrics();
            int stringWidth = metrics.stringWidth(progressString);
            int stringHeight = metrics.getHeight();
            int stringX = (width - stringWidth) / 2;
            int stringY = (height + stringHeight / 2) / 2;

            g2d.drawString(progressString, stringX, stringY);
        }
    }

    private Color blendColors(Color color1, Color color2, float ratio) {
        float r = color1.getRed() + ratio * (color2.getRed() - color1.getRed());
        float g = color1.getGreen() + ratio * (color2.getGreen() - color1.getGreen());
        float b = color1.getBlue() + ratio * (color2.getBlue() - color1.getBlue());
        return new Color((int) r, (int) g, (int) b);
    }
}
