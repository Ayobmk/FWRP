package project.item.management.dao;

public class DAOFactory {

    // Private constructor to prevent instantiation
    private DAOFactory() {}

    public static ItemsDAO getItemsDAO() {
        return new ItemsDAOimp();
    }

}
