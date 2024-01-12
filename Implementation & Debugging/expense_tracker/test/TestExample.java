// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.text.ParseException;

import javax.swing.table.TableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    public void checkTransactionInView(Transaction transaction, int row) {
	TableModel viewModel = view.getTableModel();
	double amount = Double.parseDouble(viewModel.getValueAt(row, 1).toString());
	assertEquals(transaction.getAmount(), amount, 0.01);
        assertEquals(transaction.getCategory(), viewModel.getValueAt(row, 2));
	assertEquals(transaction.getTimestamp(), viewModel.getValueAt(row, 3));
    }

    public void checkTotalCostInView(double totalCost) {
	TableModel viewModel = view.getTableModel();
	// Retrieve the contents of the last row
	double totalCostInView = Double.parseDouble(viewModel.getValueAt(viewModel.getRowCount() - 1, 3).toString());
	assertEquals(totalCost, totalCostInView, 0.01); 
    }

    @Test
    public void testAddTransaction() {
	// This is focused on the Model
	//
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
	// This is focused on the Model
	//
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

    @Test
    public void testAddTransactionInView() {
	// This is new test case 1: For the View
	//
        // Pre-condition: List of transactions is empty
	TableModel viewModel = view.getTableModel();
        assertEquals(0, viewModel.getRowCount());
    
        // Perform the action: Add a transaction
	double amount = 50.0;
	String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains only
	//                 the added transaction and the total cost.
        assertEquals(2, viewModel.getRowCount());
    
        // Check the contents of the list
	Transaction firstTransaction = model.getTransactions().get(0);
	int firstRow = 0;
	checkTransactionInView(firstTransaction, firstRow);
	
	// Check the total amount
        checkTotalCostInView(50.0);
    }

    @Test
    public void testUndoAddTransactionInView() {
	// This is new test case 6: For the View
	//
        // Pre-condition: List of transactions is empty
	TableModel viewModel = view.getTableModel();
        assertEquals(0, viewModel.getRowCount());
    
        // Perform the action: Add and remove a transaction
	double amount = 50.0;
	String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Pre-condition: List of transactions contains only the
	//                added transaction and the total cost
        assertEquals(1, model.getTransactions().size());
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransactionInView(firstTransaction, 0);
	checkTotalCostInView(amount);
	
        // Perform the action: Remove the transaction
        boolean statusCode = controller.undoTransaction(0);
	assertTrue(statusCode);
	
        // Post-condition: List of transactions is empty
	assertEquals(1, viewModel.getRowCount());
    
        // Check the total cost after removing the transaction
        checkTotalCostInView(0.00);
    }

    //filter by amount
    @Test
    public void testFilterByAmount() {
	// This is new test case 3: For the View
	//
	// Setup
	double amountToFilterBy = 50.0;
        controller.addTransaction(amountToFilterBy, "food");
        controller.addTransaction(30.00, "entertainment");
        controller.addTransaction(40.00, "food");

	// Check pre-conditions
	assertEquals(3, view.getTableModel().getRowCount() - 1);

	// Call unit under test
	controller.setFilter(new AmountFilter(amountToFilterBy));
        controller.applyFilter();

	// Check the post-conditions
        List<Transaction> displayedTransactions = view.getDisplayedTransactions();
        assertEquals(1, displayedTransactions.size());
        assertEquals(amountToFilterBy, displayedTransactions.get(0).getAmount(), 0.01);
    } 


    //filter by category
    @Test
    public void testFilterByCategory() {
	// This is new test case 4: For the View
	//
	// Setup
	String categoryToFilterBy = "food";
        controller.addTransaction(50.00, categoryToFilterBy);
        controller.addTransaction(30.00, "entertainment");
        controller.addTransaction(40.00, categoryToFilterBy);
	
	// Check pre-conditions
	assertEquals(3, view.getTableModel().getRowCount() - 1);

	// Call the unit under test
	controller.setFilter(new CategoryFilter(categoryToFilterBy));
        controller.applyFilter();

	// Check the post-conditions
        List<Transaction> displayedTransactions = view.getDisplayedTransactions();
        assertEquals(2, displayedTransactions.size());
	for (Transaction currDisplayedTransaction : displayedTransactions) {
	    assertEquals(categoryToFilterBy, currDisplayedTransaction.getCategory());
	}
    }

    @Test
    public void testUndoNoTransactions() {
	// This is new test case 5: For the Controller
	//
	// Check pre-conditions
	assertEquals(0, model.getTransactions().size());

	// Call the unit under test
	boolean statusCode = controller.undoTransaction(0);

	// Check the post-conditions
	assertEquals(statusCode, false);
	assertEquals(0, model.getTransactions().size());
    }

    @Test
    public void testUndoAddTransaction() {
      // This is new test case 6: For the Controller
      //
      // Setup
      controller.addTransaction(50.00, "food");

      // Check pre-conditions
      assertEquals(1, model.getTransactions().size());

      // Call the unit under test
      boolean statusCode = controller.undoTransaction(0);
      
      // Check the post-conditions
      assertEquals(statusCode, true);
      
      // After undoing the transaction, check if the transaction is removed
      assertEquals(0, model.getTransactions().size());
      assertEquals(0.0, getTotalCost(), 0.01);
    }
    
    @Test
    public void testInvalidInputHandling() {
	// This is new test case 2: For the Controller
	//
	// Check pre-conditions
	assertEquals(0, model.getTransactions().size());
	assertEquals(0.00, getTotalCost(), 0.01);
	// Call the unit under test
        boolean didAddTransaction = controller.addTransaction(0.00, "InvalidCategory");
	// Check post-conditions (i.e. nothing changed)
	assertFalse(didAddTransaction);
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.00, getTotalCost(), 0.01);

	// See above for the pre-conditions
	//
	// Call the unit under test
	boolean didAddTransaction2 = controller.addTransaction(50.00, "");
	// Check the post-conditions
	assertFalse(didAddTransaction2);
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.00, getTotalCost(), 0.01);
    }

    @Test
    public void testRegisterFails() {
	// Perform setup and check pre-conditions
	ExpenseTrackerModel newModel = new ExpenseTrackerModel();
	ExpenseTrackerView newView = null;
	assertNotNull(newModel);
	assertEquals(newModel.numberOfListeners(), 0);
	assertNull(newView);
	
	// Call the unit under test
	boolean registered = newModel.register(newView);

	// Check the post-conditions
	assertFalse(registered);
	assertEquals(newModel.numberOfListeners(), 0);
    }

    @Test
    public void testRegisterSucceeds() {
	// Perform setup and check pre-conditions
	ExpenseTrackerModel newModel = new ExpenseTrackerModel();
	ExpenseTrackerView newView = new ExpenseTrackerView();
	assertNotNull(newModel);
	assertEquals(newModel.numberOfListeners(), 0);
	assertNotNull(newView);
	
	// Call the unit under test
        boolean registered = newModel.register(newView);

	// Check the post-conditions
	assertTrue(registered);
	assertEquals(newModel.numberOfListeners(), 1);
	assertTrue(newModel.containsListener(newView));
    }

    @After
    public void tearDown() {
        model = null;
        view = null;
        controller = null;
    }
    
}
