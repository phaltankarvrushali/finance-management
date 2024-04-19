package models;

/* Class definition for Account */
public class Account {
    
	/* Private instance variables for the Account class */
    private String accountName; /* Stores the name of the account */
    private String accountType; /* Stores the type of the account */
    private double amount;      /* Stores the amount in the account */
    private String note;        /* Stores a note associated with the account */

    /* Constructor with parameters to initialize an Account object */
    public Account(String accountName, String accountType, double amount, String note) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.amount = amount;
        this.note = note; 
    }

    /* Default constructor */
    public Account() {
        /* This constructor is empty and is used to create an Account object without setting its properties immediately. */
    }

    /* toString method to return a string representation of the Account object */
    @Override
    public String toString() {
        
    	/* Formats and returns the account information as a string */
        return "Account{" +
                "accountName='" + accountName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }

    /* Getter for accountName */
    public String getAccountName() {
        return accountName;
    }

    /* Setter for accountName */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /* Getter for accountType */
    public String getAccountType() {
        return accountType;
    }

    /* Setter for accountType */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /* Getter for amount */
    public double getAmount() {
        return amount;
    }

    /* Setter for amount */
    public void setAmount(double amount) {
        this.amount = amount;
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
