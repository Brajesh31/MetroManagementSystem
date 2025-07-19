
package Views;

import Controllers.ChangePasswordController;
import Models.EmployeeModel;
import Models.ProductModel;
import Models.VendorModel;
import utilities.*;

import java.util.ArrayList;
import java.util.List; // Ensure this import is correct  // Import the Product model
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DEODashboardView extends JFrame {
    private RoundedButton vendorButton;
    private RoundedButton productButton;
    private RoundedButton ChngPwButton;

    private JButton addVendorButton;
    private JButton saveProductButton;

    private JTextField vendorNameField;
    private JTextField vendorCnicField;
    private JTextField vendorPhoneField;

    private JTextField productNameField;
    private JTextField productOriginalPriceField;
    private JTextField productSalePriceField;
    private JTextField productCartonPriceField;

    private JComboBox<String> vendorComboBox;
    private JComboBox<String> productCategoryComboBox;

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel vendorPanel;
    private JPanel productPanel;
    private JPanel viewProductsPanel;
    private VendorModel vendorModel;


    private JTable productTable;

    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField searchField1;
    private JTextField searchField2; // Declare the search field at the class level


    private JPanel viewVendorsPanel; // Panel for viewing vendors
    private JTable vendorTable; // Table to display vendors
    private DefaultTableModel vendorTableModel; // Table model for vendor data

    private ProductModel productModel = new ProductModel();

    // Categories array
    private String[] categories;

    public DEODashboardView(VendorModel vendorModel, Employee employee) {
        this.vendorModel = vendorModel;

        setTitle("Data Entry Operator Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        initializeUI(employee);
        setVisible(true);
    }

    private void initializeUI(Employee employee) {
        // Create the header panel
        JPanel headerPanel = new JPanel();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        // Initialize the main panel
        mainPanel = new JPanel(new CardLayout());
        JPanel centerPanel = createCenterPanel(employee);
        contentPanel = new JPanel(new CardLayout());

        // Create the panels for vendors and products
        createVendorPanel();
        createProductPanel();
        createViewProductsPanel();
        createViewVendorsPanel();

        // Add the panels to the content panel
        contentPanel.add(vendorPanel, "VendorPanel");
        contentPanel.add(productPanel, "ProductPanel");
        contentPanel.add(viewProductsPanel, "ViewProductsPanel");
        contentPanel.add(viewVendorsPanel, "ViewVendorsPanel");

        // Add the center panel and content panel to the main panel
        mainPanel.add(centerPanel, "CenterPanel");
        mainPanel.add(contentPanel, "ContentPanel");

        // Create and set up the complete dashboard
        Styling.setDashboard(this, headerPanel, d, "DEO DASHBOARD", employee.getName());

        // Add the header panel to the top of the dashboard
        add(headerPanel, BorderLayout.NORTH);
        Styling.footer(this);

        // Add the vertical panel to the center of the layout
        add(mainPanel,BorderLayout.CENTER);
    }

    private JPanel createCenterPanel(Employee employee) {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        vendorButton = new RoundedButton("Vendor Management", 30);
        productButton = new RoundedButton("Product Management", 30);
        RoundedButton viewProductsButton = new RoundedButton("View Products", 30); // New button
        // New button for viewing products
        RoundedButton viewVendorsButton = new RoundedButton(("View Vendors"), 30);
        ChngPwButton = new RoundedButton("Change Password",30);

        vendorButton.setIcon(new ImageIcon("src/Images/vendor.png"));
        productButton.setIcon(new ImageIcon("src/Images/cart.png"));
        viewProductsButton.setIcon(new ImageIcon("src/Images/product.png")); // Placeholder icon
        viewVendorsButton.setIcon(new ImageIcon("src/Images/ViewVendors.png"));
        ChngPwButton.setIcon(new ImageIcon("src/Images/change_pwd_small.png"));

        Styling.setBigButton(vendorButton);
        Styling.setBigButton(productButton);
        Styling.setBigButton(viewProductsButton);
        Styling.setBigButton(viewVendorsButton);
        Styling.setBigButton(ChngPwButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(viewVendorsButton, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        centerPanel.add(vendorButton, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        centerPanel.add(productButton, gbc);

        gbc.gridx = 3; gbc.gridy = 0;
        centerPanel.add(viewProductsButton, gbc); // Adding the new button

        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(ChngPwButton, gbc);

        vendorButton.addActionListener(e -> showPanel("VendorPanel"));
        productButton.addActionListener(e -> showPanel("ProductPanel"));
        viewProductsButton.addActionListener(e -> showPanel("ViewProductsPanel")); // Action for new button
        viewVendorsButton.addActionListener(e -> showPanel("ViewVendorsPanel"));
        ChngPwButton.addActionListener((e -> {
             EmployeeModel EmpModel = new EmployeeModel();
            new ChangePasswordController(this,employee,EmpModel,false);
            this.setVisible(false);
        }));

        return centerPanel;
    }

    private void createVendorPanel() {
        vendorPanel = new JPanel(new GridBagLayout());
        // Customizing the TitledBorder
        TitledBorder vendorBorder = BorderFactory.createTitledBorder("VENDOR MANAGEMENT");
        vendorBorder.setTitleFont(new Font("Franklin Gothic Book", Font.BOLD, 22)); // Set a larger font size
        vendorBorder.setTitleColor(ColorPalette.BLUE); // Set the color to blue for a fancy effect
        vendorBorder.setTitlePosition(TitledBorder.TOP); // Center the title at the top

        vendorPanel.setBorder(vendorBorder);
        vendorPanel.setBackground(Color.WHITE);  // Set the background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Vendor Name
        gbc.gridx = 0; gbc.gridy = 0;
        vendorPanel.add(new JLabel("Vendor Name:"), gbc);

        gbc.gridx = 1;
        vendorNameField = new JTextField(20);
        Styling.setTextField(vendorNameField);
        vendorPanel.add(vendorNameField, gbc);

        // Vendor CNIC
        gbc.gridx = 0; gbc.gridy = 1;
        vendorPanel.add(new JLabel("Vendor CNIC:"), gbc);

        gbc.gridx = 1;
        vendorCnicField = new JTextField(20);
        Styling.setTextField(vendorCnicField);
        vendorPanel.add(vendorCnicField, gbc);

        // Vendor Phone Number
        gbc.gridx = 0; gbc.gridy = 2;
        vendorPanel.add(new JLabel("Vendor Phone Number:"), gbc);

        gbc.gridx = 1;
        vendorPhoneField = new JTextField(20);
        Styling.setTextField(vendorPhoneField);
        vendorPanel.add(vendorPhoneField, gbc);

        // Add Vendor Button (aligned better)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        addVendorButton = new JButton("Add Vendor");
        Styling.setButton(addVendorButton);
        vendorPanel.add(addVendorButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        Styling.setButton(backButton);
        backButton.addActionListener(e -> showMainPanel());

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        vendorPanel.add(backButton, gbc);
    }

    private void showMainPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "CenterPanel");
    }

    private void createProductPanel() {
        productPanel = new JPanel(new GridBagLayout());

        // Customizing the TitledBorder
        TitledBorder productBorder = BorderFactory.createTitledBorder("PRODUCT MANAGEMENT");
        productBorder.setTitleFont(new Font("Franklin Gothic Book", Font.BOLD, 22)); // Set a larger font size
        productBorder.setTitleColor(ColorPalette.BLUE); // Set the color to blue for a fancy effect
        productBorder.setTitlePosition(TitledBorder.TOP); // Center the title at the top

        productPanel.setBorder(productBorder);
        productPanel.setBackground(Color.white);  // Set the background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Product Name
        gbc.gridx = 0; gbc.gridy = 0;
        productPanel.add(new JLabel("Product Name:"), gbc);

        gbc.gridx = 1;
        productNameField = new JTextField(20);
        productPanel.add(productNameField, gbc);

        // Product Category (ComboBox)
        gbc.gridx = 0; gbc.gridy = 1;
        productPanel.add(new JLabel("Product Category:"), gbc);

        gbc.gridx = 1;
        productCategoryComboBox = new JComboBox<>(); // Initialize the JComboBox
        populateCategoryComboBox(); // Populate the combo box with categories
        productCategoryComboBox.setBackground(Color.WHITE);  // Set background color
        productCategoryComboBox.setForeground(Color.BLACK);  // Set text color
        productCategoryComboBox.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font

        // Set the same preferred size for both combo boxes
        setComboBoxPreferredSize(productCategoryComboBox);

        productPanel.add(productCategoryComboBox, gbc);

        // Vendor Selection (ComboBox)
        gbc.gridx = 0; gbc.gridy = 2;
        productPanel.add(new JLabel("Select Vendor:"), gbc);

        gbc.gridx = 1;
        vendorComboBox = new JComboBox<>();
        vendorComboBox.setBackground(Color.WHITE);  // Set background color
        vendorComboBox.setForeground(Color.BLACK);  // Set text color
        vendorComboBox.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font

        // Set the same preferred size for both combo boxes
        setComboBoxPreferredSize(vendorComboBox);

        productPanel.add(vendorComboBox, gbc);

        // Product Price Fields
        gbc.gridx = 0; gbc.gridy = 3;
        productPanel.add(new JLabel("Original Price:"), gbc);
        gbc.gridx = 1;
        productOriginalPriceField = new JTextField(20);
        productPanel.add(productOriginalPriceField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        productPanel.add(new JLabel("Sale Price:"), gbc);
        gbc.gridx = 1;
        productSalePriceField = new JTextField(20);
        productPanel.add(productSalePriceField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        productPanel.add(new JLabel("Carton Price:"), gbc);
        gbc.gridx = 1;
        productCartonPriceField = new JTextField(20);
        productPanel.add(productCartonPriceField, gbc);

        // Save Product Button
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        saveProductButton = new JButton("Save Product");
        Styling.setButton(saveProductButton);
        productPanel.add(saveProductButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        Styling.setButton(backButton);
        backButton.addActionListener(e -> showMainPanel());

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        productPanel.add(backButton, gbc);
    }

    private void setComboBoxPreferredSize(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(200, 30)); // Set the same size for both combo boxes
    }

    private void populateCategoryComboBox() {
        categories = new String[]{
                "Fruits", "Vegetables", "Appliances", "Poultry", "Meat", "Dairy", "Bakery",
                "Beverages", "Snacks", "Household Items", "Personal Care", "Health & Wellness",
                "Clothing", "Footwear", "Toys & Games", "Electronics", "Furniture", "Books",
                "Stationery", "Cosmetics", "Cleaning Supplies", "Automotive", "Gardening",
                "Pet Supplies", "Baby Products", "Sports & Fitness", "Camping & Hiking",
                "Jewelry", "Tools & Hardware", "Office Supplies", "Grocery", "Seasonal Decorations"
        };

        for (String category : categories) {
            productCategoryComboBox.addItem(category);
        }
    }


    // Create a view panel for Vendors
    private void createViewVendorsPanel() {
        viewVendorsPanel = new JPanel(new BorderLayout());
        viewVendorsPanel.setBackground(Color.WHITE);

        // Create a search field panel with a centered layout
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel searchLbl = new JLabel("Search Vendor: ");
        searchLbl.setFont(new Font("Arial", Font.BOLD, 14));

        searchField1 = new JTextField(30);
        searchField1.setFont(Styling.bodyFont);
        searchField1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.BLUE, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        searchField1.setBackground(Color.WHITE);
        searchField1.setForeground(Color.BLACK);
        searchField1.setCaretColor(Color.PINK);

        searchPanel.add(searchLbl);
        searchPanel.add(searchField1);
        viewVendorsPanel.add(searchPanel, BorderLayout.NORTH);

        // Create vendor table
        vendorTableModel = new DefaultTableModel(new Object[]{
                "Vendor ID", "Name", "CNIC", "Phone Number"
        }, 0);
        vendorTable = new JTable(vendorTableModel);
        vendorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vendorTable.setDefaultEditor(Object.class, null);

        // Table styling
        vendorTable.setFont(Styling.bodyFont);
        vendorTable.setRowHeight(40);
        vendorTable.getTableHeader().setFont(Styling.headingFont);
        vendorTable.getTableHeader().setBackground(ColorPalette.BLUE);
        vendorTable.getTableHeader().setForeground(Color.WHITE);
        vendorTable.setGridColor(ColorPalette.LIGHT_GREY);
        vendorTable.setIntercellSpacing(new Dimension(10, 10));
        vendorTable.setSelectionBackground(new Color(230, 230, 255));

        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        viewVendorsPanel.add(scrollPane, BorderLayout.CENTER);

        // Add real-time filtering
        searchField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterVendorTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterVendorTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterVendorTable();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        deleteButton = new JButton("Delete Vendor");
        updateButton = new JButton("Update Vendor");
        JButton backButton = new JButton("Back");

        Styling.setButton(deleteButton);
        Styling.setButton(updateButton);
        Styling.setButton(backButton);

        deleteButton.addActionListener(e -> deleteSelectedVendor());
        updateButton.addActionListener(e -> updateSelectedVendor());
        backButton.addActionListener(e -> showMainPanel());

        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        viewVendorsPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadVendorData();
    }

    // Custom filtering for the vendor table
    private void filterVendorTable() {
        List<Vendor> vendors = new ArrayList<>();
        vendors = vendorModel.getAllVendors();
        String filterText = searchField1.getText().toLowerCase();
        vendorTableModel.setRowCount(0); // Clear the table model

        for (Vendor vendor : vendors) {
            boolean matches = vendor.getName().toLowerCase().contains(filterText) ||
                    vendor.getCnic().toLowerCase().contains(filterText) ||
                    vendor.getPhoneNumber().toLowerCase().contains(filterText);

            if (matches) {
                Object[] row = {vendor.getVendorId(), vendor.getName(), vendor.getCnic(), vendor.getPhoneNumber()};
                vendorTableModel.addRow(row);
            }
        }
    }


    private void createViewProductsPanel() {
        viewProductsPanel = new JPanel(new BorderLayout());
        viewProductsPanel.setBackground(Color.WHITE);

        // Create a search field panel with a centered layout
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel searchLbl = new JLabel("Search Product: ");
        searchLbl.setFont(new Font("Arial", Font.BOLD, 14));

        searchField2 = new JTextField(30);
        searchField2.setFont(Styling.bodyFont);
        searchField2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.BLUE, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        searchField2.setBackground(Color.WHITE);
        searchField2.setForeground(Color.BLACK);
        searchField2.setCaretColor(Color.PINK);

        searchPanel.add(searchLbl);
        searchPanel.add(searchField2);
        viewProductsPanel.add(searchPanel, BorderLayout.NORTH);

        // Create product table
        tableModel = new DefaultTableModel(new Object[]{
                "Product ID", "Vendor ID", "Name", "Category", "Original Price", "Sale Price", "Carton Price"
        }, 0);
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setDefaultEditor(Object.class, null);

        // Table styling
        productTable.setFont(Styling.bodyFont);
        productTable.setRowHeight(40);
        productTable.getTableHeader().setFont(Styling.headingFont);
        productTable.getTableHeader().setBackground(ColorPalette.BLUE);
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.setGridColor(ColorPalette.LIGHT_GREY);
        productTable.setIntercellSpacing(new Dimension(10, 10));
        productTable.setSelectionBackground(new Color(230, 230, 255));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        viewProductsPanel.add(scrollPane, BorderLayout.CENTER);

        // Add real-time filtering
        searchField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterProductTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProductTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterProductTable();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        deleteButton = new JButton("Delete Product");
        updateButton = new JButton("Update Product");
        JButton backButton = new JButton("Back");

        Styling.setButton(deleteButton);
        Styling.setButton(updateButton);
        Styling.setButton(backButton);

        deleteButton.addActionListener(e -> deleteSelectedProduct());
        updateButton.addActionListener(e -> updateSelectedProduct());
        backButton.addActionListener(e -> showMainPanel());

        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        viewProductsPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadProductData();
    }

    // Custom filtering for the product table
    private void filterProductTable() {
        // Collection to store Vendor objects


// Collection to store Product objects
        List<Product> products = new ArrayList<>();
        products = productModel.getAllProducts();
        String filterText = searchField2.getText().toLowerCase();
        tableModel.setRowCount(0); // Clear the table model

        for (Product product : products) {
            boolean matches = product.getName().toLowerCase().contains(filterText) ||
                    product.getCategory().toLowerCase().contains(filterText) ||
                    String.valueOf(product.getOriginalPrice()).contains(filterText) ||
                    String.valueOf(product.getSalePrice()).contains(filterText) ||
                    String.valueOf(product.getPriceByCarton()).contains(filterText);

            if (matches) {
                Object[] row = {
                        product.getProductId(), product.getVendorId(), product.getName(), product.getCategory(),
                        product.getOriginalPrice(), product.getSalePrice(), product.getPriceByCarton()
                };
                tableModel.addRow(row);
            }
        }
    }



    public void loadProductData() {
        tableModel.setRowCount(0); // Clear the table
        List<Product> products = productModel.searchProduct(""); // Load all products
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getProductId(),
                    product.getVendorId(),
                    product.getName(),
                    product.getCategory(),
                    product.getOriginalPrice(),
                    product.getSalePrice(),
                    product.getPriceByCarton()
            });
        }
    }

    public void loadVendorData() {
        List<Vendor> vendors = vendorModel.getAllVendors();
        // Update table model with the list of vendors
        DefaultTableModel tableModel = (DefaultTableModel) vendorTable.getModel();
        tableModel.setRowCount(0);  // Clear existing data
        for (Vendor vendor : vendors) {
            tableModel.addRow(new Object[]{vendor.getVendorId(), vendor.getName(), vendor.getCnic(), vendor.getPhoneNumber()});
        }
    }

    private void deleteSelectedVendor() {
        int selectedRow = vendorTable.getSelectedRow();
        if (selectedRow != -1) {
            int vendorId = (int) vendorTableModel.getValueAt(selectedRow, 0);

            // Delete the vendor and provide appropriate feedback
            if (vendorModel.deleteVendor(vendorId)) {
                MessageDialog.showSuccess("Vendor deleted successfully.");
                loadVendorData();  // Reload the vendor table to reflect the changes
            } else {
                MessageDialog.showFail("Failed to delete vendor.");
            }
        } else {
            MessageDialog.showFail("Please select a vendor to delete.");
        }
    }

    private void updateSelectedVendor() {
        int selectedRow = vendorTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the selected vendor ID
            int vendorId = (int) vendorTableModel.getValueAt(selectedRow, 0);
            Vendor vendor = vendorModel.getVendor(vendorId);

            if (vendor != null) {
                // Create a custom JPanel with input fields for all editable fields
                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                panel.setPreferredSize(new Dimension(400, 300));

                // Create text fields for each field (pre-fill with current values)
                JTextField nameField = new JTextField(vendor.getName());
                JTextField cnicField = new JTextField(vendor.getCnic());
                JTextField phoneNumberField = new JTextField(vendor.getPhoneNumber());

                // Add fields to the panel
                panel.add(new JLabel("Vendor Name:"));
                panel.add(nameField);
                panel.add(new JLabel("CNIC:"));
                panel.add(cnicField);
                panel.add(new JLabel("Phone Number:"));
                panel.add(phoneNumberField);

                // Show the input dialog with the panel
                int option = JOptionPane.showConfirmDialog(this, panel, "Update Vendor Details", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Validate inputs
                    String newName = nameField.getText().trim();
                    String newCnic = cnicField.getText().trim();
                    String newPhoneNumber = phoneNumberField.getText().trim();

                    if (newName.isEmpty()) {
                        MessageDialog.showFail("Name cannot be empty.");
                        return;
                    }
                    if (newCnic.length() != 13) {
                        MessageDialog.showFail("CNIC must be exactly 13 digits.");
                        return;
                    }
                    if (newPhoneNumber.length() != 11) {
                        MessageDialog.showFail("Phone number must be exactly 11 digits.");
                        return;
                    }

                    // Update vendor fields
                    vendor.setName(newName);
                    vendor.setCnic(newCnic);
                    vendor.setPhoneNumber(newPhoneNumber);

                    // Now, update the vendor in the model
                    if (vendorModel.updateVendor(vendor)) {
                        MessageDialog.showSuccess("Vendor updated successfully.");
                        loadVendorData();  // Reload the table data
                    } else {
                        MessageDialog.showFail("Failed to update vendor.");
                    }
                }
            } else {
                MessageDialog.showFail("Vendor not found.");
            }
        } else {
            MessageDialog.showFail("Please select a vendor to update.");
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            if (productModel.deleteProduct(productId)) {
                MessageDialog.showSuccess("Product Deleted Successfully");
                loadProductData();
            } else {
                MessageDialog.showFail("Failed to Delete Product");
            }
        } else {
            MessageDialog.showSuccess("Please Select a Product to Delete");
        }
    }

    private void updateSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the selected product ID
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            Product product = productModel.getProduct(productId);

            if (product != null) {
                // Create a custom JPanel with input fields for all editable fields
                JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
                panel.setPreferredSize(new Dimension(400, 300));

                // Create text fields for each field (pre-fill with current values)
                JTextField nameField = new JTextField(product.getName());
                JTextField vendorIdField = new JTextField(String.valueOf(product.getVendorId()));
                JTextField categoryField = new JTextField(product.getCategory());
                JTextField originalPriceField = new JTextField(String.valueOf(product.getOriginalPrice()));
                JTextField salePriceField = new JTextField(String.valueOf(product.getSalePrice()));
                JTextField cartonPriceField = new JTextField(String.valueOf(product.getPriceByCarton()));

                // Add fields to the panel
                panel.add(new JLabel("Product Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Vendor ID:"));
                panel.add(vendorIdField);
                panel.add(new JLabel("Category:"));
                panel.add(categoryField);
                panel.add(new JLabel("Original Price:"));
                panel.add(originalPriceField);
                panel.add(new JLabel("Sale Price:"));
                panel.add(salePriceField);
                panel.add(new JLabel("Carton Price:"));
                panel.add(cartonPriceField);

                // Show the input dialog with the panel
                int option = JOptionPane.showConfirmDialog(this, panel, "Update Product Details", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Validate and update the fields only if the user provides valid inputs
                    String newName = nameField.getText().trim();
                    String newVendorIdStr = vendorIdField.getText().trim();
                    String newCategory = categoryField.getText().trim();
                    String newOriginalPriceStr = originalPriceField.getText().trim();
                    String newSalePriceStr = salePriceField.getText().trim();
                    String newCartonPriceStr = cartonPriceField.getText().trim();

                    // Validate and update product fields if necessary
                    if (newName.isEmpty()) {
                        MessageDialog.showFail("Product name cannot be empty.");
                        return;
                    }
                    product.setName(newName);

                    if (!newVendorIdStr.isEmpty()) {
                        try {
                            int newVendorId = Integer.parseInt(newVendorIdStr);
                            product.setVendorId(newVendorId);
                        } catch (NumberFormatException e) {
                            MessageDialog.showFail("Invalid Vendor ID.");
                            return;
                        }
                    }

                    if (!newCategory.isEmpty()) {
                        product.setCategory(newCategory);
                    }

                    if (!newOriginalPriceStr.isEmpty()) {
                        try {
                            double newOriginalPrice = Double.parseDouble(newOriginalPriceStr);
                            product.setOriginalPrice(newOriginalPrice);
                        } catch (NumberFormatException e) {
                            MessageDialog.showFail("Invalid original price.");
                            return;
                        }
                    }

                    if (!newSalePriceStr.isEmpty()) {
                        try {
                            double newSalePrice = Double.parseDouble(newSalePriceStr);
                            product.setSalePrice(newSalePrice);
                        } catch (NumberFormatException e) {
                            MessageDialog.showFail("Invalid sale price.");
                            return;
                        }
                    }

                    if (!newCartonPriceStr.isEmpty()) {
                        try {
                            double newCartonPrice = Double.parseDouble(newCartonPriceStr);
                            product.setPriceByCarton(newCartonPrice);
                        } catch (NumberFormatException e) {
                            MessageDialog.showFail("Invalid carton price.");
                            return;
                        }
                    }

                    // Now, update the product in the model
                    if (productModel.updateProduct(product)) {
                        MessageDialog.showSuccess("Product updated successfully.");
                        loadProductData(); // Reload the table data
                    } else {
                        MessageDialog.showFail("Failed to update product.");
                    }
                }
            } else {
                MessageDialog.showFail("Product not found.");
            }
        } else {
            MessageDialog.showFail("Please select a product to update.");
        }
    }


    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "ContentPanel");

        CardLayout contentLayout = (CardLayout) contentPanel.getLayout();
        contentLayout.show(contentPanel, panelName);
    }
    // Getters for the components
    public JButton getVendorButton() {
        return vendorButton;
    }

    public JButton getProductButton() {
        return productButton;
    }

    public JButton getAddVendorButton() {
        return addVendorButton;
    }

    public JButton getSaveProductButton() {
        return saveProductButton;
    }

    public JTextField getVendorNameField() {
        return vendorNameField;
    }

    public JTextField getVendorCnicField() {
        return vendorCnicField;
    }

    public JTextField getVendorPhoneField() {
        return vendorPhoneField;
    }

    public JTextField getProductNameField() {
        return productNameField;
    }

    public String getProductCategoryField() {
        int index = productCategoryComboBox.getSelectedIndex();
        return categories[index];
    }

    public JTextField getProductOriginalPriceField() {
        return productOriginalPriceField;
    }

    public JTextField getProductSalePriceField() {
        return productSalePriceField;
    }

    public JTextField getProductCartonPriceField() {
        return productCartonPriceField;
    }

    public JComboBox<String> getVendorComboBox() {
        return vendorComboBox;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
