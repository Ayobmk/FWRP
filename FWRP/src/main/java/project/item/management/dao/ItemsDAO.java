package project.item.management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import project.item.management.model.Items;
//import project.mysql.cj.xdevapi.Statement;


//this DAO class provides CRUD database operations for the table items in the database
public class ItemsDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/fwrp?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "9448";
	
	
	
    private static final String INSERT_ITEMS_SQL = "INSERT INTO items (itemName, itemType, itemDescription, reason, expDate, price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ITEM_BY_ID = "SELECT id, itemName, itemType, itemDescription, reason, expDate, price FROM items WHERE id =?";
    private static final String SELECT_ALL_ITEMS = "SELECT * FROM items";
    private static final String DELETE_ITEMS_SQL = "DELETE FROM items WHERE id = ?;";
    private static final String UPDATE_ITEMS_SQL = "UPDATE items SET itemName = ?, itemType = ?, itemDescription = ?, reason = ?, expDate = ?, price = ? WHERE id = ?;";
   // private static final String SELECT_ITEMS_EXPIRING_SOON = "SELECT * FROM items WHERE expDate <= ? AND expDate >= ?;";
    private static final String SELECT_ITEMS_EXPIRING_SOON = "SELECT * FROM items WHERE expDate <= ? AND expDate >= ?;";

	
	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	// Create or insert Items 
	public void insertItem(Items item) throws SQLException {
		try(Connection con = getConnection();
				PreparedStatement pst = con.prepareStatement(INSERT_ITEMS_SQL)){
					pst.setString(1, item.getItemName());
					pst.setString(2, item.getItemType());
					pst.setString(3, item.getItemDescription());
					pst.setString(4, item.getReason());
					pst.setString(5, item.getExpDate());
					pst.setDouble(6, item.getPrice());
					
					pst.executeUpdate();
				}catch (Exception e) {
					e.printStackTrace();
				}
	}
	// Update Items 
	public boolean updateItem(Items item) throws SQLException {
		boolean rowUpdated;
		try(Connection con = getConnection();
				PreparedStatement pst = con.prepareStatement(UPDATE_ITEMS_SQL)){
					pst.setString(1, item.getItemName());
					pst.setString(2, item.getItemType());
					pst.setString(3, item.getItemDescription());
					pst.setString(4, item.getReason());
					pst.setString(5, item.getExpDate());
					pst.setDouble(6, item.getPrice());
					pst.setInt(7, item.getId());
					
					int affectedRows = pst.executeUpdate();
					System.out.println("Affected rows: " + affectedRows);

					rowUpdated = pst.executeUpdate() >0;
					
				}
		return rowUpdated;
	}
	// Select Item by id
	public Items selectItem(int id) {
		Items item = null;
		//Establish the connection
		try(Connection con = getConnection();
				//Create a statement using connection object
				PreparedStatement pst = con.prepareStatement(SELECT_ITEM_BY_ID);){
			pst.setInt(1, id);
			System.out.println(pst);
			
			//Execute the query or Update query
			ResultSet rs = pst.executeQuery();
			
			//Process the ResultSet object
            while (rs.next()) {
                String itemName = rs.getString("itemName");
                String itemType = rs.getString("itemType");
                String itemDescription = rs.getString("itemDescription");
                String reason = rs.getString("reason");
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                item = new Items(id, itemName, itemType, itemDescription, reason, expDate, price);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;	
				
	}
	// Select Items 
	public List<Items> selectAllItem() {
		List<Items> items = new ArrayList<>();
		//Establish the connection
		try(Connection con = getConnection();
				//Create a statement using connection object
				PreparedStatement pst = con.prepareStatement(SELECT_ALL_ITEMS);){
				System.out.println(pst);
			
			//Execute the query or Update query
			ResultSet rs = pst.executeQuery();
			
			//Process the ResultSet object
            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("itemName");
                String itemType = rs.getString("itemType");
                String itemDescription = rs.getString("itemDescription");
                String reason = rs.getString("reason");
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                items.add(new Items(id, itemName, itemType, itemDescription, reason, expDate, price));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;	
				
	}

	//Delete Item
	public boolean deleteItem(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection con = getConnection();
				PreparedStatement pst =con.prepareStatement(DELETE_ITEMS_SQL);){
			pst.setInt(1, id);
			rowDeleted = pst.executeUpdate() > 0;
		} return rowDeleted;
	}
	
	//selectItemsExpiringSoon
//    public List<Items> selectItemsExpiringSoon() {
//        List<Items> itemsExpiringSoon = new ArrayList<>();
//        LocalDate today = LocalDate.now();
//        LocalDate sevenDaysFromNow = today.plusDays(7);
//        
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(SELECT_ITEMS_EXPIRING_SOON)) {
//           // pst.setString(1, sevenDaysFromNow.toString());
//           // pst.setString(2, today.toString());
//           // ResultSet rs = pst.executeQuery();
//        	pst.setDate(1, java.sql.Date.valueOf(today));
//            pst.setDate(2, java.sql.Date.valueOf(sevenDaysFromNow));
//            ResultSet rs = pst.executeQuery();
//            System.out.println("Items fetched: " + itemsExpiringSoon.size());
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String itemName = rs.getString("itemName");
//                String itemType = rs.getString("itemType");
//                String itemDescription = rs.getString("itemDescription");
//                String reason = rs.getString("reason");
//                String expDate = rs.getString("expDate");
//                double price = rs.getDouble("price");
//                
//                itemsExpiringSoon.add(new Items(id, itemName, itemType, itemDescription, reason, expDate, price));
//            }
//        } catch (SQLException e) {
//        	e.printStackTrace();
//        }
//        return itemsExpiringSoon;
//    }
	
    public List<Items> fetchItemsExpiringSoon(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Items> itemsList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(SELECT_ITEMS_EXPIRING_SOON)) {
            pst.setDate(1, java.sql.Date.valueOf(endDate));
            pst.setDate(2, java.sql.Date.valueOf(startDate));
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String itemName = resultSet.getString("itemName");
                String itemType = resultSet.getString("itemType");
                String itemDescription = resultSet.getString("itemDescription");
                String reason = resultSet.getString("reason");
                String expDate = resultSet.getString("expDate");
                double price = resultSet.getDouble("price");
                Items item = new Items(id, itemName, itemType, itemDescription, reason, expDate, price);
                itemsList.add(item);
            }
        }
        return itemsList;
    }
    
}
