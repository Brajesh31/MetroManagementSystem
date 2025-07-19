/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.BillModel;
import Models.EmployeeModel;
import Models.ProductModel;
import Views.CashierDashboardView;
import utilities.Employee;

/**
 *
 * @author laiba
 */
public class CashierDashboardController {
    
    private CashierDashboardView view;
    private Employee cashier;
    private EmployeeModel empModel;
    private ProductModel prodModel;
    private BillModel billModel;

    public CashierDashboardController(Employee cashier, EmployeeModel empModel) {
        this.cashier = cashier;
        this.empModel = empModel;
        this.prodModel = new ProductModel();
        this.billModel = new BillModel();
        view = new CashierDashboardView(cashier.getName());
        view.setGenBillButtonListener(e -> generateBill());
        view.setChangePwdButtonListener(e -> changePwd());
        
        if(cashier.getPassword().equals("123456"))
        {
           new ChangePasswordController(view, cashier, empModel, true);
            view.setVisible(false);
        }
    }
    
    public void changePwd()
    {
        new ChangePasswordController(view, cashier, empModel, false);
        view.setVisible(false);
    }
    
    
    public void generateBill()
    {
            new GenerateBillController(view, prodModel, billModel, cashier);
            view.setVisible(false);
    }
    
    
    
    
}
