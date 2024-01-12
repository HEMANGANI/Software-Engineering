package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public Transaction getTransactionAt(int index) {
    if(index >= 0 && index < transactions.size()) {
        return transactions.get(index);
    }
    return null;
  }

  // Method to get transactions filtered by amount
  public List<Transaction> getTransactionsFilteredByAmount(double amount) {
      return transactions.stream()
                          .filter(t -> t.getAmount() == amount)
                          .collect(Collectors.toList());
  }

  // Method to get transactions filtered by category
  public List<Transaction> getTransactionsFilteredByCategory(String category) {
      return transactions.stream()
                          .filter(t -> category.equals(t.getCategory()))
                          .collect(Collectors.toList());
  }

}