package Controllers;

import Models.BillModel;
import Models.ProductBranchModel;
import Models.ProductModel;
import Views.GenerateBillView;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utilities.Bill;
import utilities.Employee;
import utilities.GlobalValues;
import utilities.MessageDialog;
import utilities.Product;

public class GenerateBillController {
    private Bill bill; 
    private ProductModel prodModel;
    private BillModel billModel;
    private ProductBranchModel pbmodel;
    private Map<Product, Integer> products_qty;
    private GenerateBillView view;
    private ArrayList<Product> products;
    private JFrame mainView;
    private Employee emp;

    public GenerateBillController(JFrame mainView, ProductModel prodModel, BillModel billModel, Employee emp) 
    {
        bill = new Bill(emp.getBranchCode());
        products_qty = new HashMap<>();
        this.billModel = billModel;
        this.prodModel = prodModel;
        this.emp = emp;
        this.mainView = mainView;
        products = (ArrayList<Product>) prodModel.getAllProducts();
        filterProducts();
        view = new GenerateBillView(products, emp.getName());
        addProductToBill();
        view.setConfirmBtnListener(e -> confirmBill());
        view.setBackBtnActionListener(e -> back());
    }
    
    public void filterProducts()
    {
        pbmodel = new ProductBranchModel();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (pbmodel.getProductBranchQty(p.getProductId(), emp.getBranchCode()) == 0) {
                iterator.remove();
            }
        }
    }
    
    public void addProductToBill()
    {
        HashMap<Product, JButton> productCards = view.getProductCards();

        for (Product product : productCards.keySet())
        {
            JButton addBtn = productCards.get(product);
            
            addBtn.addActionListener(e -> {
                int currentQty = products_qty.getOrDefault(product, 0);
                
                products_qty.put(product, currentQty + 1); // update map for bill
                view.addProductToBill(product, currentQty + 1); // update view
                
                System.out.println("Added unit of: " + product.getName() + ". New quantity: " + products_qty.get(product));
            });

        }
    }
    
    public void confirmBill()
    {
        bill.setProducts_qty(products_qty);
        bill.setDate(LocalDate.now());
        
        boolean cash = view.getCashCheckBox().isSelected();
        double totalAmount = calculateTotalAmount();
        double tax = calculateTax(totalAmount);
        double netAmount = totalAmount + tax;
        
        bill.setTotalAmount(totalAmount);
        bill.setTax(tax);
        bill.setNetAmount(netAmount);
        bill.setCash(cash);
        
        MessageDialog.showBillAndConfirmation(bill, this);
    }
    
    public void back()
    {
        mainView.setVisible(true);
        view.dispose();
    }
    
    public void addBill()
    {
        billModel.addBill(bill);
        pbmodel.updateProductQuantities(bill);
        reinitializeBill();
    }
    
    public void reinitializeBill()
    {
        bill = new Bill(emp.getBranchCode());
        products_qty.clear();
        view.clearBill();
    }
    
    public double calculateTotalAmount()
    {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : bill.getProducts_qty().entrySet()) 
        {
            int qty = entry.getValue();
            double price = entry.getKey().getSalePrice();
            total += (price * qty);
        }
        
        return total;
    }
    
    public double calculateTax(double totalAmount)
    {
        return totalAmount * GlobalValues.getTax();
    }
    
    
}
