/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.EmployeeModel;
import Views.ChangePasswordView;
import javax.swing.JFrame;
import utilities.Employee;
import utilities.MessageDialog;

/**
 *
 * @author laiba
 */
public class ChangePasswordController {
    
    private ChangePasswordView view;
    private Employee employee;
    private EmployeeModel empModel;
    private JFrame mainView;

    public ChangePasswordController(JFrame mainView, Employee employee, EmployeeModel empModel, boolean firstTime) {
        this.employee = employee;
        this.empModel = empModel;
        this.mainView = mainView;
        view = new ChangePasswordView(firstTime, employee.getName());
        view.setSubmitBtnListener(e -> submit());
        view.setBackBtnListener(e -> back());
    }
    
    public void submit()
    {
        String oldPwd = view.getTfCurrentPwd().getText(), newPwd = view.getTfNewPwd().getText();
        String email = view.getTfEmail().getText();
        if(oldPwd.equals(employee.getPassword()) && email.equals(employee.getEmail()))
        {
            employee.setPassword(newPwd);
            if(empModel.updateEmployee(employee))
            {
                MessageDialog.showSuccess("Password changed successfully!");
                mainView.setVisible(true);
                view.dispose();
            }
            else
            {
                MessageDialog.showFail("Could not change password!");
            }
        }
        else
        {
            MessageDialog.showFail("Incorrect current password!");
        }
    }
    
    public void back()
    {
        mainView.setVisible(true);
        view.dispose();
    }
    
}
