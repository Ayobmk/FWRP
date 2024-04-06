package project.users.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String User_Name = request.getParameter("name");
		String User_Email = request.getParameter("email");
		String User_Password = request.getParameter("pass");
		String User_RePassword = request.getParameter("re_pass");
		String User_Phone = request.getParameter("contact");
		String User_Type = request.getParameter("type");
		String User_Province = request.getParameter("province");
		
		RequestDispatcher dispatcher = null;
		Connection con = null;

		// check the Name if is null so it won't send request to the server
				if(User_Name == null || User_Name.equals("")) {
					request.setAttribute("status", "invalidName");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}
		// check the Email if is null so it won't send request to the server
				if(User_Email == null || User_Email.equals("")) {
					request.setAttribute("status", "invalidEmail");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}
				
		// check the Password if is null so it won't send request to the server
				if(User_Password == null || User_Password.equals("")) {
					request.setAttribute("status", "invalidPassword");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}
				
				// check the Password if is null so it won't send request to the server
				if(User_RePassword == null || User_RePassword.equals("")) {
					request.setAttribute("status", "invalidComfirmPassword");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				} else if (!User_RePassword.equals(User_Password)) {
					request.setAttribute("status", "invalidMatchPassword");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);	
				}		
				
		// check the PhoneNumber if is null so it won't send request to the server
				if(User_Phone == null || User_Phone.equals("")) {
					request.setAttribute("status", "invalidPhoneNumber");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				} else if(User_Phone.length()>10){
					request.setAttribute("status", "invalidPhoneNumberLenght(10digits)");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}
		
		// check the Type if is null so it won't send request to the server
				if(User_Type == null || User_Type.equals("")) {
					request.setAttribute("status", "invalidType");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}
				
		// check the Province if is null so it won't send request to the server
				if(User_Province == null || User_Province.equals("")) {
					request.setAttribute("status", "invalidProvince");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
				}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fwrp?useSSL=false","root","9448");
			PreparedStatement pst = con.prepareStatement("INSERT INTO Users(User_Name,User_Email,User_Password,User_Phone,User_Type,User_Province) VALUES(?,?,?,?,?,?) ");
			pst.setString(1, User_Name);
			pst.setString(2, User_Email);
			pst.setString(3, User_Password);
			pst.setString(4, User_Phone);
			pst.setString(5, User_Type);
			pst.setString(6, User_Province);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			
			if( rowCount > 0) {
				request.setAttribute("status", "success");
				
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
