package project.item.management.dao;

/**
 * A factory class for creating DAO (Data Access Object) instances. This class uses the Factory
 * pattern to encapsulate the instantiation logic of DAO objects, making it easier to manage
 * the creation process and to provide specific DAO implementations without exposing the
 * instantiation logic.
 * 
 * The DAO pattern is a structural pattern that allows for abstracting and encapsulating access
 * to data sources. The DAO manages the connection with the data source to obtain and store data.
 */
public class DAOFactory {

    // Private constructor to prevent instantiation
    private DAOFactory() {}

    public static ItemsDAO getItemsDAO() {
        return new ItemsDAOimp();
    }

}
