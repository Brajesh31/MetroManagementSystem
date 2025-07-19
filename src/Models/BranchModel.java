package Models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utilities.*;

public class BranchModel {

    private static final Connection conn = DBConnection.getInstance().getConnection();

    public boolean createTable() {
        String query = "CREATE TABLE IF NOT EXISTS branch (" +
                "branch_code VARCHAR(6) PRIMARY KEY, " +
                "city VARCHAR(55), " +
                "address VARCHAR(100), " +
                "phone VARCHAR(11), " +
                "n_employees INT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addBranch(Branch branch) {
        String query = "INSERT INTO branch (branch_code, city, address, phone, n_employees) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branch.getBranchCode());
            stmt.setString(2, branch.getCity());
            stmt.setString(3, branch.getAddress());
            stmt.setString(4, branch.getPhone());
            stmt.setInt(5, branch.getNEmployees());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBranch(Branch branch) {
        String query = "DELETE FROM branch WHERE branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branch.getBranchCode());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBranch(Branch branch) {
        String query = "UPDATE branch SET city=?, address=?, phone=?, n_employees=? WHERE branch_code=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branch.getCity());
            stmt.setString(2, branch.getAddress());
            stmt.setString(3, branch.getPhone());
            stmt.setInt(4, branch.getNEmployees());
            stmt.setString(5, branch.getBranchCode());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean searchBranch(Branch branch) {
        String query = "SELECT * FROM branch WHERE branch_code=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branch.getBranchCode());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Branch Found: " + rs.getString("city"));
                return true;
            } else {
                System.out.println("Branch not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getBranchCodes() {
        List<String> branchCodes = new ArrayList<>();
        String query = "SELECT branch_code FROM branch";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branchCodes.add(rs.getString("branch_code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return branchCodes;
    }

    public static List<String> getBranchesWithoutManager() {
        List<String> branches = new ArrayList<>();
        String query = "SELECT branch_code FROM branch WHERE branch_code NOT IN (SELECT branch_code FROM employees WHERE emp_type = 'B')";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                branches.add(rs.getString("branch_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

}
