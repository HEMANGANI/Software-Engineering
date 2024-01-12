package model;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter {
    private final String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                           .filter(t -> t.getCategory().equalsIgnoreCase(category))
                           .collect(Collectors.toList());
    }
}