package view;

import javax.swing.table.DefaultTableModel;


public class ExpenseTrackerTableModel extends DefaultTableModel
{
    public ExpenseTrackerTableModel(String[] columnNames, int rowCount) {
	super(columnNames, rowCount);
    }

    public boolean isCellEditable(int row, int column) {
	return false;
    }
}
