package utilities;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private final int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setFocusPainted(false); // Remove focus paint
        setContentAreaFilled(false); // Disable default background painting
        setOpaque(false); // Ensure transparency to paint custom background
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the button's background with rounded corners
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Paint the button's text and foreground
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the border with rounded corners
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}
