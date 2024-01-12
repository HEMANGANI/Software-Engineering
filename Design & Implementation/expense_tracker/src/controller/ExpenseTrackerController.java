package controller;

import view.ExpenseTrackerView;

import java.util.List;

import model.AmountFilter;
import model.CategoryFilter;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.TransactionFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
    setupEventHandlers();
  }

  private void setupEventHandlers() {
    view.getApplyFilterBtn().addActionListener(e -> applyFilter());
    view.getRemoveFilterBtn().addActionListener(e -> removeFilter());
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods

  public boolean applyFilter() {
        String filterType = view.getSelectedFilterType();
        List<Transaction> filteredTransactions;

        if ("Amount".equals(filterType)) {
            double minAmount = view.getMinAmountField();
            double maxAmount = view.getMaxAmountField();
            if (!InputValidation.isValidAmount(minAmount)) {
              return false;
            }
            if (!InputValidation.isValidAmount(maxAmount)) {
              return false;
            }
            TransactionFilter amountFilter = new AmountFilter(minAmount, maxAmount);
            filteredTransactions = amountFilter.filter(model.getTransactions());
        } else {
            String category = view.getCategoryField();
            if (!InputValidation.isValidCategory(category)) {
              return false;
            }
            TransactionFilter categoryFilter = new CategoryFilter(category);
            filteredTransactions = categoryFilter.filter(model.getTransactions());
        }

        view.highlightTransactions(filteredTransactions);
        return true;
    }
  
  public void removeFilter() {
      refresh();
  }
}