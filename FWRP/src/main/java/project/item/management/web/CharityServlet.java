package project.item.management.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import project.item.management.dao.DAOFactory;
import project.item.management.dao.ItemsDAO;
import project.item.management.model.Items;

@WebServlet("/charity")
public class CharityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemsDAO itemsDAO;

    public CharityServlet() {
        this.itemsDAO = DAOFactory.getItemsDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Fetch all items and filter to include only items marked for donation
            List<Items> donationItems = itemsDAO.selectAllItem().stream()
                    .filter(item -> "Donation".equals(item.getReason()))
                    .collect(Collectors.toList());
            
            request.setAttribute("donationItems", donationItems);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching donation items.");
        }

        // Forward the request to charity.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("charity.jsp");
        dispatcher.forward(request, response);
    }
}
