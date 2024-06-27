import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
   protected void doPost(HttpServletRequest req,HttpServletResponse res) {
	   try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/egrocery", "root", "2003");
		String email=req.getParameter("email");
		String pass=req.getParameter("pass");
		PreparedStatement ps=con.prepareStatement("select * from users where email=? and password=?");
		ps.setString(1, email);
		ps.setString(2, pass);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			   HttpSession session = req.getSession(); // Fetch current session or create one if it doesn't exist
			    session.setAttribute("email", email);
			  Cookie loginCookie = new Cookie("email", email); 
			  loginCookie.setMaxAge(30 * 60*48); // setting cookie to expire in 30 minutes //
			  res.addCookie(loginCookie);
			 			RequestDispatcher rd=req.getRequestDispatcher("Home.jsp");
			rd.forward(req, res);
		}
		else {
			res.sendRedirect("login.jsp?error=true");
		}
		
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	} catch (ServletException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
	   
   }
}
