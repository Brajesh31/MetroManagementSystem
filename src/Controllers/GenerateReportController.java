package Controllers;

import Models.EmployeeModel;
import Models.ReportModel;
import Views.GenerateReportView;
import utilities.Employee;
import utilities.Report;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateReportController {
    private GenerateReportView view;
    private Employee emp;
    private EmployeeModel empModel;

    public GenerateReportController(Employee employee, EmployeeModel empModel) {
        this.empModel = empModel;
        this.emp = employee;
        this.view = new GenerateReportView();

        addListeners();
    }

    public void addListeners(){
        view.getGenerateButton().addActionListener(e -> {
            String startDate = view.getStartDateField().getText();
            String rangeTag = view.getRangeTagField().getSelectedItem().toString();
            generateReport(startDate, rangeTag);
        });
    }

    public void generateReport(String startDate, String rangeTag) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end;
            switch (rangeTag.toUpperCase()) {
                case "DAILY":
                    end = start.plusDays(1);
                    break;
                case "WEEKLY":
                    end = start.plusWeeks(1);
                    break;
                case "MONTHLY":
                    end = start.plusMonths(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid range tag: " + rangeTag);
            }

            double sales = empModel.getSales(start, end);
            String  remaining = empModel.getRemainingStock(emp.getBranchCode(), start, end);
            System.out.println(remaining);
            double profit = 0.0;

            Report newReport = new Report(0, emp.getBranchCode(), rangeTag, start, end, sales, remaining, profit);
            ReportModel repModel= new ReportModel();
            repModel.addReport(newReport);

        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }


}
