package model;

public class Donation {
    private int id;
    private int userId;
    private String foodItem;
    private String quantity;
    private String pickupLocation;
    private String status;

    // Constructor without ID (for new donations)
    public Donation(int userId, String foodItem, String quantity, String pickupLocation) {
        this.userId = userId;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.pickupLocation = pickupLocation;
        this.status = "pending";
    }

    // Constructor with ID (for reading from DB)
    public Donation(int id, int userId, String foodItem, String quantity, String pickupLocation, String status) {
        this.id = id;
        this.userId = userId;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.pickupLocation = pickupLocation;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getFoodItem() { return foodItem; }
    public String getQuantity() { return quantity; }
    public String getPickupLocation() { return pickupLocation; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setFoodItem(String foodItem) { this.foodItem = foodItem; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setStatus(String status) { this.status = status; }
}
