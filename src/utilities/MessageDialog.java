package utilities;

import Controllers.GenerateBillController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MessageDialog {
    public static void showSuccess(String message) {
        showStyledMessage(message, "Success", "success");
    }

    public static void showFail(String message) {
        showStyledMessage(message, "Error", "error");
    }

    private static Icon createCustomIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        return new ImageIcon(icon.getImage());
    }
    private static ImageIcon createTitleBarIcon(String path) {
        return new ImageIcon(path);
    }

    public static void showStyledMessage(String message, String title, String type) {
        // Label text
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(Styling.bodyFont);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Icon icon = null;
        if ("success".equalsIgnoreCase(type)) {
            // Add your success icon image path here
            icon = createCustomIcon("src/Images/success-icon.png");
        } else if ("error".equalsIgnoreCase(type)) {
            // Add your error icon image path here
            icon = createCustomIcon("src/Images/error-icon.png");
        }

        // Custom dialog
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(ColorPalette.BLUE);

        // Icon and text panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ColorPalette.BLUE);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        mainPanel.add(iconLabel, BorderLayout.WEST);
        mainPanel.add(messageLabel, BorderLayout.EAST);
        dialog.add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorPalette.BLUE);
        JButton okButton = new JButton("OK");
        Styling.setButton(okButton);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public static void showConfirmation(String message, Runnable onYes, Runnable onNo) {
        // Label text
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(Styling.bodyFont);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add your confirmation icon image path here
        Icon icon = createCustomIcon("src/Images/warning.png");


        // Custom dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Confirmation");
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(ColorPalette.BLUE);
        dialog.setIconImage(createTitleBarIcon("src/Images/warning.png").getImage());



        // Icon and text panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ColorPalette.BLUE);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        mainPanel.add(iconLabel, BorderLayout.WEST);
        mainPanel.add(messageLabel, BorderLayout.EAST);
        dialog.add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorPalette.BLUE);

        JButton yesButton = new JButton("Yes");
        Styling.setButton(yesButton);
        yesButton.addActionListener(e -> {
            if (onYes != null) {
                onYes.run();
            }
            dialog.dispose();
        });

        JButton noButton = new JButton("No");
        Styling.setButton(noButton);
        noButton.addActionListener(e -> {
            if (onNo != null) {
                onNo.run();
            }
            dialog.dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public static void showBillAndConfirmation(Bill bill, GenerateBillController controller)
    {
        // custom dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Confirm bill");
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(ColorPalette.BLUE);
        
        // main outer panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ColorPalette.BLUE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        // center inner panel with bill items
        JPanel billItems = new JPanel(new GridBagLayout());
        billItems.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addRow(billItems, gbc, "Item", "Qty", "Price", true);
        for (Map.Entry<Product, Integer> entry : bill.getProducts_qty().entrySet()) {
            gbc.gridy++; 
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            String price = String.format("%.2f", product.getSalePrice() * quantity);
            addRow(billItems, gbc, product.getName(), quantity.toString(), price, false);
        }
        
        gbc.gridy++;
        gbc.weighty = 1;
        billItems.add(new JLabel(), gbc);
        
        JScrollPane billScroll = new JScrollPane(billItems);
        billScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        billScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Styling.styleScrollBar(billScroll);
        mainPanel.add(billScroll);
        
        // bottom inner panel with totals
        JPanel totals = new JPanel(new GridLayout(0, 1));
        totals.setBackground(Color.WHITE);
        
        JLabel totalLbl = new JLabel(String.format("Total: Rs. %.2f", bill.getTotalAmount()));
        Styling.styleBillItems(totalLbl, Color.BLACK);
        totalLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel taxLbl = new JLabel(String.format("Tax: Rs. %.2f", bill.getTax()));
        Styling.styleBillItems(taxLbl, Color.BLACK);
        taxLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel netLbl = new JLabel(String.format("Net Amt: Rs. %.2f", bill.getNetAmount()));
        Styling.styleBillItems(netLbl, Color.BLACK);
        netLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        totals.add(totalLbl);
        totals.add(taxLbl);
        totals.add(netLbl);
        mainPanel.add(totals, BorderLayout.SOUTH);

        dialog.add(mainPanel, BorderLayout.CENTER);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorPalette.BLUE);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("CLEAR");
        Styling.setButton(okButton);
        Styling.setButton(cancelButton);
        okButton.addActionListener(e -> {
            controller.addBill();
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> {
            controller.reinitializeBill();
            dialog.dispose();
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
    
    private static void addRow(JPanel panel, GridBagConstraints gbc, String left, String quantity, String right, boolean title) {
        // Left label (left-aligned)
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST; 
        JLabel leftLbl = new JLabel(left);
        Styling.styleBillItems(leftLbl, Color.BLACK);
        panel.add(leftLbl, gbc);

        // Quantity (center-aligned)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel centerLbl = new JLabel(quantity);
        Styling.styleBillItems(centerLbl, Color.BLACK);
        panel.add(centerLbl, gbc);

        // Right label (right-aligned)
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST; 
        JLabel rightLbl = new JLabel(right);
        Styling.styleBillItems(rightLbl, Color.BLACK);
        panel.add(rightLbl, gbc);
        
        if(title)
        {
            leftLbl.setFont(leftLbl.getFont().deriveFont(Font.BOLD));
            rightLbl.setFont(rightLbl.getFont().deriveFont(Font.BOLD));
            centerLbl.setFont(centerLbl.getFont().deriveFont(Font.BOLD));
        }
    }
}
