package Controllers;

import Models.BranchModel;
import Models.EmployeeModel;
import Models.ReportModel;
import Views.AddEmployeeView;
import utilities.Employee;
import utilities.InputValidation;
import utilities.MessageDialog;

import java.util.List;

public class AddEmployeeController {
    private AddEmployeeView addEmployeeView;
    private EmployeeModel empModel;
    private ReportModel reportModel;
    private char empType;
    private Employee emp;

    public AddEmployeeController(Employee emp, EmployeeModel empModel, ReportModel reportModel, char empType) {
        this.emp = emp;
        this.empModel = empModel;
        this.reportModel = reportModel;
        this.empType = empType;

        List<String> branches = empType == 'B' ? BranchModel.getBranchesWithoutManager() : BranchModel.getBranchCodes();
        this.addEmployeeView = new AddEmployeeView(emp, empModel, empType, branches); // Pass filtered branches

        addListeners();
    }

    private void addListeners() {
        addEmployeeView.getSaveButton().addActionListener(e -> {
            createEmployee(); // Method to create a new employee
            clearInputFields(); // Clear the input fields after saving
        });
        addEmployeeView.getCancelButton().addActionListener(e -> {
            if (emp == null) {
                new SuperAdminDashboardController(empModel,reportModel);
                addEmployeeView.dispose();
            } else {
                new BranchMgrDashboardController(emp, empModel,reportModel);
                addEmployeeView.dispose();
            }
        });
    }

    private void createEmployee() {
        try {
            String name = addEmployeeView.getNameField().getText();
            String email = addEmployeeView.getEmail().getText();
            String password = "123456"; // Default password
            if(emp == null){
                branchCode = addEmployeeView.getSelectedBranchCode();
            }
            else{
                branchCode = emp.getBranchCode();
            }
            String empTypeString = String.valueOf(empType); // Convert char to String
            double salary = Double.parseDouble(addEmployeeView.getSalary().getText());

            if (InputValidation.validateEmail(email)) {
                Employee newEmployee = new Employee(0, name, email, password, branchCode, salary, empTypeString);

                if (empModel.addEmployee(newEmployee)) {
                    MessageDialog.showSuccess(getSuccessMessage());
                    if(emp == null){
                        BranchModel.incrementEmployees(branchCode);
                        new SuperAdminDashboardController(empModel,reportModel);
                        addEmployeeView.dispose();
                    }
                    else{
                        BranchModel.incrementEmployees(branchCode);
                        new BranchMgrDashboardController(emp, empModel,reportModel);
                        addEmployeeView.dispose();
                    }
                } else {
                    MessageDialog.showFail("Could not create employee!");
                }
            } else {
                MessageDialog.showFail("Invalid input. Please check the name and email.");
            }
        } catch (NumberFormatException ex) {
            MessageDialog.showFail("Invalid salary. Please enter a valid number.");
        } catch (Exception ex) {
            MessageDialog.showFail("An error occurred: " + ex.getMessage());
        }
    }

    private void clearInputFields() {
        addEmployeeView.getNameField().setText("");
        addEmployeeView.getEmail().setText("");
        addEmployeeView.getSalary().setText("");
    }

    private String getSuccessMessage() {
        return switch (empType) {
            case 'C' -> "Cashier created successfully!";
            case 'D' -> "Data Entry Operator created successfully!";
            default -> "Branch Manager created successfully!";
        };
    }
}
