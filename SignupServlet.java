import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 String username = request.getParameter("username");
         String pass1 = request.getParameter("pass1"); // Changed to pass1
         String pass2 = request.getParameter("pass2"); // Changed to pass2
         String email = request.getParameter("email");

         System.out.println("Received username: " + username);
         System.out.println("Received pass1: " + pass1);
         System.out.println("Received pass2: " + pass2);
         System.out.println("Received email: " + email);
           try {
        	   if (!pass1.equals(pass2)) {
                   throw new IllegalArgumentException("Passwords do not match");
               }

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","Teja1308");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO userdetails (username, password, email) VALUES (?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, pass1);
            pstmt.setString(3, email);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");
            //conn.commit();
            conn.close();
            System.out.println("Database connection closed");
            response.sendRedirect("dash.html");
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("signup.html"); // Redirect to signup page if signup fails
        }
    }
}
   