/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import utilities.ColorPalette;
import utilities.Employee;
import utilities.Product;
import utilities.Styling;

/**
 *
 * @author laiba
 */
public class GenerateBillView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel dashboardPanel;
    private JPanel centerPanel;
    private JPanel leftPane, rightPane;
    private JPanel productsPanel; // in left pane
    private JPanel billItems; // in right pane :
    private JButton confirmBtn, backBtn;
    private JCheckBox cashCheckBox;
    private String empName;
    
    private HashMap<Product, JButton> productCards; // track product card buttons
    private HashMap<String, Integer> inBill;
    
    private GridBagConstraints gbc;
    
    public GenerateBillView(ArrayList<Product> products, String empName)
    {
        this.empName = empName;
        productCards = new HashMap<>();
        inBill = new HashMap<>();
        
        setTitle("Generate Bill");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(d.width, d.height); 
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        
        init();
        initProducts(products);
        setVisible(true);
    }
    
    public void init()
    {
        setLayout(new BorderLayout());
        dashboardPanel = new JPanel();
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        Styling.setDashboard(this, dashboardPanel, d, "Generate Bill", empName);
        
        // left panel
        makeLeftPanel();
        
        // right panel
        makeRightPanel();
        
        // add to frame
        add(dashboardPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPane, BorderLayout.EAST);
        Styling.footer(this);
    }
    
    public void makeLeftPanel()
    {
        leftPane = new JPanel(new BorderLayout(1, 1));
        leftPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        leftPane.setBackground(Color.WHITE);
        
        JLabel productsTitle = new JLabel("Products");
        productsTitle.setHorizontalAlignment(JLabel.CENTER);
        Styling.setLabelMainHeading(productsTitle);
        
        //----------products cards
        productsPanel = new JPanel(new GridLayout(0, 4, 2, 10));
        JScrollPane productsScroll = new JScrollPane(productsPanel);
        productsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        productsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Styling.styleScrollBar(productsScroll);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(screenSize.width * 0.30);
        productsPanel.setPreferredSize(new Dimension(width, 1000));
        productsPanel.setBackground(Color.WHITE);
        
        leftPane.add(productsTitle, BorderLayout.NORTH);
        leftPane.add(productsScroll, BorderLayout.CENTER);
        
        centerPanel.add(leftPane, BorderLayout.CENTER);
    }
    
    public void makeRightPanel()
    {
        rightPane = new JPanel(new BorderLayout());
        rightPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        rightPane.setBackground(Color.WHITE);
        
        //-----------top of the bill sidebar
        JLabel billLbl = new JLabel("Bill");
        Styling.setLabelMainHeading(billLbl);
        billLbl.setHorizontalAlignment(JLabel.CENTER);
        rightPane.setPreferredSize(new Dimension(300, getHeight()));
        
        //-----------bottom of the bill sidebar
        JPanel bottomBill = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomBill.setBackground(Color.WHITE);
        backBtn = new JButton("Back");
        confirmBtn = new JButton("Confirm Bill");
        Styling.setButton(confirmBtn);
        Styling.setButton(backBtn);
        cashCheckBox = new JCheckBox("Cash");
        cashCheckBox.setBackground(Color.WHITE);
        cashCheckBox.setFont(Styling.headingFont);        
        cashCheckBox.setFont(cashCheckBox.getFont().deriveFont(16));        
        bottomBill.add(backBtn);
        bottomBill.add(cashCheckBox);
        bottomBill.add(confirmBtn);
        
        //----------------middle of the bill sidebar: bill items
        billItems = new JPanel();
        billItems.setBackground(Color.WHITE);
        billItems.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weightx = 1;
        addRow(billItems, gbc, "Item", "Qty", "Price", true);
        
        JScrollPane billScroll = new JScrollPane(billItems);
        billScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        billScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Styling.styleScrollBar(billScroll);
        
        rightPane.add(billLbl, BorderLayout.NORTH);
        rightPane.add(billScroll, BorderLayout.CENTER);
        rightPane.add(bottomBill, BorderLayout.SOUTH);
    }
    
    public void initProducts(ArrayList<Product> products)
    {
        for(Product p : products)
        {
            JPanel card = makeProductCard(p);
            productsPanel.add(card);
        }
    }
    
    public JPanel makeProductCard(Product product)
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(100, 100)); // Adjusted size to accommodate the image
        card.setBackground(Color.WHITE);

        // Product Image
        JLabel imageLabel = new JLabel();
        ImageIcon productImage = new ImageIcon("src/Images/Products/" + product.getProductId() +".png");
        imageLabel.setIcon(new ImageIcon(productImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Product Info Panel (for ID, Name, and Prices)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
        infoPanel.setBackground(Color.WHITE);

        // Product ID
        JLabel idLabel = new JLabel("#: " + product.getProductId());
        Styling.setLabelBody(idLabel);
        idLabel.setHorizontalAlignment(JLabel.CENTER);

        // Product Name
        JLabel nameLabel = new JLabel(product.getName());
        Styling.setLabelBody(nameLabel);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(16f));
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));

        // Product Prices
        JLabel priceLbl = new JLabel(String.format("Rs.%.1f", product.getSalePrice()));
        
        Styling.setLabelBody(priceLbl);
        priceLbl.setHorizontalAlignment(JLabel.CENTER);

        // Add labels to the info panel
        infoPanel.add(idLabel);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLbl);
        
        // Add buttons to button panel
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 1));
        JButton addUnit = new JButton("Add");
        btnPanel.setBackground(Color.WHITE);
        Styling.setButton(addUnit);
        btnPanel.add(addUnit); // [0]

        // Add components to the card
        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnPanel, BorderLayout.SOUTH);
        
        productCards.put(product, addUnit);
        return card;
    }
    
    public void addProductToBill(Product p, int qty)
    {
        if (inBill.containsKey(p.getName())) 
        {
            inBill.put(p.getName(), qty);
            updateProductLabel(p.getName(), qty, p.getSalePrice());
        } else 
        {
            inBill.put(p.getName(), qty);
            String price = String.format("%.2f", p.getSalePrice() * qty);
            addRow(billItems, gbc, p.getName(), Integer.toString(qty), price, false);
            
        }
        billItems.revalidate();
        billItems.repaint();
    }
    
    private void updateProductLabel(String productName, int qty, double price) {
        for (java.awt.Component component : billItems.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().contains(productName)) {
                    String priceS = String.format("%.2f", price*qty);
                    updateRow(billItems, productName, Integer.toString(qty), priceS);
                    break;
                }
            }
        }
    }
    
    private static void addRow(JPanel panel, GridBagConstraints gbc, String left, String quantity, String right, boolean title) {
        // Left label (left-aligned)
        gbc.gridx = 0; // Column 0
        gbc.anchor = GridBagConstraints.WEST; 
        JLabel leftLbl = new JLabel(left);
        leftLbl.setName("leftLabel_" + left);
        Styling.styleBillItems(leftLbl, Color.BLACK);
        panel.add(leftLbl, gbc);

        // Quantity (center-aligned)
        gbc.gridx = 1; // Column 1
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel centerLbl = new JLabel(quantity);
        centerLbl.setName("centerLabel_" + left);
        Styling.styleBillItems(centerLbl, Color.BLACK);
        panel.add(centerLbl, gbc);

        // Right label (right-aligned)
        gbc.gridx = 2; // Column 2
        gbc.anchor = GridBagConstraints.EAST; 
        JLabel rightLbl = new JLabel(right);
        rightLbl.setName("rightLabel_" + left);
        Styling.styleBillItems(rightLbl, Color.BLACK);
        panel.add(rightLbl, gbc);
        
        if(title)
        {
            leftLbl.setFont(leftLbl.getFont().deriveFont(Font.BOLD));
            rightLbl.setFont(rightLbl.getFont().deriveFont(Font.BOLD));
            centerLbl.setFont(centerLbl.getFont().deriveFont(Font.BOLD));
        }
        
        gbc.gridy++;
    }
    
    private void updateRow(JPanel panel, String left, String quantity, String right) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getName() != null) {
                    if (label.getName().equals("leftLabel_" + left)) {
                        label.setText(left); 
                    } else if (label.getName().equals("centerLabel_" + left)) {
                        label.setText(quantity);
                    } else if (label.getName().equals("rightLabel_" + left)) {
                        label.setText(right);
                    }
                }
            }
    }
    }
    
    public void clearBill()
    {
        inBill.clear();
        billItems.removeAll();
        billItems.revalidate();
        billItems.repaint();
    }
    
    public void setConfirmBtnListener(ActionListener al)
    {
        confirmBtn.addActionListener(al);
    }
    
    public void setBackBtnActionListener(ActionListener al)
    {
        backBtn.addActionListener(al);
    }
    
    public JCheckBox getCashCheckBox() {
        return cashCheckBox;
    }

    public HashMap<Product, JButton> getProductCards() {
        return productCards;
    }
    
    
}
