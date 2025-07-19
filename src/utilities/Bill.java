/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author laiba
 */
public class Bill {
    private int billId;
    private String branchCode;
    private LocalDate date;
    private Map<Product, Integer> products_qty;
    private boolean cash;
    private double totalAmount;
    private double tax;
    private double netAmount;

    public Bill(int billId, String branchCode, LocalDate date, Map<Product, Integer> products_qty, boolean cash, double totalAmount, double tax, double netAmount) {
        this.billId = billId;
        this.branchCode = branchCode;
        this.date = date;
        this.products_qty = products_qty;
        this.cash = cash;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.netAmount = netAmount;
    }
    
    public Bill(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<Product, Integer> getProducts_qty() {
        return products_qty;
    }

    public void setProducts_qty(Map<Product, Integer> products_qty) {
        this.products_qty = products_qty;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    
    
    
}
