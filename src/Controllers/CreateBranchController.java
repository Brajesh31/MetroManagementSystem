package Controllers;

import Models.BranchModel;
import Models.EmployeeModel;
import Models.ReportModel;
import Views.CreateBranchView;
import utilities.Branch;
import utilities.InputValidation;
import utilities.MessageDialog;

import java.util.List;

public class CreateBranchController {
    private CreateBranchView createBranchView;
    private EmployeeModel empModel;
    private BranchModel branchModel;
    private ReportModel reportModel;


    public CreateBranchController(EmployeeModel empModel, BranchModel bModel, ReportModel reportModel){
        this.empModel = empModel;
        this.branchModel = bModel;
        this.createBranchView = new CreateBranchView(empModel,reportModel);
        this.reportModel = reportModel;

        addListeners();

    }
    private void addListeners() {
        createBranchView.getSaveButton().addActionListener(e -> createBranch());
    }
    public void createBranch() {
        String city = createBranchView.getSelectedCity();
        String address = createBranchView.getAddress();
        String phone = createBranchView.getPhone();

        // Generate branch code
        String branchCode = generateBranchCode(city);

        if (branchCode == null) {
            MessageDialog.showFail("Could not generate branch code.");
            return;
        }

        if (InputValidation.validatePhone(phone)) {
            Branch branch = new Branch(branchCode, city, address, phone, 0);
            if (branchModel.addBranch(branch)) {
                MessageDialog.showSuccess("Branch created successfully with code: " + branchCode);
                new SuperAdminDashboardController(empModel,reportModel);
                createBranchView.dispose();
            } else {
                MessageDialog.showFail("Could not create branch!");
            }
        } else {
            MessageDialog.showFail("Invalid phone format.");
        }
    }

    // Method to generate branch code based on existing branches
    private String generateBranchCode(String city) {
        String cityCode = getCityCode(city);
        if (cityCode == null) return null;

        List<String> existingCodes = BranchModel.getBranchCodes();

        // Find the maximum branch number for the selected city
        int maxNumber = 0;
        for (String code : existingCodes) {
            if (code.startsWith(cityCode)) {
                try {
                    int number = Integer.parseInt(code.substring(3));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // Increment the branch number by 1 and format to 3 digits
        String branchNumber = String.format("%03d", maxNumber + 1);
        return cityCode + branchNumber;
    }

    // Helper method to get city code
    private String getCityCode(String city) {
        return switch (city) {
            case "Karachi" -> "KHI";
            case "Lahore" -> "LHR";
            case "Islamabad" -> "ISB";
            case "Rawalpindi" -> "RWP";
            case "Peshawar" -> "PEW";
            case "Quetta" -> "UET";
            case "Faisalabad" -> "FSD";
            case "Multan" -> "MUX";
            case "Sialkot" -> "SKT";
            case "Hyderabad" -> "HYD";
            default -> null;
        };
    }


}
