import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create a connection to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/egrocery", "root", "2003");

            // Retrieve user input from request
            String name = req.getParameter("name");  // Assuming you are collecting name
            String email = req.getParameter("email");
            String pass = req.getParameter("password");
            String phone = req.getParameter("phone"); // Assuming phone is optional

            // Prepare SQL statement to insert user data into the login table
            String query = "INSERT INTO users (name,email, password,phone) VALUES (?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4,phone);

            // Execute update
            int result = ps.executeUpdate(); 
            if (result > 0) {
            	   Cookie emailCookie = new Cookie("emailCookie", email);
            	    Cookie passwordCookie = new Cookie("passwordCookie", pass);

            	    // Set expiry time for cookies (e.g., 5 minutes)
            	    emailCookie.setMaxAge(5 * 60);
            	    passwordCookie.setMaxAge(5 * 60);

            	    // Add cookies to response
            	    res.addCookie(emailCookie);
            	    res.addCookie(passwordCookie);
                res.sendRedirect("login.jsp?registration=success"); // Redirect to login page after successful registration
            } else {
                res.sendRedirect("register.jsp?error=true"); // Redirect back to the registration form if registration failed
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB error when registering user", e);
        } finally {
            // Close all resources to prevent memory leaks
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
