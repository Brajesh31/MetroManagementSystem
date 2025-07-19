/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author laiba
 */
public class Branch {
    private String branchCode;
    private String city;
    private String address;
    private String phone;
    private int nEmployees;

    public Branch(String branchCode, String city, String address, String phone, int nEmployees) {
        this.branchCode = branchCode;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.nEmployees = nEmployees;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNEmployees() {
        return nEmployees;
    }

    public void setNEmployees(int nEmployees) {
        this.nEmployees = nEmployees;
    }
    
    
}
