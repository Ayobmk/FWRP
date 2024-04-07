package project.item.management.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project.item.management.dao.ItemsDAOimp;
import project.item.management.model.Order;

@WebServlet("/customerStatus")
public class CustomerStatusServlet extends HttpServlet {
    private ItemsDAOimp itemsDAO;

    public void init() {
        this.itemsDAO = new ItemsDAOimp(); // Initialize your DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session
        String userEmail = (String) session.getAttribute("name"); // Ensure you're using the correct attribute for email

        try {
            // Fetch orders by the user's email
            List<Order> userOrders = itemsDAO.fetchOrdersByUserEmail(userEmail);
            request.setAttribute("userOrders", userOrders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customerStatus.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching user orders", e);
        }
    }
}
