package project.item.management.model;

public class Items {
    private int id;
    private byte[] image; 
    private String itemName;
    private String itemType;
    private String itemDescription;
    private String reason;
    private String expDate;
    private double price;
    private boolean surplus;


    // Constructors
    public Items(int id, byte[] image, String itemName, String itemType, String itemDescription, String reason,
			String expDate, double price, boolean surplus) {
		super();
		this.id = id;
		this.image = image;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemDescription = itemDescription;
		this.reason = reason;
		this.expDate = expDate;
		this.price = price;
		this.surplus = surplus;

	}

	public Items(byte[] image, String itemName, String itemType, String itemDescription, String reason, String expDate, double price, boolean surplus) {
        this(0,image, itemName, itemType, itemDescription, reason, expDate, price, surplus); 
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public boolean isSurplus() {
		return surplus;
	}

	public void setSurplus(boolean surplus) {
		this.surplus = surplus;
	}
    
    
}
