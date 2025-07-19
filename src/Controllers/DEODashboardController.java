
package Controllers;

import Models.EmployeeModel;
import Models.ProductModel;
import Models.VendorModel;
import Views.DEODashboardView;
import utilities.Employee;
import utilities.InputValidation;
import utilities.MessageDialog;
import utilities.Product;
import utilities.Vendor;

import javax.swing.*;
import java.awt.*;

public class DEODashboardController {
    private final VendorModel vendorModel;
    private final ProductModel prodModel;
    private final DEODashboardView view;

    public DEODashboardController(Employee deo, EmployeeModel empModel) {
        this.prodModel = new ProductModel();
        this.vendorModel = new VendorModel();
        this.view = new DEODashboardView(vendorModel,deo);
        if(deo.getPassword().equals("123456")){
            new ChangePasswordController(view,deo,empModel,true);
            view.setVisible(false);
        }

        initializeListeners();
        populateVendorComboBox();
    }

    private void initializeListeners() {
        // Navigation button listeners to switch panels
        view.getVendorButton().addActionListener(e -> {
            view.showPanel("VendorPanel");
        });

        view.getProductButton().addActionListener(e -> {
            view.showPanel("ProductPanel");
        });

        // Add vendor button listener
        view.getAddVendorButton().addActionListener(e -> addVendor());

        // Save product button listener
        view.getSaveProductButton().addActionListener(e -> addProduct());
    }

    private void showVendorPanel() {
        CardLayout cl = (CardLayout) view.getContentPanel().getLayout();
        cl.show(view.getContentPanel(), "VendorPanel");
    }

    private void showProductPanel() {
        CardLayout cl = (CardLayout) view.getContentPanel().getLayout();
        cl.show(view.getContentPanel(), "ProductPanel");
    }

    private void populateVendorComboBox() {
        // Fetch vendor names from the model and populate the combo box
        String[] vendorNames = VendorModel.getVendorNames();
        JComboBox<String> vendorComboBox = view.getVendorComboBox();
        for (String vendorName : vendorNames) {
            vendorComboBox.addItem(vendorName);
        }
    }

    public void addVendor() {
        String name = view.getVendorNameField().getText();
        String cnic = view.getVendorCnicField().getText();
        String phoneNumber = view.getVendorPhoneField().getText();

        if (InputValidation.validateCNIC(cnic) && InputValidation.validatePhone(phoneNumber)) {
            Vendor vendor = new Vendor(0, name, cnic, phoneNumber);
            if (vendorModel.addVendor(vendor)) {
                MessageDialog.showSuccess("Vendor added successfully!");
                view.getVendorComboBox().addItem(name);
                view.loadVendorData();

                // Update vendor combo box
            } else {
                MessageDialog.showFail("Could not add vendor!");
            }
        } else {
            MessageDialog.showFail("Invalid CNIC or phone number!");
        }
    }

    public void addProduct() {
        try {
            String productName = view.getProductNameField().getText();
            String productCategory = view.getProductCategoryField();
            Product product = getProduct(productName, productCategory);

            if (prodModel.addProduct(product)) { // Pass the selected vendor name
                MessageDialog.showSuccess("Product added successfully!");
                view.loadProductData();
            } else {
                MessageDialog.showFail("Could not add product!");
            }
        } catch (NumberFormatException ex) {
            MessageDialog.showFail("Please enter valid numerical values for prices!");
        }
    }

    private Product getProduct(String productName, String productCategory) {
        double originalPrice = Double.parseDouble(view.getProductOriginalPriceField().getText());
        double salePrice = Double.parseDouble(view.getProductSalePriceField().getText());
        double cartonPrice = Double.parseDouble(view.getProductCartonPriceField().getText());

        // Get selected vendor ID based on combo box selection
        int vendorId = vendorModel.getVendorIdByName((String) view.getVendorComboBox().getSelectedItem());
        System.out.println(vendorId);

        Product product = new Product(0, vendorId, productName, productCategory,
                originalPrice, salePrice, cartonPrice);
        return product;
    }
}
