package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import model.ExpenseTrackerModel;
import model.ExpenseTrackerModelListener;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerView extends JFrame implements ExpenseTrackerModelListener {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  // private JTextField dateFilterField;
  private JTextField categoryFilterField;
  private JButton categoryFilterBtn;

  private JTextField amountFilterField;
  private JButton amountFilterBtn;

  private JButton undoButton;
  

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new ExpenseTrackerTableModel(columnNames, 0);

    
    // Create table
    transactionsTable = new JTable(model);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");

    JLabel amountFilterLabel = new JLabel("Filter by Amount:");
    amountFilterField = new JTextField(10);
    amountFilterBtn = new JButton("Filter by Amount");

    // Initialize the undo button
    undoButton = new JButton("Undo");

  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(amountFilterBtn);
    buttonPanel.add(categoryFilterBtn);
    buttonPanel.add(undoButton);
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(600, 400); // Increase the size for better visibility
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  
  }

  public DefaultTableModel getTableModel() {
    return model;
  }
    

  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
}


  public void addApplyAmountFilterListener(ActionListener listener) {
    amountFilterBtn.addActionListener(listener);
  }

  public double getAmountFilterInput() {
    String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
    try {
        return Double.parseDouble(input);
    } catch (NumberFormatException e) {
        // Handle parsing error here
        // You can show an error message or return a default value
        return 0.0; // Default value (or any other appropriate value)
    }
  }

  protected void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
  
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
      // Add total row
      Object[] totalRow = {"Total", null, null, totalCost};
      model.addRow(totalRow);

      // Clear the previous highlighting
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
      
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }


  protected void highlightRows(List<Integer> rowIndexes) {
      // The row indices are being used as hashcodes for the transactions.
      // The row index directly maps to the the transaction index in the list.
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                        boolean hasFocus, int row, int column) {
              Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	      if (isSelected) {
		  c.setBackground(Color.BLUE);
	      }
              else if (rowIndexes.contains(row)) {
                  c.setBackground(new Color(173, 255, 168)); // Light green
              } else {
                  c.setBackground(table.getBackground());
              }
              return c;
          }
      });

      transactionsTable.repaint();
  }

  public List<Transaction> getDisplayedTransactions() {
      // To support testability
      List<Transaction> displayedTransactions = new ArrayList<>();
      for (int i = 0; i < transactionsTable.getRowCount(); i++) {
	  TableCellRenderer renderer = transactionsTable.getCellRenderer(i, 0);
	  Component component = transactionsTable.prepareRenderer(renderer, i, 0);
	  
	  // Check if the row is highlighted based on the background color
	  if (component.getBackground().equals(new Color(173, 255, 168))) {
	      Object amountObj = transactionsTable.getValueAt(i, 1); // Assuming amount is in column 1
	      Object categoryObj = transactionsTable.getValueAt(i, 2); // Assuming category is in column 2
	      
	      if (amountObj != null && categoryObj != null) {
		  double amount = (double) amountObj;
		  String category = (String) categoryObj;
		  displayedTransactions.add(new Transaction(amount, category));
	      }
	  }
      }
      return displayedTransactions;
  }

  // Method to add action listener to the undo button
  public void addUndoButtonListener(ActionListener listener) {
      undoButton.addActionListener(listener);
  }

  // Method to get the selected row index from the table
  public int getSelectedRowIndex() {
      return transactionsTable.getSelectedRow();
  }

  public void update(ExpenseTrackerModel model) {
      refreshTable(model.getTransactions());
      if (model.getMatchedFilterIndices().size() > 0) {
	  highlightRows(model.getMatchedFilterIndices());
      }
  }
    
}
