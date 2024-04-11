package project.item.management.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project.item.management.dao.ItemsDAOimp;
import project.item.management.model.Order;

/**
 * Servlet implementation class CustomerStatusServlet
 * This servlet retrieves information about the current status of a customer's orders and notifications.
 */
@WebServlet("/customerStatus")
public class CustomerStatusServlet extends HttpServlet {
    private ItemsDAOimp itemsDAO;

    
    /**
     * Initializes the servlet by creating an instance of ItemsDAOimp.
     */
    public void init() {
        this.itemsDAO = new ItemsDAOimp(); // Initialize your DAO
    }

    
    /**
     * Handles HTTP GET requests.
     * Retrieves user orders and notifications, forwards the request to customerStatus.jsp.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session
        String userEmail = (String) session.getAttribute("name"); // Ensure you're using the correct attribute for email

        try {
            // Fetch orders by the user's email
            List<Order> userOrders = itemsDAO.fetchOrdersByUserEmail(userEmail);
            request.setAttribute("userOrders", userOrders);
            
            // Fetch notifications for the user
            List<String> notifications = itemsDAO.fetchNotificationsForUser(userEmail);
            request.setAttribute("notifications", notifications);
            
            // Mark notifications as seen, logically this is done before the forward to reflect in subsequent requests
            itemsDAO.markNotificationsAsSeen(userEmail);

            RequestDispatcher dispatcher = request.getRequestDispatcher("customerStatus.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching user orders", e);
        }
    }
}
