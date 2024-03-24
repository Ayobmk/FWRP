package project.item.management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.item.management.model.Items;
//import project.mysql.cj.xdevapi.Statement;


//this DAO class provides CRUD database operations for the table items in the database
public class ItemsDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/fwrp?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "9448";
	
	
	
    private static final String INSERT_ITEMS_SQL = "INSERT INTO items (itemName, itemType, itemDescription, expDate, price) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ITEM_BY_ID = "SELECT id, itemName, itemType, itemDescription, expDate, price FROM items WHERE id =?";
    private static final String SELECT_ALL_ITEMS = "SELECT * FROM items";
    private static final String DELETE_ITEMS_SQL = "DELETE FROM items WHERE id = ?;";
    private static final String UPDATE_ITEMS_SQL = "UPDATE items SET itemName = ?, itemType = ?, itemDescription = ?, expDate = ?, price = ? WHERE id = ?;";

	
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
					pst.setString(4, item.getExpDate());
					pst.setDouble(5, item.getPrice());
					
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
					pst.setString(4, item.getExpDate());
					pst.setDouble(5, item.getPrice());
					pst.setInt(6, item.getId());
					
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
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                item = new Items(id, itemName, itemType, itemDescription, expDate, price);
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
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                items.add(new Items(id, itemName, itemType, itemDescription, expDate, price));
				
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
}
