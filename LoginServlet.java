import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Received username: " + username);
        System.out.println("Received password: " + password);

        try {
        	Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Teja1308");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM userdetails WHERE username=? AND password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Successful login
                System.out.println("Login successful");
                response.sendRedirect("dash.html");
                
            } else {
                // Failed login
                System.out.println("Login failed");
                
                response.sendRedirect("signup.html");
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            response.sendRedirect("login.html"); // Redirect to login page if login fails
        }
    }
}
