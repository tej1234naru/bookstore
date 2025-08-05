import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Store the username and hashed password in the database
        storeUserInDatabase(username, hashedPassword);

        // Redirect to a success page
        response.sendRedirect("success.html");
    }

    // Hashing function (use a proper library like BCrypt)
    private String hashPassword(String password) {
        // Implement your hashing algorithm here
        return password; // For demonstration, returning plain password
    }

    // Store user in the database
    private void storeUserInDatabase(String username, String hashedPassword) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

            // Create a PreparedStatement
            pstmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
