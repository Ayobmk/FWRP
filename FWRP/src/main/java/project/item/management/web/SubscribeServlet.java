package project.item.management.web;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

import project.item.management.dao.DAOFactory;
import project.item.management.dao.ItemsDAO;

/**
 * Servlet implementation class SubscribeServlet
 * This servlet handles user subscription to receive updates.
 */
@WebServlet("/SubscribeServlet")
public class SubscribeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("name");
        String userName = (String) session.getAttribute("userName");
        String userType = (String) session.getAttribute("userType");
        String userProvince = (String) session.getAttribute("userProvince");
        String foodPref = request.getParameter("foodPref");
        
        ItemsDAO itemsDAO = DAOFactory.getItemsDAO();

        try {
            // Call the subscription method
            boolean isSubscribed = itemsDAO.subscribeUser(userName, userEmail, userType, foodPref, userProvince);

            if (isSubscribed) {
                session.setAttribute("subscriptionMessage", "You subscribed successfully!");
            } else {
                session.setAttribute("subscriptionMessage", "Subscription failed. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("subscriptionMessage", "An error occurred during subscription.");
        }

        response.sendRedirect("index.jsp");
        
    }
}