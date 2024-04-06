package project.item.management.button;

import java.io.IOException;

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
 * Servlet implementation class ClaimItemServlet
 */
@WebServlet("/ClaimItemServlet")
public class ClaimItemServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("name");
        String userEmail = (String) session.getAttribute("email");
        String userType = (String) session.getAttribute("userType");
        
        ItemsDAO itemsDAO = DAOFactory.getItemsDAO();
        boolean success = itemsDAO.claimItem(itemId, userName, userEmail, userType);

        if (success) {
            // indicate success
        	response.sendRedirect("charity");
//        	response.sendRedirect("charity.jsp?claimSuccess=true");
        } else {
            // Handle failure
            request.setAttribute("errorMessage", "Unable to claim the item.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("charity.jsp");
            dispatcher.forward(request, response);
        }
    }

}
