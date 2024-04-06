package project.item.management.button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project.item.management.dao.ItemsDAOimp;

/**
 * Servlet implementation class BuyItemServlet
 */
@WebServlet("/BuyItemServlet")
public class BuyItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        HttpSession session = request.getSession();
        String buyerName = (String) session.getAttribute("userName"); 
        String buyerEmail = (String) session.getAttribute("name"); 
        String userType = (String) session.getAttribute("userType"); 
        
        // Retrieve the map of discounted prices from where it's stored 
        Map<Integer, Double> discountedPrices = (Map<Integer, Double>) session.getAttribute("discountedPrices");
        double discountedPrice = discountedPrices.getOrDefault(itemId, 0.0); // Get the discounted price for the item, default to 0.0 if not found
        
        ItemsDAOimp itemsDAO = new ItemsDAOimp();
        try {
            boolean success = itemsDAO.buyItem(itemId, buyerName, buyerEmail, userType, discountedPrice);
            if (success) {
                // Redirect to a success page or display a success message
                response.sendRedirect("customer");
            } else {
                // Error handling
                response.sendRedirect("customer.jsp?error=Purchase failed");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during the purchase", e);
        }
    }

}
