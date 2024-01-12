package controller;
import view.ExpenseTrackerView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /** 
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class 
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    view.addRemoveTransactionListener(e -> removeSelectedTransaction(-1));
  }

  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
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

  public void applyFilter() {
    //null check for filter
    if(filter!=null){
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      view.highlightRows(rowIndexes);
    }
    else{
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();
    }
  }

  public boolean removeSelectedTransaction(int n) {
    int selectedRow = view.getTransactionsTable().getSelectedRow();
    if(n!=-1)
    selectedRow=n;
    if (selectedRow >= 0) { 
      int confirm = JOptionPane.showConfirmDialog(view, 
                      "Are you sure you want to Undo the selected transaction?", 
                      "Undo Transaction", 
                      JOptionPane.YES_NO_OPTION);
      
      if (confirm == JOptionPane.YES_OPTION) {
        int modelRow = view.getTransactionsTable().convertRowIndexToModel(selectedRow);
        Transaction transactionToRemove = model.getTransactionAt(modelRow);
        if (transactionToRemove != null) {
          model.removeTransaction(transactionToRemove); 
          view.getTableModel().removeRow(modelRow); 
          refresh(); //update total cost
          return true;
        }
      }
    } else {
      JOptionPane.showMessageDialog(view, "No transaction selected for Undo.");
    }
    return false;
  }

}
