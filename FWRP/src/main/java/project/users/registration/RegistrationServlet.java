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

import project.item.management.dao.DAOFactory;
import project.item.management.dao.ItemsDAO;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ItemsDAO itemsDAO;

    
    public void init() {
        itemsDAO = DAOFactory.getItemsDAO();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String User_Name = request.getParameter("name");
        String User_Email = request.getParameter("email");
        String User_Password = request.getParameter("pass");
        String User_RePassword = request.getParameter("re_pass");
        String User_Phone = request.getParameter("contact");
        String User_Type = request.getParameter("type");
        String User_Province = request.getParameter("province");

        boolean validationFailed = false;


        if (User_Province == null || User_Province.equals("")) {
            request.setAttribute("status", "invalidProvince");
            validationFailed = true;
        }
        if (User_Type == null || User_Type.equals("")) {
            request.setAttribute("status", "invalidType");
            validationFailed = true;
        }
        if (User_Phone == null || User_Phone.equals("") || User_Phone.length() > 10) {
            request.setAttribute("status", User_Phone.length() > 10 ? "invalidPhoneNumberLength" : "invalidPhoneNumber");
            validationFailed = true;
        }
        if (User_RePassword == null || User_RePassword.equals("") || !User_RePassword.equals(User_Password)) {
            request.setAttribute("status", User_RePassword.equals("") ? "invalidComfirmPassword" : "invalidMatchPassword");
            validationFailed = true;
        }
        if (User_Password == null || User_Password.equals("")) {
            request.setAttribute("status", "invalidPassword");
            validationFailed = true;
        }
        if (User_Email == null || User_Email.equals("")) {
            request.setAttribute("status", "invalidEmail");
            validationFailed = true;
        }
        if (User_Name == null || User_Name.equals("")) {
            request.setAttribute("status", "invalidName");
            validationFailed = true;
        }


        if (validationFailed) {
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        } else {
            try {
                boolean isRegistered = itemsDAO.registerUser(User_Name, User_Email, User_Password, User_Phone, User_Type, User_Province);
                if (isRegistered) {
                    request.setAttribute("status", "success");
                } else {
                    request.setAttribute("status", "failed");
                }
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                // Consider adding an error status attribute and forwarding to an error page or back to the form with an error message.
            }
        }
    		
	}

}
