package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

public class AmountFilter implements TransactionFilter{
    private double amountFilter;

    public AmountFilter(double amountFilter){
        // Since the AmountFilter constructor is public, 
        // the input validation needs to be performed again.
        if(!InputValidation.isValidAmount(amountFilter)){
            throw new IllegalArgumentException("Invalid amount filter");
        } else {
            this.amountFilter = amountFilter;
        }
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactions){
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            // Your solution could use a different comparison here.
            if(transaction.getAmount() == amountFilter){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    
}
