package directories;

import java.util.ArrayList;

import models.Transaction;

/* Class definition for TransactionDirectory */
public class TransactionDirectory {

    /* Private ArrayList to store the list of Transactions */
    private ArrayList<Transaction> history;

    /* Constructor for TransactionDirectory */
    public TransactionDirectory() {
        this.history = new ArrayList<Transaction>(); /* Initializes the ArrayList */
    }

    /* Getter for history */
    public ArrayList<Transaction> getHistory() {
        return history;
    }

    /* Setter for history */
    public void setHistory(ArrayList<Transaction> history) {
        this.history = history;
    }

    /* Method to add a new Transaction to the directory */
    public Transaction addNewTransaction(Transaction transaction){
        history.add(transaction);
        return transaction;
    }

    /* Method to delete a Transaction from the directory by index */
    public void deleteTransaction(int index){
        history.remove(index);
    }

    /* Method to update a Transaction in the directory */
    public void updateTransaction(Transaction transaction, int index){
        history.set(index, transaction);
    }

    /* Method to delete all Transactions from the directory */
    public void deleteAll(){
        history.removeAll(history);
    }

    /* toString method to return a string representation of the TransactionDirectory object */
    @Override
    public String toString() {
        
    	/* Formats and returns the directory information as a string */
        return "TransactionDirectory{" + "history=" + history + '}';
    }

}
