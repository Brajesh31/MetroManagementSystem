package Models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperAdmin {
    private final Connection conn = DBConnection.getInstance().getConnection();

    public void seedAdmin(String username, String password) {
        String query = "INSERT INTO super_admin (username, password) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Super admin seeded successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error seeding super admin: " + e.getMessage());
        }
    }

    public boolean adminLogin(String usernameInput, String pwdInput)
    {
        String query = "SELECT * FROM super_admin WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usernameInput);
            pstmt.setString(2, pwdInput);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
