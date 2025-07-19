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
public class InputValidation {
    
    public static boolean validateBranchCode(String bCode)
    {
        String regex = "^\\d{3}[A-Z]{3}$";
        if(bCode.matches(regex))
        {
            return true;
        }
        else
        {
            MessageDialog.showFail("Enter branch code in the following format:\n000AAA (0: any digit, A: any alphabet)");
            return false;
        }
    }
    
    public static boolean validatePhone(String phoneNum)
    {
        String regex = "^\\d{11}$";
        if(phoneNum.matches(regex))
        {
            return true;
        }
        else
        {
            MessageDialog.showFail("Phone number must contain 11 digits");
            return false;
        }
    }
    
    public static boolean validateEmail(String email)
    {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(email.matches(regex))
        {
            return true;
        }
        else
        {
            MessageDialog.showFail("Incorrect email!");
            return false;
        }
    }
    
    public static boolean validateSalary(Double salary)
    {
        return (salary > 0);
    }
    
    public static boolean validateCNIC(String cnic)
    {
        String regex = "^\\d{13}$";
        if(cnic.matches(regex))
        {
            return true;
        }
        else
        {
            MessageDialog.showFail("CNIC must contain 13 digits!");
            return false;
        }
    }
    
}
