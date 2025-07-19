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
public class Employee {
    private String name;
    private int empNo;
    private String email;
    private String password;
    private String branchCode;
    private double salary;
    private String e_type;

    public Employee(int empNo, String name, String email, String password, String branchCode, double salary, String e_type) {
        this.empNo = empNo;
        this.name = name;
        this.email = email;
        this.password = password;
        this.branchCode = branchCode;
        this.salary = salary;
        this.e_type = e_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmpType() {
        return e_type;
    }

    public void setEmpType(String e_type) {
        this.e_type = e_type;
    }
    
    
}
