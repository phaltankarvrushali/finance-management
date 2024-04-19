package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import directories.TransactionDirectory;

/* Class definition for User */
public class User {
    
	/* Private instance variables for the User class */
    private String name;          /* Stores the name of the user */
    private int age;              /* Stores the age of the user */
    private String gender;        /* Stores the gender of the user */
    private String emailId;       /* Stores the email ID of the user */
    private String password;      /* Stores the password of the user */
    private String location;      /* Stores the location of the user */
    private long phoneNumber;     /* Stores the phone number of the user */

    private ArrayList<Account> userAccounts; /* Stores the list of accounts associated with the user */

    private Map<String, Account> userAccountMap = new HashMap<>(); /* Map to store user to account details */
    
    private TransactionDirectory transactionDirectory; /* Stores the transaction directory for the user */

    /* Constructor with parameters to initialize a User object */
    public User(String name, int age, String gender, String emailId, String password, String location, long phoneNumber, ArrayList<Account> userAccounts, TransactionDirectory transactionDirectory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.emailId = emailId;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.userAccounts = userAccounts;
        this.transactionDirectory = transactionDirectory;
    }

    /* Getter for transactionDirectory */
    public TransactionDirectory getTransactionDirectory() {
        return transactionDirectory;
    }

    /* Setter for transactionDirectory */
    public void setTransactionDirectory(TransactionDirectory transactionDirectory) {
        this.transactionDirectory = transactionDirectory;
    }

    /* Default constructor */
    public User() {
        /* This constructor is empty and is used to create a User object without setting its properties immediately. */
    }

    /* toString method to return a string representation of the User object */
    @Override
    public String toString() {
        
    	/* Formats and returns the user information as a string */
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", userAccounts=" + userAccounts +
                ", transactionDirectory=" + transactionDirectory +
                '}';
    }

    /* Getter for userAccounts */
    public ArrayList<Account> getUserAccounts() {
        return userAccounts;
    }

    /* Setter for userAccounts */
    public void setUserAccounts(ArrayList<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    /* Getter for name */
    public String getName() {
        return name;
    }

    /* Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /* Getter for age */
    public int getAge() {
        return age;
    }

    /* Setter for age */
    public void setAge(int age) {
        this.age = age;
    }

    /* Getter for gender */
    public String getGender() {
        return gender;
    }

    /* Setter for gender */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /* Getter for emailId */
    public String getEmailId() {
        return emailId;
    }

    /* Setter for emailId */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /* Getter for password */
    public String getPassword() {
        return password;
    }

    /* Setter for password */
    public void setPassword(String password) {
        this.password = password;
    }

    /* Getter for location */
    public String getLocation() {
        return location;
    }

    /* Setter for location */
    public void setLocation(String location) {
        this.location = location;
    }

    /* Getter for phoneNumber */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /* Setter for phoneNumber */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public Map<String, Account> getUserAccountMap() {
		return userAccountMap;
	}

	public void setUserAccountMap(Map<String, Account> userAccountMap) {
		this.userAccountMap = userAccountMap;
	}

}
