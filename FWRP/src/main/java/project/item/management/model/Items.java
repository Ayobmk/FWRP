package project.item.management.model;

public class Items {
    private int id;
    private String itemName;
    private String itemType;
    private String itemDescription;
    private String reason;
    private String expDate;
    private double price;

    // Constructors
    public Items(int id, String itemName, String itemType, String itemDescription, String reason, String expDate, double price) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
        this.reason = reason;
        this.expDate = expDate;
        this.price = price;
    }
    
    public Items(String itemName, String itemType, String itemDescription, String reason, String expDate, double price) {
        this(0, itemName, itemType, itemDescription, reason, expDate, price); // Assuming ID is auto-generated or not needed for insertion.
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public String getReason() {
    	return reason;
    }
    
    public void setReason(String reason) {
    	this.reason = reason;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
