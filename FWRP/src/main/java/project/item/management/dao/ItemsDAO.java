package project.item.management.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import project.item.management.model.Items;
import project.item.management.model.Order;

public interface ItemsDAO {
    
	void insertItem(Items item) throws SQLException;
    boolean updateItem(Items item) throws SQLException;
    Items selectItem(int id) throws SQLException;
    List<Items> selectAllItem() throws SQLException;
    boolean deleteItem(int id) throws SQLException;
    List<Items> fetchItemsExpiringSoon(LocalDate startDate, LocalDate endDate) throws SQLException;
    boolean claimItem(int itemId, String userName, String userEmail, String userType);
    public List<Order> fetchAllOrders() throws SQLException;
    public boolean buyItem(int itemId, String userName, String userEmail, String userType, double discountedPrice) throws SQLException ;
}
