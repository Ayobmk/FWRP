package project.item.management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import project.item.management.model.Order;

import project.item.management.model.DefaultItemCalculationStrategy;
//import project.item.management.model.ItemCalculationStrategy;
import project.item.management.model.Items;
//import project.mysql.cj.xdevapi.Statement;


//this DAO class provides CRUD database operations for the table items in the database
public class ItemsDAOimp implements ItemsDAO{
	private String jdbcURL = "jdbc:mysql://localhost:3306/fwrp?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "9448";
	
	
	
    private static final String INSERT_ITEMS_SQL = "INSERT INTO items (image, itemName, itemType, itemDescription, reason, expDate, price, surplus) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ITEM_BY_ID = "SELECT id, image, itemName, itemType, itemDescription, reason, expDate, price, surplus FROM items WHERE id =?";
    private static final String SELECT_ALL_ITEMS = "SELECT * FROM items";
    private static final String DELETE_ITEMS_SQL = "DELETE FROM items WHERE id = ?;";
    private static final String UPDATE_Not_Null_ITEMS_SQL = "UPDATE items SET image = ?, itemName = ?, itemType = ?, itemDescription = ?, reason = ?, expDate = ?, price = ?, surplus = ? WHERE id = ?";
    private static final String UPDATE_Null_ITEMS_SQL = "UPDATE items SET itemName = ?, itemType = ?, itemDescription = ?, reason = ?, expDate = ?, price = ?, surplus = ? WHERE id = ?";
   // private static final String SELECT_ITEMS_EXPIRING_SOON = "SELECT * FROM items WHERE expDate <= ? AND expDate >= ?;";
   // private static final String SELECT_ITEMS_EXPIRING_SOON = "SELECT * FROM items WHERE expDate <= ? AND expDate >= ? AND reason = 'Sell';";
    private DefaultItemCalculationStrategy calculationStrategy = new DefaultItemCalculationStrategy();

	
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
	
