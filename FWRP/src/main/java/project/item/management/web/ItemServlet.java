package project.item.management.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import project.item.management.dao.ItemsDAOimp;
import project.item.management.model.Items;
import project.item.management.model.DefaultItemCalculationStrategy;
import project.item.management.model.ItemCalculationStrategy;
/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/")
@MultipartConfig
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemsDAOimp itemsDAO;
    private ItemCalculationStrategy calculationStrategy;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
    	this.itemsDAO = new ItemsDAOimp();
        this.calculationStrategy = new DefaultItemCalculationStrategy();
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
		Map<Integer, Double> discountedPrices = new HashMap<>();
        for (Items item : listItems) {
            double discountedPrice = calculationStrategy.calculateDiscountedPrice(item); // Calculate the discounted price
            discountedPrices.put(item.getId(), discountedPrice); // Associate the item ID with its discounted price
        }
		System.out.println("Number of items: " + listItems.size()); // Debugging statement
		request.setAttribute("listItems", listItems);
		request.setAttribute("discountedPrices", discountedPrices);
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
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        
     // Initialize variables to hold the form data.
        byte[] imageBytes = null;
        Part filePart = request.getPart("image");
        
        // Check if a new file has been uploaded.
        if (filePart != null && filePart.getSize() > 0) {
            // Process the uploaded file part and get the imageBytes.
            try (InputStream fileContent = filePart.getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = fileContent.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                imageBytes = outputStream.toByteArray();
            } 
        }

        String itemName = request.getParameter("itemName");
        String itemType = request.getParameter("itemType");
        String itemDescription = request.getParameter("itemDescription");
        String reason = request.getParameter("reason");
        String expDate = request.getParameter("expDate");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean surplus = request.getParameter("surplus") != null && request.getParameter("surplus").equals("on");

        Items item = new Items(id, imageBytes, itemName, itemType, itemDescription, reason, expDate, price, surplus);
        try {
            // Update the item in the database. This method needs to properly handle the case where imageBytes is null.
            itemsDAO.updateItem(item);

            // Redirect or forward after successful update.
            response.sendRedirect("list"); // Adjust as needed.
        } catch (SQLException e) {
            throw new ServletException("Database error while updating the item.", e);
        }    }

	
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
	protected void insertItem(HttpServletRequest request, HttpServletResponse response) 
	        throws SQLException, IOException, ServletException { // Already declared to throw ServletException and IOException
	    Part filePart = request.getPart("image"); // This can throw IOException or ServletException which are already handled
	    byte[] imageBytes = null;
	    
	    if (filePart != null) {
	        try (InputStream fileContent = filePart.getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            byte[] buffer = new byte[1024];
	            int read;
	            while ((read = fileContent.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, read);
	            }
	            imageBytes = outputStream.toByteArray(); // Correctly obtain the byte array here
	        } // No need for a catch here as you're throwing the exceptions. Resource management is handled by try-with-resources.
	    }

	    // Get the rest of the form data
	    String itemName = request.getParameter("itemName");
	    String itemType = request.getParameter("itemType");
	    String itemDescription = request.getParameter("itemDescription");
	    String reason = request.getParameter("reason");
	    String expDate = request.getParameter("expDate");
	    double price = Double.parseDouble(request.getParameter("price"));
	    boolean surplus = request.getParameter("surplus") != null && request.getParameter("surplus").equals("on");

	    // Create a new item object
	    Items newItem = new Items(imageBytes, itemName, itemType, itemDescription, reason, expDate, price, surplus);
	    itemsDAO.insertItem(newItem); // Insert the new item
	    response.sendRedirect("list"); // Redirect after insertion
	}



}