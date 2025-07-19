package Views;

import Controllers.SuperAdminDashboardController;
import Models.EmployeeModel;
import Models.ReportModel;
import utilities.ColorPalette;
import utilities.Styling;

import javax.swing.*;
import java.awt.*;

public class CreateBranchView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JLabel branchCodeField;
    private JComboBox<String> cityComboBox;
    private JTextField addressField;
    private JTextField phoneField;
    private EmployeeModel empModel;
    private static JButton saveButton;
    private ReportModel reportModel;

    public CreateBranchView(EmployeeModel empModel, ReportModel reportModel) {
        this.empModel = empModel;
        this.reportModel = reportModel;

        setTitle("Create Branch");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(d.width, d.height);
        setBackground(ColorPalette.LIGHT_GREY);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());

        // Add Dashboard
        JPanel dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Super Admin - Create Branch", "Super Admin");
        add(dashboardPanel, BorderLayout.NORTH);

        // Create Branch Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Branch Code (initialize branchCodeField first)
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Branch Code:"), gbc);
        gbc.gridx = 1;
        branchCodeField = new JLabel();  // Initialize branchCodeField here
        branchCodeField.setFont(Styling.bodyFont);
        formPanel.add(branchCodeField, gbc);

        // City
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;

        // Initialize cityComboBox with Pakistani cities
        cityComboBox = new JComboBox<>(getPakistaniCities());
        cityComboBox.addActionListener(e -> generateBranchCode());
        Styling.setComboBox(cityComboBox);
        formPanel.add(cityComboBox, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        Styling.setTextField(addressField);
        formPanel.add(addressField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        Styling.setTextField(phoneField);
        formPanel.add(phoneField, gbc);

        // Button Panel (above footer)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        saveButton = new JButton("Save");
        Styling.setButton(saveButton);

        JButton cancelButton = new JButton("Cancel");
        Styling.setButton(cancelButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add action for cancel button
        cancelButton.addActionListener(e -> {
            new SuperAdminDashboardController(empModel,reportModel);
            dispose();
        });

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setPreferredSize(new Dimension(d.width, 50));
        footerPanel.add(Styling.footer(this), BorderLayout.SOUTH);

        // Main Bottom Panel (button panel + footer)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add formPanel and bottomPanel to main layout
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public void generateBranchCode() {
        String city = getSelectedCity();
        String cityCode = getCityCode(city);
        if (cityCode != null) {
            branchCodeField.setText( cityCode +"001"); // Default to the first branch until dynamically updated
        }
    }


    private String getCityCode(String city) {
        return switch (city) {
            case "Karachi" -> "KHI";
            case "Lahore" -> "LHR";
            case "Islamabad" -> "ISB";
            case "Rawalpindi" -> "RWP";
            case "Peshawar" -> "PEW";
            case "Quetta" -> "UET";
            case "Faisalabad" -> "FSD";
            case "Multan" -> "MUX";
            case "Sialkot" -> "SKT";
            case "Hyderabad" -> "HYD";
            default -> null;
        };
    }

    private String[] getPakistaniCities() {
        return new String[]{"Karachi", "Lahore", "Islamabad", "Rawalpindi", "Peshawar", "Quetta", "Faisalabad", "Multan", "Sialkot", "Hyderabad"};
    }


    // Getters for form fields
    public String getBranchCode() {
        return branchCodeField.getText();
    }

    public String getSelectedCity() {
        return (String) cityComboBox.getSelectedItem();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}
