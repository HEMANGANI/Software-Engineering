/**
 * This class provides utility methods for input validation related to expenses.
 */

public class InputValidation {
    public static boolean isValidAmount(double input) {
        try {
            double amount = input;
            return amount > 0 && amount < 1000;
        } catch (NumberFormatException e) {
            return false; // Invalid number format
        }
    }
    
    public static boolean isValidCategory(String category) {
        String[] validCategories = {"food", "travel", "bills", "entertainment", "other"};
        for (String validCategory : validCategories) {
            if (validCategory.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false; // Invalid category
    }
}
