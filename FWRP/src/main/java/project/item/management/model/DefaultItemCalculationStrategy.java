package project.item.management.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DefaultItemCalculationStrategy implements ItemCalculationStrategy{

	@Override
	public double calculateDiscountedPrice(Items item) {
        
		if (!"Donation".equals(item.getReason()) && (item.isSurplus() || isItemNearToExpire(item))) {
            return item.getPrice() * 0.5;
        } else {
            return -1; // Indicates no discount is applicable
        }
	}

	@Override
	public boolean isItemNearToExpire(Items item) {
        
		LocalDate expirationDate = LocalDate.parse(item.getExpDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return expirationDate.isBefore(LocalDate.now().plusDays(7));
	}

}
