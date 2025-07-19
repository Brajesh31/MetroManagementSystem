package Views;

import utilities.ColorPalette;
import utilities.RoundedButton;
import utilities.Styling;

import javax.swing.*;
import java.awt.*;

public class SuperAdminView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    private JButton createBranchBtn = new JButton();
    private JButton addBMBtn = new JButton();
    private JButton viewBMBtn = new JButton();
    private JButton viewReportsBtn = new JButton();
    private JButton viewBranchesBtn = new JButton(); // New button

    public SuperAdminView() {
        setTitle("Super Admin");
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
        Styling.setDashboard(this, dashboardPanel, d, "Super Admin Dashboard", "Super Admin");
        Styling.footer(this);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        optionsPanel.setBackground(Color.white);

        createBranchBtn = setButtons("Create Branch", "src/Images/branchIcon.png");
        addBMBtn = setButtons("Add Branch Managers", "src/Images/branchManager.png");
        viewBMBtn = setButtons("View Branch Managers", "src/Images/viewBranchManagers.png");
        viewReportsBtn = setButtons("View Reports", "src/images/report.png");
        viewBranchesBtn = setButtons("View Branches", "src/Images/viewBranches.png"); // New button initialization

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        optionsPanel.add(createBranchBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        optionsPanel.add(addBMBtn, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        optionsPanel.add(viewBMBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        optionsPanel.add(viewReportsBtn, gbc);

        // Add the new button to the second row
        gbc.gridx = 1;
        gbc.gridy = 1;
        optionsPanel.add(viewBranchesBtn, gbc);

        add(dashboardPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton setButtons(String text, String filePath) {
        RoundedButton btn = new RoundedButton(text, 30);
        btn.setFont(Styling.headingFont);
        btn.setForeground(Color.WHITE);

        ImageIcon branchIcon = new ImageIcon(new ImageIcon(filePath).getImage());
        btn.setIcon(branchIcon);

        // Adjusting text location
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);

        Styling.setBigButton(btn);

        return btn;
    }

    // Getters
    public JButton getCreateBranchBtn() {
        return createBranchBtn;
    }

    public JButton getAddBMBtn() {
        return addBMBtn;
    }

    public JButton getViewBMBtn() {
        return viewBMBtn;
    }

    public JButton getViewReportsBtn() {
        return viewReportsBtn;
    }

    public JButton getViewBranchesBtn() {
        return viewBranchesBtn;
    }
}
