package Models;
import java.sql.*;
import java.util.Map;
import utilities.*;

public class ProductBranchModel {
    private final Connection conn = DBConnection.getInstance().getConnection();

    public boolean createTable() {
        String query = "CREATE TABLE IF NOT EXISTS product_branch (" +
                "product_id INT, " +
                "branch_code VARCHAR(50), " +
                "quantity INT, " +
                "PRIMARY KEY (product_id, branch_code))";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addProductBranch(ProductBranch pb) {
        String query = "INSERT INTO product_branch (product_id, branch_code, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pb.getProductId());
            stmt.setString(2, pb.getBranchCode());
            stmt.setInt(3, pb.getQuantity());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProductBranch(ProductBranch pb) {
        String query = "DELETE FROM product_branch WHERE product_id = ? AND branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pb.getProductId());
            stmt.setString(2, pb.getBranchCode());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductBranch(ProductBranch pb) {
        String query = "UPDATE product_branch SET quantity = ? WHERE product_id = ? AND branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pb.getQuantity());
            stmt.setInt(2, pb.getProductId());
            stmt.setString(3, pb.getBranchCode());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean searchProductBranch(ProductBranch pb) {
        String query = "SELECT * FROM product_branch WHERE product_id = ? AND branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pb.getProductId());
            stmt.setString(2, pb.getBranchCode());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("ProductBranch Found: Quantity = " + rs.getInt("quantity"));
                return true;
            } else {
                System.out.println("ProductBranch not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getProductBranchQty(int id, String branch)
    {
        String query = "SELECT * FROM product_branch WHERE product_id = ? AND branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, branch);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            } else {
                System.out.println("ProductBranch not found.");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public void updateProductQuantities(Bill bill)
    {
        Map<Product, Integer> products_qty = bill.getProducts_qty();
    
        String query = "UPDATE product_branch SET quantity = quantity - ? WHERE product_id = ? AND branch_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Map.Entry<Product, Integer> entry : products_qty.entrySet()) {
                Product product = entry.getKey();
                int quantitySold = entry.getValue();

                stmt.setInt(1, quantitySold);
                stmt.setInt(2, product.getProductId());
                stmt.setString(3, bill.getBranchCode()); 

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Warning: Product " + product.getProductId() + " in branch " + bill.getBranchCode() + " was not found.");
                }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
