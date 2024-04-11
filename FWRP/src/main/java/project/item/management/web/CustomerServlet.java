package project.item.management.web;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.item.management.dao.DAOFactory;
import project.item.management.dao.ItemsDAO;
import project.item.management.dao.ItemsDAOimp;
import project.item.management.model.DefaultItemCalculationStrategy;
//import project.item.management.dao.ItemsDAOimp;
import project.item.management.model.Items;

/**
 * Servlet implementation class CustomerServlet
 * This servlet handles requests from customers, displaying items for sale and managing calculations for discounted prices.
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemsDAOimp itemsDAO;
    private DefaultItemCalculationStrategy calculationStrategy = new DefaultItemCalculationStrategy();

    /**
     * Initializes the servlet by creating an instance of ItemsDAOimp.
     */
    public CustomerServlet() {
        this.itemsDAO = new ItemsDAOimp(); // Initialize your ItemsDAO implementation
    }

    
    /**
     * Handles HTTP GET requests.
     * Retrieves items for sale, calculates discounted prices, and forwards the request to customer.jsp.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all items for sale
		List<Items> allSellItems = itemsDAO.selectAllItem().stream()
		        .filter(item -> "Sell".equals(item.getReason()))
		        .collect(Collectors.toList());

		// Determine items expiring within 7 days and not surplus
		List<Items> itemsExpiringSoon = allSellItems.stream()
		        .filter(item -> calculationStrategy.isItemNearToExpire(item) && !item.isSurplus())
		        .collect(Collectors.toList());
		
		// Fetch surplus items
	    List<Items> surplusItems = itemsDAO.selectAllItem().stream()
	                                       .filter(Items::isSurplus)
	                                       .collect(Collectors.toList());
	    
		// Filter for other sellable items, excluding items expiring soon and marked as surplus
		List<Items> otherSellItems = allSellItems.stream()
		        .filter(item -> !calculationStrategy.isItemNearToExpire(item) && !item.isSurplus())
		        .collect(Collectors.toList());

		// Calculate discounted prices for all sellable items
		Map<Integer, Double> discountedPrices = new HashMap<>();
		allSellItems.forEach(item -> discountedPrices.put(item.getId(), calculationStrategy.calculateDiscountedPrice(item)));

		request.setAttribute("listItemsExpiringSoon", itemsExpiringSoon);
		request.setAttribute("otherSellItems", otherSellItems);
		request.setAttribute("discountedPrices", discountedPrices);
	    request.setAttribute("surplusItems", surplusItems);

        RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
        dispatcher.forward(request, response);
    }
}
