package project.item.management.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.item.management.dao.ItemsDAO;
import project.item.management.model.Items;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemsDAO itemsDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
    	this.itemsDAO = new ItemsDAO();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	this.doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			try {
				insertItem(request, response);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteItem(request, response);
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateItem(request, response);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
        case "/expiringSoon":
            try {
            	listItemsExpiringSoon(request, response);
			} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
			}
            break;
		default:
			try {
				listItems(request,response);
			} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	//default
	private void listItems(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException{
		List<Items> listItems = itemsDAO.selectAllItem();
		request.setAttribute("listItems", listItems);
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
		dispatcher.forward(request, response);
	}

	//listItemsExpiringSoon
//    private void listItemsExpiringSoon(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, ServletException, IOException {
//        List<Items> listItemsExpiringSoon = itemsDAO.selectItemsExpiringSoon();
//        request.setAttribute("listItemsExpiringSoon", listItemsExpiringSoon);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);
//    }
	void listItemsExpiringSoon(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {
	    LocalDate today = LocalDate.now();
	    LocalDate sevenDaysFromNow = today.plusDays(7);
	    List<Items> listItemsExpiringSoon = itemsDAO.fetchItemsExpiringSoon(today, sevenDaysFromNow);
	    request.setAttribute("listItemsExpiringSoon", listItemsExpiringSoon);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
	    dispatcher.forward(request, response);
	}



	//Method to handle update Method Request
    private void updateItem(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String itemName = request.getParameter("itemName");
        String itemType = request.getParameter("itemType");
        String itemDescription = request.getParameter("itemDescription");
        String reason = request.getParameter("reason");
        String expDate = request.getParameter("expDate");
        double price = Double.parseDouble(request.getParameter("price"));
        Items item = new Items(id, itemName, itemType, itemDescription, reason, expDate, price);
        itemsDAO.updateItem(item);
        response.sendRedirect("list");
    }

	
	//Method to handle delete Method Request
    private void deleteItem(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        itemsDAO.deleteItem(id);
        response.sendRedirect("list");
    }

	
	//showEditForm Method
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Items existingItem = itemsDAO.selectItem(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        request.setAttribute("item", existingItem);
        dispatcher.forward(request, response);
    }


	
	//showNewForm Method
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
		dispatcher.forward(request, response);
	}
	
	//Method to handle Insert Method request
    private void insertItem(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String itemName = request.getParameter("itemName");
        String itemType = request.getParameter("itemType");
        String itemDescription = request.getParameter("itemDescription");
        String reason = request.getParameter("reason");
        String expDate = request.getParameter("expDate");
        double price = Double.parseDouble(request.getParameter("price"));
        Items newItem = new Items(itemName, itemType, itemDescription, reason, expDate, price);
        itemsDAO.insertItem(newItem);
        response.sendRedirect("list");
    }


}
