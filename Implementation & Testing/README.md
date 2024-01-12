# Solution files for hw2

# Undo Functionality Implementation

## Overview

This README documents the implementation of the undo functionality and the corresponding test suites within the application. The undo feature allows users to remove a selected transaction and is designed following the MVC architecture pattern with a focus on user experience.

## UI Design

- An `undoButton` is present in the UI, activated only when a transaction can be undone.
- The button's state (enabled/disabled) clearly indicates when undo functionality is available.

## MVC Architecture Integration

### Model

- `ExpenseTrackerModel` includes a history stack for undo operations and methods for transaction manipulation.
- Methods for adding and removing transactions have been implemented.

### View

- `TransactionListView` and `TotalCostView` update in real-time to reflect changes in the transaction list and total cost.

### Controller

- `ExpenseTrackerController` manages user interactions for transaction removal and undo operations.

## Implementation Details

- The `undoButton` widget is defined in the UI layout.
- The `undo` method in `ExpenseTrackerView` handles state restoration.
- `ExpenseTrackerController` listens for undo operations and updates the model and views.

## Testing

### Existing Test Cases

- Regression tests confirm that test cases pass without issues.

### New Test Cases

1. **Add Transaction**
   - **Steps**: Add a transaction with amount 50.00 and category "food".
   - **Expected Output**: Transaction is added, Total Cost is updated.

2. **Invalid Input Handling**
   - **Steps**: Try adding a transaction with invalid inputs.
   - **Expected Output**: Error displayed, no changes to transactions or Total Cost.

3. **Filter by Amount**
   - **Steps**: Add transactions, apply an amount filter.
   - **Expected Output**: Only transactions with matching amounts are shown.

4. **Filter by Category**
   - **Steps**: Add transactions, apply a category filter.
   - **Expected Output**: Only transactions with matching categories are shown.

5. **Undo Disallowed**
   - **Steps**: Attempt undo when no transactions are present.
   - **Expected Output**: Undo option is disabled or an error is thrown.

6. **Undo Allowed**
   - **Steps**: Add and then undo a transaction.
   - **Expected Output**: Transaction removed, Total Cost reflects this.

## Usage Instructions

- To undo a transaction, select it and press 'Undo' and confirm 'Yes' in the pop up message.