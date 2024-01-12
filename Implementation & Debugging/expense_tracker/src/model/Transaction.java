package model;

import controller.InputValidation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Transaction {

  public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
  //final means that the variable cannot be changed
  private final double amount;
  private final String category;
  private final String timestamp;

  public Transaction(double amount, String category) {
    // Since this is a public constructor, perform input validation
    // to guarantee that the amount and category are both valid
    if (InputValidation.isValidAmount(amount) == false) {
	throw new IllegalArgumentException("The amount is not valid.");
    }
    if (InputValidation.isValidCategory(category) == false) {
	throw new IllegalArgumentException("The category is not valid.");
    }
      
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  public double getAmount() {
    return amount;
  }

  //setter method is removed because we want to make the Transaction immutable
  // public void setAmount(double amount) {
  //   this.amount = amount;
  // }

  public String getCategory() {
    return category;
  }

  // public void setCategory(String category) {
  //   this.category = category; 
  // }
  
  public String getTimestamp() {
    return timestamp;
  }
  //private helper method to generate timestamp
  private String generateTimestamp() {
     return dateFormatter.format(new Date());
  }

}
