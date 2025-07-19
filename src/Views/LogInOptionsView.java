
package Views;

import utilities.ColorPalette;
import utilities.Styling;
import javax.swing.*;
import java.awt.*;

public class LogInOptionsView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private static JTextField enterName;
    private static JPasswordField enterPassword;
    private static JRadioButton superAdminButton;
    private static JRadioButton branchManagerButton;
    private static JRadioButton cashierButton;
    private static JRadioButton dataEntryOperatorButton;
    private static JButton loginButton;
    private static JButton exitButton;

    public LogInOptionsView() {
        setTitle("Login");
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

        JLabel groceryImage = new JLabel(new ImageIcon(new ImageIcon("src/Images/GroceryShopping.jpg")
                .getImage()
                .getScaledInstance(d.width / 2, d.height, Image.SCALE_SMOOTH)));

        JPanel loginForm = new JPanel();
        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
        loginForm.setOpaque(false);
        loginForm.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel logo = new JLabel(new ImageIcon(new ImageIcon("src/Images/MetroLogo.png")
                .getImage()
                .getScaledInstance(d.width / 4, d.height / 4, Image.SCALE_SMOOTH)));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginForm.add(logo);

        JLabel loginlbl = new JLabel("LOGIN");
        loginlbl.setFont(new Font("Arial", Font.BOLD, 36));
        loginlbl.setForeground(ColorPalette.BLUE);
        loginlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginForm.add(Box.createRigidArea(new Dimension(0, 40)));
        loginForm.add(loginlbl);

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        radioButtonPanel.setOpaque(false);

        superAdminButton = new JRadioButton("Super Admin");
        branchManagerButton = new JRadioButton("Branch Manager");
        cashierButton = new JRadioButton("Cashier");
        dataEntryOperatorButton = new JRadioButton("Data-Entry Operator");

        ButtonGroup group = new ButtonGroup();
        group.add(superAdminButton);
        group.add(branchManagerButton);
        group.add(cashierButton);
        group.add(dataEntryOperatorButton);

        customizeRadioButton(superAdminButton);
        customizeRadioButton(branchManagerButton);
        customizeRadioButton(cashierButton);
        customizeRadioButton(dataEntryOperatorButton);

        radioButtonPanel.add(superAdminButton);
        radioButtonPanel.add(branchManagerButton);
        radioButtonPanel.add(cashierButton);
        radioButtonPanel.add(dataEntryOperatorButton);

        loginForm.add(radioButtonPanel);

        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        credentialsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel enterUsernamelbl = new JLabel("Email:  ");
        Styling.setLabelHeading(enterUsernamelbl);
        enterName = new JTextField();
        Styling.setTextField(enterName);

        JLabel enterPasswordlbl = new JLabel("Password:  ");
        Styling.setLabelHeading(enterPasswordlbl);
        enterPassword = new JPasswordField();
        Styling.setPasswordField(enterPassword);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        credentialsPanel.add(enterUsernamelbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        credentialsPanel.add(enterName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        credentialsPanel.add(enterPasswordlbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        credentialsPanel.add(enterPassword, gbc);

        loginForm.add(credentialsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        loginButton = new JButton("LOGIN");

        exitButton = new JButton("EXIT");

        Styling.setButton(loginButton);
        Styling.setButton(exitButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        loginForm.add(Box.createRigidArea(new Dimension(0, 30)));
        loginForm.add(buttonPanel);

        add(groceryImage, BorderLayout.WEST);
        add(loginForm, BorderLayout.CENTER);

        setVisible(true);
    }

    private void customizeRadioButton(JRadioButton radioButton) {
        radioButton.setFont(new Font("Bell MT", Font.PLAIN, 18));
        radioButton.setForeground(ColorPalette.BLUE);

        radioButton.setBorder(BorderFactory.createLineBorder(ColorPalette.BLUE, 2));
        radioButton.setBackground(Color.white);
        radioButton.setContentAreaFilled(false);
        radioButton.setFocusPainted(false);
        radioButton.setOpaque(true);

        radioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                radioButton.setBackground(new Color(230, 230, 255));
            }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    radioButton.setBackground(Color.white);
                }
            });

        radioButton.addActionListener(e -> {
                if (radioButton.isSelected()) {
                    radioButton.setBackground(new Color(130, 230, 255));
                } else {
                    radioButton.setBackground(Color.white);
                }
            });
        }

        public String getUsername() {
            return enterName.getText();
        }

        public String getPassword() {
            return new String(enterPassword.getPassword());
        }

        public JButton getLoginButton(){
            return loginButton;
        }

        public JButton getExitButton(){
            return exitButton;
        }

        public String getSelectedRole() {
            if (superAdminButton.isSelected()) {
                return "Super Admin";
            } else if (branchManagerButton.isSelected()) {
                return "B";
            } else if (cashierButton.isSelected()) {
                return "C";
            } else if (dataEntryOperatorButton.isSelected()) {
                return "D";
            }
            return "";
        }
    }
