# hw2

## Understandability: Documentation

### README Update:
We have updated the README file to document the new functionality added in Homework 2.
### Javadoc Generation:
We have generated Javadoc documentation for the source code and included it in the jdoc folder. This documentation provides detailed information about the classes and methods in the application.
### Git Log:
Our Git log contains incremental commit messages that describe the changes made at each stage of development. This helps in tracking the progress of the project.

## Modularity: Open-Closed Principle

### MVC Architecture:
We have implemented the Model-View-Controller (MVC) architecture pattern to improve modularity and separation of concerns in the application.
### Data Encapsulation:
We have applied data encapsulation to the list of transactions in the ExpenseTrackerModel class, ensuring that data is accessed and modified through appropriate methods.
### Immutability:
We have made the list of transactions immutable when accessed through the getter method in the ExpenseTrackerModel class.
### Transaction Class Updates:
We have made changes to the Transaction class to prevent external data modification, ensuring information hiding for declared fields.

## Extensibility: Strategy Design Pattern

### Filter Feature:
We have added a filter feature that allows users to filter transactions by attribute type, either by "amount" or "category."
### TransactionFilter Interface:
We introduced a TransactionFilter interface, defining the filter method to encapsulate filter algorithms.
### Filter Strategies:
We implemented two filter strategies: CategoryFilter and AmountFilter to filter transactions based on category or amount.
### Apply Filter Method:
We added an applyFilter method in the ExpenseTrackerController to apply the selected filter strategy and highlight matching transactions.
### Remove Filter Method:
We've added a removeFilter function that sets the cell renderer background color to the default background color for all cells in the table, effectively removing the highlighting.
### User Interface Update:
We updated the ExpenseTrackerView to allow users to specify filters and apply them to the transaction list. Matching rows are highlighted in green.

## Usability: Undo Functionality
### Undo Feature Design:
We designed an undo functionality that allows users to remove any transaction by selecting the row and undo the removal to reflect the changes in the Total Cost.