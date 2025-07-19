package Controllers;

import Models.EmployeeModel;
import Models.ReportModel;
import Views.BranchMgrView;
import Views.ViewEmployeesView;
import utilities.Employee;
import utilities.InputValidation;
import utilities.MessageDialog;

public class BranchMgrDashboardController {
    private Employee branchMgr;
    private EmployeeModel empModel;
    private ReportModel reportModel;
    private BranchMgrView view;

    public BranchMgrDashboardController(Employee branchMgr, EmployeeModel empModel, ReportModel reportModel) {
        this.branchMgr = branchMgr;
        this.empModel = empModel;
        this.view = new BranchMgrView(branchMgr);
        this.reportModel = reportModel;
        if(branchMgr.getPassword().equals("123456"))
        {
            new ChangePasswordController(view, branchMgr, empModel, true);
        }
        addListeners();
    }

    public void addListeners(){
        view.getAddCashier().addActionListener(e -> {
            new AddEmployeeController(branchMgr, empModel,reportModel, 'C');
            view.dispose();
        });

        view.getAddDeo().addActionListener(e -> {
            new AddEmployeeController(branchMgr, empModel, reportModel,'D');
            view.dispose();
        });

        view.getViewCashier().addActionListener(e -> {
            new ViewEmployeesView(branchMgr, empModel, reportModel,'C');
            view.dispose();
        });

        view.getViewDeo().addActionListener(e -> {
            new ViewEmployeesView(branchMgr, empModel,reportModel, 'D');
            view.dispose();
        });

        view.getChangePwd().addActionListener(e -> {
            new ChangePasswordController(view, branchMgr, empModel, false);
        });
        
                view.getGenerateReport().addActionListener(e -> {
            new GenerateReportController(branchMgr, empModel);
        });

    }
    
    public void changePwd()
    {
        new ChangePasswordController(view, branchMgr, empModel, true);
    }
    
    public void addCashier()
    {
        String name = "", email = "", password = "123456", branchcode = "", emptype = "C";
        double salary = 0;
        
        if(InputValidation.validateEmail(email) && InputValidation.validateSalary(salary))
        {
            Employee cashier = new Employee(0, name, email, password, branchcode, salary, emptype);
            if(empModel.addEmployee(cashier))
            {
                MessageDialog.showSuccess("Cashier created successfully!");
            }
            else
            {
                MessageDialog.showFail("Could not create Cashier!");
            }
        }
        
    }
    
    public void addDEO()
    {
        String name = "", email = "", password = "123456", branchcode = "", emptype = "D";
        double salary = 0;
        
        if(InputValidation.validateEmail(email) && InputValidation.validateSalary(salary))
        {
            Employee deo = new Employee(0, name, email, password, branchcode, salary, emptype);
            if(empModel.addEmployee(deo))
            {
                MessageDialog.showSuccess("Data Entry Operator created successfully!");
            }
            else
            {
                MessageDialog.showFail("Could not create Data Entry Operator!");
            }
        }
    }
    
    
    
    
    
}
