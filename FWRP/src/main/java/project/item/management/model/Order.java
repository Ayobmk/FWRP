package project.item.management.model;

public class Order {
    private int id;
    private byte[] image;
    private String itemName;
    private String itemType;
    private String itemDescription;
    private String reason;
    private String expDate;
    private double price;
    private double discountedPrice;
    private String userName;
    private String userEmail;
    private String userType;
    private String base64Image;
	
    public Order() {
    }
    
    public Order(int id, byte[] image, String itemName, String itemType, String itemDescription, String reason,
			String expDate, double price, double discountedPrice, String userName, String userEmail, String userType) {
		super();
		this.id = id;
		this.image = image;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemDescription = itemDescription;
		this.reason = reason;
		this.expDate = expDate;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userType = userType;
	}
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
	public double getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	   public String getBase64Image() {
	        return base64Image;
	    }

	    public void setBase64Image(String base64Image) {
	        this.base64Image = base64Image;
	    }
}
