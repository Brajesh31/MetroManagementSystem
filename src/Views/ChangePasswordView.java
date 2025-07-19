/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import utilities.ColorPalette;
import utilities.Employee;
import utilities.Styling;

/**
 *
 * @author laiba
 */
public class ChangePasswordView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel dashboardPanel;
    private JPanel centerPanel;
    
    private JTextField tfEmail;
    private JPasswordField tfCurrentPwd;
    private JPasswordField tfNewPwd;
    
    private JButton btnSubmit;
    private JButton btnBack;
    
    private boolean firstTime;
    private String empName;
    
    public ChangePasswordView(boolean firstTime, String empName){
        this.empName = empName;
        this.firstTime = firstTime;
        setTitle("Change Password");
        ImageIcon icon = new ImageIcon("src/Images/MetroLogo.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(d.width, d.height);
        setBackground(ColorPalette.LIGHT_GREY);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        init();
        setVisible(true);
    }
    
    public void init()
    {
        setLayout(new BorderLayout());
        dashboardPanel = new JPanel();
        Styling.setDashboard(this, dashboardPanel, d, "Change Password", empName);
        
        centerPanel = new JPanel(new GridLayout(0,1));
        tfEmail = new JTextField();
        tfCurrentPwd = new JPasswordField();
        tfNewPwd = new JPasswordField();
        Styling.setTextField(tfEmail);
        Styling.setPasswordField(tfCurrentPwd);
        Styling.setPasswordField(tfNewPwd);
        
        JLabel lblTitle = new JLabel("Change Password");
        JLabel lblEmail = new JLabel("Enter your email");
        JLabel lblCurrentPwd = new JLabel("Enter your current password");
        JLabel lblNewPwd = new JLabel("Enter your new password");
        Styling.setLabelMainHeading(lblTitle);
        Styling.setLabelBody(lblEmail);
        Styling.setLabelBody(lblCurrentPwd);
        Styling.setLabelBody(lblNewPwd);
        
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSubmit = new JButton("Submit");
        Styling.setButton(btnSubmit);
        btnBack = new JButton("Back");
        Styling.setButton(btnBack);
        
        if(!firstTime)
        {
            buttonPanel.add(btnBack);
        }
        buttonPanel.add(btnSubmit);
        
        centerPanel.add(lblTitle);
        centerPanel.add(lblEmail);
        centerPanel.add(tfEmail);
        centerPanel.add(lblCurrentPwd);
        centerPanel.add(tfCurrentPwd);
        centerPanel.add(lblNewPwd);
        centerPanel.add(tfNewPwd);
        
        centerPanel.add(buttonPanel);
        
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 500, 50, 500)); // t l b r
        add(dashboardPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        Styling.footer(this);
    }
    
    public JTextField getTfEmail() {
        return tfEmail;
    }

    public JPasswordField getTfCurrentPwd() {
        return tfCurrentPwd;
    }

    public JPasswordField getTfNewPwd() {
        return tfNewPwd;
    }
    
    public void setSubmitBtnListener(ActionListener al)
    {
        btnSubmit.addActionListener(al);
    }
    
    public void setBackBtnListener(ActionListener al)
    {
        btnBack.addActionListener(al);
    }

}
