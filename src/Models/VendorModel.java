package Models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utilities.*;

public class VendorModel {
    private static final Connection conn = DBConnection.getInstance().getConnection();

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS vendor (" +
                "vendor_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100), " +
                "cnic CHAR(13), " +
                "phone_number CHAR(11))";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addVendor(Vendor vendor) {
        // Check if vendor with the same CNIC already exists
        String checkQuery = "SELECT COUNT(*) FROM vendor WHERE cnic = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, vendor.getCnic());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // CNIC already exists in the database, show error message
               MessageDialog.showFail("Vendor with this CNIC Already Exists");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Proceed to insert the new vendor if CNIC is unique
        String insertQuery = "INSERT INTO vendor (name, cnic, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getCnic());
            pstmt.setString(3, vendor.getPhoneNumber());
            return pstmt.executeUpdate() > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteVendor(int vendorId) {
        String query = "DELETE FROM vendor WHERE vendor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, vendorId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateVendor(Vendor vendor) {
        // First, check if the CNIC already exists in the database for another vendor
        String checkQuery = "SELECT COUNT(*) FROM vendor WHERE cnic = ? AND vendor_id != ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, vendor.getCnic());
            checkStmt.setInt(2, vendor.getVendorId()); // Ensure it's not checking for the current vendor

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // If another vendor with the same CNIC exists, prevent the update
                MessageDialog.showFail("A vendor with this CNIC already exists");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // If CNIC check passes, proceed with the update
        String updateQuery = "UPDATE vendor SET name = ?, cnic = ?, phone_number = ? WHERE vendor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getCnic());
            pstmt.setString(3, vendor.getPhoneNumber());
            pstmt.setInt(4, vendor.getVendorId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public Vendor getVendor(int vendorId) {
        String query = "SELECT * FROM vendor WHERE vendor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, vendorId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Vendor(
                        rs.getInt("vendor_id"),
                        rs.getString("name"),
                        rs.getString("cnic"),
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Vendor not found
    }


    public List<Vendor> searchVendor(String name) {
        ArrayList<Vendor> vendorList = new ArrayList<>();
        String query = "SELECT * FROM vendor WHERE name LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vendorList.add(new Vendor(
                        rs.getInt("vendor_id"),
                        rs.getString("name"),
                        rs.getString("cnic"),
                        rs.getString("phone_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendorList;
    }

    // New Method: Fetch Vendor Names
    public static String[] getVendorNames() {
        List<String> vendorNames = new ArrayList<>();
        String query = "SELECT name FROM vendor";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vendorNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendorNames.toArray(new String[0]);
    }

    public List<Vendor> getAllVendors() {
        List<Vendor> vendorList = new ArrayList<>();
        String query = "SELECT * FROM vendor";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Process each record and create Vendor objects
            while (rs.next()) {
                Vendor vendor = new Vendor(
                        rs.getInt("vendor_id"),
                        rs.getString("name"),
                        rs.getString("cnic"),
                        rs.getString("phone_number")
                );
                vendorList.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendorList;
    }



    public int getVendorIdByName(String vendorName) {
        String query = "SELECT vendor_id FROM vendor WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendorName);  // Use the vendor name to fetch vendor_id
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("vendor_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if vendor not found
    }
}


