package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.text.NumberFormat;

import model.Transaction;

import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn, applyFilterBtn, removeFilterBtn;
  private JFormattedTextField amountField, minAmountField, maxAmountField;
  private JTextField categoryField;
  private JComboBox<String> filterTypeComboBox;
  private DefaultTableModel model;

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(700, 500); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    applyFilterBtn = new JButton("Apply Filter");
    removeFilterBtn = new JButton("Remove Filter");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    JLabel filterTypeLabel = new JLabel("Filter Type:");
    String[] filterTypes = {"Amount", "Category"};
    filterTypeComboBox = new JComboBox<>(filterTypes);

    JLabel minAmountLabel = new JLabel("Min Amount:");
    minAmountField = new JFormattedTextField(format);
    minAmountField.setColumns(10);

    JLabel maxAmountLabel = new JLabel("Max Amount:");
    maxAmountField = new JFormattedTextField(format);
    maxAmountField.setColumns(10);

    // Create table
    transactionsTable = new JTable(model); 
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel filterPanel = new JPanel();
    filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
    filterPanel.add(filterTypeLabel);
    filterPanel.add(filterTypeComboBox);
    filterPanel.add(minAmountLabel);
    filterPanel.add(minAmountField);
    filterPanel.add(maxAmountLabel);
    filterPanel.add(maxAmountField);
    filterPanel.add(applyFilterBtn);
    filterPanel.add(removeFilterBtn);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);

    JPanel combinedPanel = new JPanel(new GridLayout(2, 1));
    combinedPanel.add(filterPanel);
    combinedPanel.add(inputPanel);
  
    // Add panels to frame
    add(combinedPanel, BorderLayout.WEST);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
    
    // Set frame properties
    setSize(900, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void highlightTransactions(List<Transaction> filteredTransactions) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row >= 0 && row < transactionsTable.getRowCount() - 1) { // -1 to exclude the "Total" row
                double tableAmount = (double) transactionsTable.getValueAt(row, 1);
                String tableCategory = (String) transactionsTable.getValueAt(row, 2);
                String tableTimestamp = (String) transactionsTable.getValueAt(row, 3);

                boolean isFiltered = filteredTransactions.stream().anyMatch(t -> 
                    t.getAmount() == tableAmount && 
                    t.getCategory().equals(tableCategory) && 
                    t.getTimestamp().equals(tableTimestamp)
                );

                if (isFiltered) {
                    c.setBackground(new Color(173, 255, 168)); // Set background color to green
                } else {
                    c.setBackground(Color.white); // Set background color to default
                }
            } else {
                c.setBackground(Color.white);
            }

            return c;
        }
    });

    transactionsTable.repaint(); // Repaint the table to apply the highlighting
  }

  public void refreshTable(List<Transaction> transactions) {
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
  
      // Fire table update
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
      transactionsTable.updateUI();
  
  }  
  
  public JButton getApplyFilterBtn() {
      return applyFilterBtn;
  }

  public JButton getRemoveFilterBtn() {
      return removeFilterBtn;
  }

  public String getSelectedFilterType() {
      return (String) filterTypeComboBox.getSelectedItem();
  }

  public double getMinAmountField() {
      String text = minAmountField.getText().replace(",", "");
      return text.isEmpty() ? 0 : Double.parseDouble(text);
  }

  public double getMaxAmountField() {
      String text = maxAmountField.getText().replace(",", "");
      return text.isEmpty() ? 0 : Double.parseDouble(text);
  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  
  // Other view methods

  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    String text = amountField.getText().replace(",", "");
    if(text.isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(text);
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

}