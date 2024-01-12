package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ExpenseTrackerModel {

  private  List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    return Collections.unmodifiableList(transactions);
  }

}