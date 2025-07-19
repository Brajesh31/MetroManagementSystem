package Views;

//import Controllers.GenerateReportController;
import Models.EmployeeModel;
import utilities.ColorPalette;
import utilities.Employee;
import utilities.MessageDialog;
import utilities.Styling;

import javax.swing.*;
import java.awt.*;

public class GenerateReportView extends JFrame {

    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JTextField startDateField;
    private JComboBox<String> rangeTagField;
    private JButton generateButton;
    private JButton backButton;
//    private final GenerateReportController controller;

    public GenerateReportView() {
//        this.controller = new GenerateReportController();
        setTitle("Generate Report");
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

    private void init() {
        setLayout(new BorderLayout());

        // Dashboard
        JPanel dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Generate Report", "Admin");
        add(dashboardPanel, BorderLayout.NORTH);

        // Main Content Panel
        add(createFormPanel(), BorderLayout.CENTER);

        // Bottom Section
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Back Button
        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.WHITE);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backButton = new JButton("Back");
        Styling.setButton(backButton);

        backButton.addActionListener(e -> dispose());

        backPanel.add(backButton);
        JPanel footerPanel = Styling.footer(this);

        bottomPanel.add(backPanel, BorderLayout.NORTH);
        bottomPanel.add(footerPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        Styling.setLabelHeading(startDateLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(startDateLabel, gbc);

        startDateField = new JTextField(20);
        Styling.setTextField(startDateField);
        gbc.gridx = 1;
        panel.add(startDateField, gbc);

        JLabel rangeTagLabel = new JLabel("Range Tag:");
        Styling.setLabelHeading(rangeTagLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(rangeTagLabel, gbc);

        rangeTagField = new JComboBox<>(new String[]{"DAILY", "WEEKLY", "MONTHLY"});
        Styling.setComboBox(rangeTagField);
        gbc.gridx = 1;
        panel.add(rangeTagField, gbc);

        generateButton = new JButton("Generate Report");
        Styling.setButton(generateButton);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(generateButton, gbc);
        return panel;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JTextField getStartDateField() {
        return startDateField;
    }

    public JComboBox<String> getRangeTagField() {
        return rangeTagField;
    }

}
