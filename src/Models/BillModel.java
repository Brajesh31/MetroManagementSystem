package Models;

import utilities.*;
import java.sql.*;
import java.util.Map;

public class BillModel {
    private final Connection conn = DBConnection.getInstance().getConnection();

    /**
     * Creates both bill and bill_product tables.
     */
    public boolean createTable() {
        String billTableQuery = "CREATE TABLE IF NOT EXISTS bill (" +
                "bill_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "branch_code VARCHAR(6)" +
                "date DATE, " +
                "cash TINYINT(1), " +
                "total_amount DOUBLE, " +
                "tax DOUBLE, " +
                "net_amount DOUBLE,"
                + "FOREIGN KEY (branch_code) REFERENCES branch(branch_code) ON DELETE SET NULL ON UPDATE CASCADE)";

        String billProductTableQuery = "CREATE TABLE IF NOT EXISTS bill_product (" +
                "bill_id INT, " +
                "product_id INT, " +
                "quantity INT, " +
                "PRIMARY KEY (bill_id, product_id), " +
                "FOREIGN KEY (bill_id) REFERENCES bill(bill_id) ON DELETE CASCADE)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(billTableQuery);
            stmt.execute(billProductTableQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new bill and its corresponding products to the database.
     */
    public boolean addBill(Bill bill) {
        String billQuery = "INSERT INTO bill (branch_code, date, cash, total_amount, tax, net_amount) VALUES (?, ?, ?, ?, ?, ?)";
        String billProductQuery = "INSERT INTO bill_product (bill_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            // Disable auto-commit for transaction management
            conn.setAutoCommit(false);

            // Insert into bill table
            int generatedBillId;
            try (PreparedStatement billStmt = conn.prepareStatement(billQuery, Statement.RETURN_GENERATED_KEYS)) {
                billStmt.setString(1, bill.getBranchCode());
                billStmt.setString(2, bill.getDate().toString());
                billStmt.setBoolean(3, bill.isCash());
                billStmt.setDouble(4, bill.getTotalAmount());
                billStmt.setDouble(5, bill.getTax());
                billStmt.setDouble(6, bill.getNetAmount());
                billStmt.executeUpdate();

                // Retrieve the generated bill ID
                ResultSet rs = billStmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedBillId = rs.getInt(1);
                } else {
                    conn.rollback();
                    throw new SQLException("Failed to generate bill ID.");
                }
            }

            // Insert into bill_product table
            try (PreparedStatement billProductStmt = conn.prepareStatement(billProductQuery)) {
                for (Map.Entry<Product, Integer> entry : bill.getProducts_qty().entrySet()) {
                    billProductStmt.setInt(1, generatedBillId);
                    billProductStmt.setInt(2, entry.getKey().getProductId());
                    billProductStmt.setInt(3, entry.getValue());
                    billProductStmt.addBatch();
                }
                billProductStmt.executeBatch();
            }

            // Commit the transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Deletes a bill and its corresponding entries in the bill_product table.
     */
    public boolean deleteBill(Bill bill) {
        String query = "DELETE FROM bill WHERE bill_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bill.getBillId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a bill in the bill table. Does not modify bill_product entries.
     */
    public boolean updateBill(Bill bill) {
        String query = "UPDATE bill SET date = ?, cash = ?, total_amount = ?, tax = ?, net_amount = ? WHERE bill_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bill.getDate().toString());
            stmt.setBoolean(2, bill.isCash());
            stmt.setDouble(3, bill.getTotalAmount());
            stmt.setDouble(4, bill.getTax());
            stmt.setDouble(5, bill.getNetAmount());
            stmt.setInt(6, bill.getBillId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Searches for a bill in the database by bill ID.
     */
    public boolean searchBill(Bill bill) {
        String query = "SELECT * FROM bill WHERE bill_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bill.getBillId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Bill Found: ID = " + rs.getInt("bill_id"));
                return true;
            } else {
                System.out.println("Bill not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
