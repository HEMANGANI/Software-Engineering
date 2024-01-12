import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.table.DefaultTableModel;

import static org.junit.Assert.assertEquals;

public class ExpenseTrackerTest {

    private ExpenseTrackerView view;
    private ExpenseTrackerApp app;

    @Before
    public void setup() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Serial");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Category");
        tableModel.addColumn("Date");
        view = new ExpenseTrackerView(tableModel);
        app = new ExpenseTrackerApp();
    }

    @Test
    public void testAddTransaction() {
        // Create a new transaction
        double amount = 100.0;
        String category = "Food";
        Transaction transaction = new Transaction(amount, category);

        // Add the transaction to the view
        view.addTransaction(transaction);

        // Get the transactions from the view
        java.util.List<Transaction> transactions = view.getTransactions();

        // Verify that the transaction was added
        assertEquals(1, transactions.size());
        assertEquals(amount, transactions.get(0).getAmount(), 0.001);
        assertEquals(category, transactions.get(0).getCategory());

        // Additional test cases for InputValidation class

        // Test valid amount (within range)
        assertTrue(InputValidation.isValidAmount(500.0));

        // Test amount greater than 1000
        assertFalse(InputValidation.isValidAmount(1001.0));

        // Test amount less than 0
        assertFalse(InputValidation.isValidAmount(-50.0));

        // Test invalid data type for amount
        assertFalse(InputValidation.isValidAmount(Double.NaN));

        // Test valid category
        assertTrue(InputValidation.isValidCategory("food"));

        // Test invalid category (case-insensitive)
        assertFalse(InputValidation.isValidCategory("InvalidCategory"));

        // Test invalid category (null)
        assertFalse(InputValidation.isValidCategory(null));

        // Test invalid category (empty string)
        assertFalse(InputValidation.isValidCategory(""));
    }
    
}