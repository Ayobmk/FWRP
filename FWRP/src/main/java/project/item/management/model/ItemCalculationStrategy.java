package project.item.management.model;

public interface ItemCalculationStrategy {
    
	double calculateDiscountedPrice(Items item);
    boolean isItemNearToExpire(Items item);
}
