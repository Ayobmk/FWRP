package project.item.management.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import project.item.management.model.Items;
import project.item.management.model.Order;

/**
 * Defines the operations that can be performed on the items and orders within the item management system.
 * This interface abstracts the data access layer, allowing for the manipulation of item and order data
 * in a database. It includes methods for creating, reading, updating, and deleting (CRUD) items, as well
 * as specific operations such as claiming items, buying items, and fetching orders.
 */
public interface ItemsDAO {
	
	boolean registerUser(String userName, String userEmail, String userPassword, String userPhone, String userType, String userProvince) throws SQLException;    
	void insertItem(Items item) throws SQLException;
    boolean updateItem(Items item) throws SQLException;
    Items selectItem(int id) throws SQLException;
    List<Items> selectAllItem() throws SQLException;
    boolean deleteItem(int id) throws SQLException;
    List<Items> fetchItemsExpiringSoon(LocalDate startDate, LocalDate endDate) throws SQLException;
    boolean claimItem(int itemId, String userName, String userEmail, String userType);
    public List<Order> fetchAllOrders() throws SQLException;
    public boolean buyItem(int itemId, String userName, String userEmail, String userType, double discountedPrice) throws SQLException ;
    boolean subscribeUser(String userName, String userEmail, String userType, String userProvince, String foodPref) throws SQLException;
    List<Order> fetchOrdersByUserEmail(String userEmail) throws SQLException;

}
