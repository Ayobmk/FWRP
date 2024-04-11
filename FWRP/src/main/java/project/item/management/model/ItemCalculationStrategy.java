package project.item.management.model;

/**
 * Defines the contract for item calculation strategies related to pricing and expiration checks.
 * Implementations of this interface will provide specific logic for calculating discounted prices
 * and determining if items are near to their expiration dates.
 */
public interface ItemCalculationStrategy {
    
    /**
     * Calculates the discounted price for a given item. The implementation can define
     */
	double calculateDiscountedPrice(Items item);
	
    /**
     * Determines whether an item is near to its expiration date. Implementations can define
     */
    boolean isItemNearToExpire(Items item);
}
