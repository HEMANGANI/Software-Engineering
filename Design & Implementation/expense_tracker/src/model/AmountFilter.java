package model;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {
    private final double minAmount;
    private final double maxAmount;

    public AmountFilter(double minAmount, double maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                           .filter(t -> t.getAmount() >= minAmount && t.getAmount() <= maxAmount)
                           .collect(Collectors.toList());
    }
}