package project.item.management.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import project.item.management.model.Items;
import project.item.management.model.Order;

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
