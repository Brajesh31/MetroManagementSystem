/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utilities.ColorPalette;
import utilities.Employee;
import utilities.RoundedButton;
import utilities.Styling;

/**
 *
 * @author laiba
 */
public class CashierDashboardView extends JFrame {
    private final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel dashboardPanel;
    private JPanel centerPanel;
    private JButton btnChangePwd;
    private JButton btngenBill;
    private String empName;
    
    public CashierDashboardView(String empName){
        this.empName = empName;
        setTitle("Cashier Dashboard");
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
        
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        btnChangePwd = setButtons(centerPanel, "Change Password", "src/Images/change_pwd_small.png", 100, 80);
        btngenBill = setButtons(centerPanel, "Generate Bill", "src/Images/add_bill_small.png", 100, 80);
        Styling.setDashboard(this, dashboardPanel, d, "Cashier Dashboard", empName);
        
        add(dashboardPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        Styling.footer(this);
    }
    
    public JButton setButtons(JPanel optionsPanel, String text, String filePath, int width, int height){
       RoundedButton btn = new RoundedButton(text, 30);
       btn.setFont(Styling.headingFont);
       btn.setForeground(Color.WHITE);


       ImageIcon branchIcon = new ImageIcon(filePath);
       btn.setIcon(branchIcon);
       
       btn.setHorizontalTextPosition(SwingConstants.CENTER);
       btn.setVerticalTextPosition(SwingConstants.BOTTOM);
       Styling.setBigButton(btn); 


       optionsPanel.add(btn);
       return btn;
   }
    
    // for controller
    
    public void setChangePwdButtonListener(ActionListener al)
    {
        btnChangePwd.addActionListener(al);
    }
    
    public void setGenBillButtonListener(ActionListener al)
    {
        btngenBill.addActionListener(al);
    }

}
