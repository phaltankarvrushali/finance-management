package models;

import java.util.Date;

/* Class definition for Transaction */
public class Transaction {
    
	/* Private instance variables for the Transaction class */
    private double amount;          /* Stores the transaction amount */
    private Account account;        /* Stores the associated account */
    Date transactionDate;           /* Stores the date of the transaction */
    private String category;        /* Stores the category of the transaction */
    private String note;            /* Stores a note associated with the transaction */
    private String transactionType; /* Stores the type of the transaction (e.g., income, expense) */

    /* Constructor with parameters to initialize a Transaction object */
    public Transaction(double amount, Account account, Date transactionDate, String category, String note, String transactionType) {
        this.amount = amount;
        this.account = account;
        this.transactionDate = transactionDate;
        this.category = category;
        this.note = note;
        this.transactionType = transactionType;
    }

    /* Getter for transactionType */
    public String getTransactionType() {
        return transactionType;
    }

    /* Setter for transactionType */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /* Default constructor */
    public Transaction() {
        /* This constructor is empty and is used to create a Transaction object without setting its properties immediately. */
    }

    /* toString method to return a string representation of the Transaction object */
    @Override
    public String toString() {
        
    	/* Formats and returns the transaction information as a string */
        return "Transaction{" +
                "amount=" + amount +
                ", account=" + account +
                ", transactionDate=" + transactionDate +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }

    /* Getter for amount */
    public double getAmount() {
        return amount;
    }

    /* Setter for amount */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /* Getter for account */
    public Account getAccount() {
        return account;
    }

    /* Setter for account */
    public void setAccount(Account account) {
        this.account = account;
    }

    /* Getter for transactionDate */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /* Setter for transactionDate */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /* Getter for category */
    public String getCategory() {
        return category;
    }

    /* Setter for category */
    public void setCategory(String category) {
        this.category = category;
    }

    /* Getter for note */
    public String getNote() {
        return note;
    }

    /* Setter for note */
    public void setNote(String note) {
        this.note = note;
    }
}
