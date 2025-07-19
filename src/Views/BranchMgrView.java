package Views;

import utilities.ColorPalette;
import utilities.Employee;
import utilities.RoundedButton;
import utilities.Styling;

import javax.swing.*;
import java.awt.*;

public class BranchMgrView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // Use screen size dimensions
    private JButton addCashier;
    private JButton addDeo;
    private JButton viewCashier;
    private JButton viewDeo;
    private JButton generateReport;
    private JButton changePwd;
    private Employee branchMgr;

    public BranchMgrView(Employee branchMgr) {
        this.branchMgr = branchMgr;
        setTitle("Branch Manager Dashboard");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(ColorPalette.LIGHT_GREY);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        init();
    }

    public void init() {
        setLayout(new BorderLayout());

        // Add Dashboard
        JPanel dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Branch Manager Dashboard", branchMgr.getName());
        Styling.footer(this);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        optionsPanel.setBackground(Color.white);

        addCashier = setButtons("Add Cashier", "src/Images/cashier.png");
        viewCashier = setButtons("View Cashier", "src/Images/viewCashier.png");
        addDeo = setButtons("Add Data Entry Operator", "src/Images/data entry.png");
        viewDeo = setButtons("View Data Entry Operator", "src/images/DEO.png");
        generateReport = setButtons("Generate Reports", "src/images/generateReport.png");
        changePwd = setButtons("Change Password", "src/images/change_pwd_small.png");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        optionsPanel.add(addCashier, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        optionsPanel.add(viewCashier, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        optionsPanel.add(addDeo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        optionsPanel.add(viewDeo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        optionsPanel.add(generateReport, gbc);


        gbc.gridx = 2;
        gbc.gridy = 1;
        optionsPanel.add(changePwd, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        add(dashboardPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);

        setVisible(true);
    }
    public JButton setButtons(String text, String filePath){
        RoundedButton btn = new RoundedButton(text, 30);
        btn.setFont(Styling.headingFont);
        btn.setForeground(Color.WHITE);

        ImageIcon branchIcon = new ImageIcon(new ImageIcon(filePath)
                .getImage());
        btn.setIcon(branchIcon);

        //adjusting text location
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);

        Styling.setBigButton(btn);

        return btn;
    }


    public JButton getAddCashier() {
        return addCashier;
    }

    public JButton getAddDeo() {
        return addDeo;
    }

    public JButton getViewCashier() {
        return viewCashier;
    }

    public JButton getViewDeo() {
        return viewDeo;
    }

    public JButton getGenerateReport() {
        return generateReport;
    }

    public JButton getChangePwd(){
        return changePwd;
    }


}
