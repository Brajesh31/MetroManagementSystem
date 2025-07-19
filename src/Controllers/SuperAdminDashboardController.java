package Controllers;

import Models.BranchModel;
import Models.EmployeeModel;
import Models.ReportModel;
import Views.SuperAdminView;
import Views.ViewEmployeesView;
import Views.ViewBranchesView; // Import the new ViewBranchesView
import Views.ViewReportsView;

public class SuperAdminDashboardController {
    private BranchModel branchModel;
    private EmployeeModel empModel;
    private SuperAdminView spaView;
    private ReportModel reportModel;

    public SuperAdminDashboardController(EmployeeModel empModel, ReportModel reportModel) {
        this.reportModel = reportModel;
        this.branchModel = new BranchModel();
        this.empModel = empModel;

        this.spaView = new SuperAdminView();
        addListeners();
    }

    private void addListeners() {
        spaView.getCreateBranchBtn().addActionListener(e -> {
            new CreateBranchController(empModel, branchModel,reportModel);
            spaView.dispose();
        });

        spaView.getAddBMBtn().addActionListener(e -> {
            new AddEmployeeController(null, empModel, reportModel,'B');
            spaView.dispose();
        });

        spaView.getViewBMBtn().addActionListener(e -> {
            new ViewEmployeesView(null, empModel, reportModel,'B');
            spaView.dispose();
        });

        // New action listener for the View Branches button
        spaView.getViewBranchesBtn().addActionListener(e -> {
            new ViewBranchesView(branchModel); // Call the view directly
            spaView.dispose();
        });

        spaView.getViewReportsBtn().addActionListener(e -> {

            new ViewReportsView(reportModel);
            spaView.dispose();

        });
    }

    public void seeReports() {
        // Show reports screen
    }
}
