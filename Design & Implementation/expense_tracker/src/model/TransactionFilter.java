package model;

import java.util.List;

public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);
}