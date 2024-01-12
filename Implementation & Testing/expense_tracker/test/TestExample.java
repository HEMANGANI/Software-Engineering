// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;
import view.ExpenseTrackerView;

public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    public void checkTransaction(double amount, String category, Transaction transaction) {
	assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }

    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
	double amount = 50.0;
	String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains only
	//                 the added transaction	
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);
	
	// Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
	double amount = 50.0;
	String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains only
	//                the added transaction
        assertEquals(1, model.getTransactions().size());
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);

	assertEquals(amount, getTotalCost(), 0.01);
	
	// Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }
    
    //------------------
    @Test //case 1: Add transaction succeeds (View/Controller)
    public void addTransaction_ShouldSucceed_WhenValidInput() {
        // Setup
        double amount = 100.0;
        String category = "food";
        int initialSize = model.getTransactions().size();

        // Execution
        boolean result = controller.addTransaction(amount, category);

        // Validation
        //assertTrue(result);
        assertEquals(initialSize + 1, model.getTransactions().size());
        Transaction transaction = model.getTransactions().get(initialSize);
        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
    }

    @Test //(expected = IllegalArgumentException.class) //case 2: Add transaction fails (Model/View/Controller)
    public void addTransaction_ShouldFail_WhenInvalidInput() {
        // Setup
        double invalidAmount = -50.0;
        String invalidCategory = "";

        // Execution
        controller.addTransaction(invalidAmount, invalidCategory);
    }

    @Test //case 3: Amount filter (Model/View/Controller)
    public void filterTransactions_ByAmount_ShouldReturnFilteredResults() {
        // Setup
        controller.addTransaction(100.0, "food");
        controller.addTransaction(200.0, "other");
        controller.setFilter(new AmountFilter(100.0));

        // Execution
        List<Transaction> filteredTransactions = model.getTransactionsFilteredByAmount(100);

        // Validation
        assertNotNull(filteredTransactions);
        assertTrue(!filteredTransactions.isEmpty());
        assertTrue(filteredTransactions.stream().allMatch(t -> t.getAmount() == 100.0));
    }

    @Test //case 4: Category filter (Model/View/Controller)
    public void filterTransactions_ByCategory_ShouldReturnFilteredResults() {
        // Setup
        controller.addTransaction(100.0, "food");
        controller.addTransaction(200.0, "other");
        controller.setFilter(new CategoryFilter("food"));

        // Execution
        List<Transaction> filteredTransactions = model.getTransactionsFilteredByCategory("food");

        // Validation
        assertNotNull(filteredTransactions);
        assertTrue(!filteredTransactions.isEmpty());
        assertTrue(filteredTransactions.stream().allMatch(t -> "food".equals(t.getCategory())));
    }

    @Test //(expected = IllegalStateException.class) //case 5: Undo is disallowed (View/Controller)
    public void undoTransaction_ShouldFail_WhenNoTransactions() {
        // Setup
        assertTrue(model.getTransactions().isEmpty());
        // Execution
        controller.removeSelectedTransaction(-1);
    }

    @Test //case 6: Undo is allowed (View/Controller)
    public void undoTransaction_ShouldSucceed_WhenTransactionsExist() {
        // Setup
        controller.addTransaction(100.0, "food");
        // Execution
        controller.removeSelectedTransaction(0);
        // Validation
        assertEquals(0, model.getTransactions().size());
    }
    //------------------

}