	//Implement the registerUser method
	@Override
	public boolean registerUser(String userName, String userEmail, String userPassword, String userPhone, String userType, String userProvince) throws SQLException {
	    boolean result = false;
	    String sql = "INSERT INTO Users (User_Name, User_Email, User_Password, User_Phone, User_Type, User_Province) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, userName);
	        preparedStatement.setString(2, userEmail);
	        preparedStatement.setString(3, userPassword);
	        preparedStatement.setString(4, userPhone);
	        preparedStatement.setString(5, userType);
	        preparedStatement.setString(6, userProvince);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        result = rowsAffected > 0;
	    }
	    return result;
	}
	
	// Create or insert Items 
	public void insertItem(Items item) throws SQLException {
		try(Connection con = getConnection();
				PreparedStatement pst = con.prepareStatement(INSERT_ITEMS_SQL)){
			
					pst.setBytes(1, item.getImage());
					pst.setString(2, item.getItemName());
					pst.setString(3, item.getItemType());
					pst.setString(4, item.getItemDescription());
					pst.setString(5, item.getReason());
					pst.setString(6, item.getExpDate());
					pst.setDouble(7, item.getPrice());
					pst.setBoolean(8, item.isSurplus());
					
					pst.executeUpdate();
				}catch (Exception e) {
					e.printStackTrace();
				}
	}
	// Update Items 
	public boolean updateItem(Items item) throws SQLException {
		boolean rowUpdated;
	    String sql = item.getImage() != null ? UPDATE_Not_Null_ITEMS_SQL : UPDATE_Null_ITEMS_SQL;

		try(Connection con = getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
					
				if (item.getImage() != null) {
					pst.setBytes(1, item.getImage());
					pst.setString(2, item.getItemName());
					pst.setString(3, item.getItemType());
					pst.setString(4, item.getItemDescription());
					pst.setString(5, item.getReason());
					pst.setString(6, item.getExpDate());
					pst.setDouble(7, item.getPrice());
					pst.setBoolean(8, item.isSurplus());
					pst.setInt(9, item.getId());
				} else {
					pst.setString(1, item.getItemName());
					pst.setString(2, item.getItemType());
					pst.setString(3, item.getItemDescription());
					pst.setString(4, item.getReason());
					pst.setString(5, item.getExpDate());
					pst.setDouble(6, item.getPrice());
					pst.setBoolean(7, item.isSurplus());
					pst.setInt(8, item.getId());
				}

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
            	byte[] imageBytes = rs.getBytes("image");
                String itemName = rs.getString("itemName");
                String itemType = rs.getString("itemType");
                String itemDescription = rs.getString("itemDescription");
                String reason = rs.getString("reason");
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                boolean surplus = rs.getBoolean("surplus");
                item = new Items(id, imageBytes, itemName, itemType, itemDescription, reason, expDate, price, surplus);
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
                byte[] imageBytes = rs.getBytes("image");
                String itemName = rs.getString("itemName");
                String itemType = rs.getString("itemType");
                String itemDescription = rs.getString("itemDescription");
                String reason = rs.getString("reason");
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");
                boolean surplus = rs.getBoolean("surplus");
                items.add(new Items(id, imageBytes, itemName, itemType, itemDescription, reason, expDate, price, surplus));
				
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
	

    @Override
    public List<Items> fetchItemsExpiringSoon(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Items> allItems = selectAllItem(); // Fetch all items

        // Filter items based on the isItemNearToExpire logic
        return allItems.stream()
                       .filter(item -> calculationStrategy.isItemNearToExpire(item)&& "Sell".equals(item.getReason()))
                       .collect(Collectors.toList());
    }

    @Override
    public boolean buyItem(int itemId, String userName, String userEmail, String userType, double discountedPrice) throws SQLException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement fetchStatement = null;
        PreparedStatement insertStatement = null;
        PreparedStatement insertStatementCustomer = null;
        PreparedStatement deleteStatement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Fetch the item details
            String fetchSql = "SELECT * FROM items WHERE id = ?";
            fetchStatement = connection.prepareStatement(fetchSql);
            fetchStatement.setInt(1, itemId);
            ResultSet rs = fetchStatement.executeQuery();

            if (rs.next()) {
                // Extract item details
                byte[] image = rs.getBytes("image");
                String itemName = rs.getString("itemName");
                String itemType = rs.getString("itemType");
                String itemDescription = rs.getString("itemDescription");
                String reason = rs.getString("reason");
                String expDate = rs.getString("expDate");
                double price = rs.getDouble("price");

                // Insert into itemsOrders
                String insertSql = "INSERT INTO itemsOrders (image, itemName, itemType, itemDescription, reason, expDate, price, Discounted_Price, User_Name, User_Email, User_Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setBytes(1, image);
                insertStatement.setString(2, itemName);
                insertStatement.setString(3, itemType);
                insertStatement.setString(4, itemDescription);
                insertStatement.setString(5, reason);
                insertStatement.setString(6, expDate);
                insertStatement.setDouble(7, price); // Original price
                insertStatement.setDouble(8, discountedPrice); // Discounted price
                insertStatement.setString(9, userName);
                insertStatement.setString(10, userEmail);
                insertStatement.setString(11, userType);                
                insertStatement.executeUpdate();
                
                // Insert into itemsOrdersCustomer
                String insertSqlCustomer = "INSERT INTO itemsOrdersCustomer (image, itemName, itemType, itemDescription, reason, expDate, price, Discounted_Price, User_Name, User_Email, User_Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                insertStatementCustomer = connection.prepareStatement(insertSqlCustomer);
                insertStatementCustomer.setBytes(1, image);
                insertStatementCustomer.setString(2, itemName);
                insertStatementCustomer.setString(3, itemType);
                insertStatementCustomer.setString(4, itemDescription);
                insertStatementCustomer.setString(5, reason);
                insertStatementCustomer.setString(6, expDate);
                insertStatementCustomer.setDouble(7, price); // Original price
                insertStatementCustomer.setDouble(8, discountedPrice); // Discounted price
                insertStatementCustomer.setString(9, userName);
                insertStatementCustomer.setString(10, userEmail);
                insertStatementCustomer.setString(11, userType);                
                insertStatementCustomer.executeUpdate();

                // Delete from items
                String deleteSql = "DELETE FROM items WHERE id = ?";
                deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, itemId);
                deleteStatement.executeUpdate();

                connection.commit(); // Commit transaction
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        } finally {
            if (fetchStatement != null) fetchStatement.close();
            if (insertStatement != null) insertStatement.close();
            if (insertStatementCustomer != null) insertStatement.close();
            if (deleteStatement != null) deleteStatement.close();
            if (connection != null) {
                connection.setAutoCommit(true); // Reset auto-commit
                connection.close();
            }
        }

        return success;
    }

    
    @Override
    public boolean claimItem(int itemId, String userName, String userEmail, String userType) {
        Connection connection = null;
        PreparedStatement fetchStatement = null;
        PreparedStatement insertStatement = null;
        PreparedStatement deleteStatement = null;
        boolean success = false;

        try {
            connection = getConnection();
            // Start transaction
            connection.setAutoCommit(false);

            // Fetch the item from `items`
            String fetchSql = "SELECT * FROM items WHERE id = ?";
            fetchStatement = connection.prepareStatement(fetchSql);
            fetchStatement.setInt(1, itemId);
            ResultSet itemResult = fetchStatement.executeQuery();

            if (itemResult.next()) {
                // Extract item details
                byte[] image = itemResult.getBytes("image");
                String itemName = itemResult.getString("itemName");
                String itemType = itemResult.getString("itemType");
                String itemDescription = itemResult.getString("itemDescription");
                String reason = itemResult.getString("reason");
                String expDate = itemResult.getString("expDate");
                double price = itemResult.getDouble("price");
                
                // Access calculationStrategy
                Items item = new Items(itemId, image, itemName, itemType, itemDescription, reason, expDate, price, false); // Assuming surplus is false or adjust accordingly
                double discountedPrice = calculationStrategy.calculateDiscountedPrice(item);

                // Insert into `itemsOrders`
                String insertSql = "INSERT INTO itemsOrders (image, itemName, itemType, itemDescription, reason, expDate, price, Discounted_Price, User_Name, User_Email, User_Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setBytes(1, image);
                insertStatement.setString(2, itemName);
                insertStatement.setString(3, itemType);
                insertStatement.setString(4, itemDescription);
                insertStatement.setString(5, reason);
                insertStatement.setString(6, expDate);
                insertStatement.setDouble(7, price);
                insertStatement.setDouble(8, discountedPrice);
                insertStatement.setString(9, userName);
                insertStatement.setString(10, userEmail);
                insertStatement.setString(11, userType);

                int insertCount = insertStatement.executeUpdate();

                if (insertCount > 0) {
                    // Delete from `items`
                    String deleteSql = "DELETE FROM items WHERE id = ?";
                    deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setInt(1, itemId);
                    int deleteCount = deleteStatement.executeUpdate();

                    if (deleteCount > 0) {
                        success = true;
                    }
                }
                
            }

            if (success) {
                // Commit transaction
                connection.commit();
            } else {
                // Rollback transaction in case of failure
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    // Rollback transaction in case of exception
                    connection.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (fetchStatement != null) fetchStatement.close();
                if (insertStatement != null) insertStatement.close();
                if (deleteStatement != null) deleteStatement.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto-commit to true
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return success;
    }
    
    @Override
    public List<Order> fetchAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM itemsOrders";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setImage(rs.getBytes("image"));
                order.setItemName(rs.getString("itemName"));
                order.setItemType(rs.getString("itemType"));
                order.setItemDescription(rs.getString("itemDescription"));
                order.setReason(rs.getString("reason"));
                order.setExpDate(rs.getString("expDate"));
                order.setPrice(rs.getDouble("price"));
                order.setDiscountedPrice(rs.getDouble("Discounted_Price"));
                order.setUserName(rs.getString("User_Name"));
                order.setUserEmail(rs.getString("User_Email"));
                order.setUserType(rs.getString("User_Type"));
                orders.add(order);
            } 
            
        }
        return orders;
    }
    
    @Override
    public List<Order> fetchOrdersByUserEmail(String userEmail) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM itemsOrdersCustomer WHERE User_Email = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEmail);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                // Assuming you have a constructor or setters to set these
                order.setId(rs.getInt("id"));
                order.setImage(rs.getBytes("image"));
                order.setItemName(rs.getString("itemName"));
                order.setItemType(rs.getString("itemType"));
                order.setItemDescription(rs.getString("itemDescription"));
                order.setReason(rs.getString("reason"));
                order.setExpDate(rs.getString("expDate"));
                order.setPrice(rs.getDouble("price"));
                order.setDiscountedPrice(rs.getDouble("Discounted_Price"));
                order.setUserName(rs.getString("User_Name"));
                order.setUserEmail(rs.getString("User_Email"));
                order.setUserType(rs.getString("User_Type"));
                orders.add(order);
            }
        }
        return orders;
    }
    
    @Override
    public boolean subscribeUser(String userName, String userEmail, String userType, String foodPref, String userProvince) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement("INSERT INTO Subscriber (User_Name, User_Email, User_Type, Food_Pref, User_Province) VALUES (?, ?, ?, ?, ?)")) {
        	pst.setString(1, userName);
        	pst.setString(2, userEmail);
        	pst.setString(3, userType);
        	pst.setString(4, foodPref);
            pst.setString(5, userProvince);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}