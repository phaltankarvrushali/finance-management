package directories;

import java.util.ArrayList;

import models.Account;
import models.User;

/* Class definition for UserDirectory */
public class UserDirectory {

    /* Private ArrayList to store the list of Users */
    private ArrayList<User> history;

    /* Constructor for UserDirectory */
    public UserDirectory() {
        this.history = new ArrayList<User>(); /* Initializes the ArrayList */
    }

    /* Getter for history */
    public ArrayList<User> getHistory() {
        return history;
    }

    /* Setter for history */
    public void setHistory(ArrayList<User> history) {
        this.history = history;
    }

    /* Method to add a new User to the directory */
    public User addNewUser(User user){
        history.add(user);
        return user;
    }

    /* Method to delete a User from the directory by index */
    public void deleteUser(int index){
        history.remove(index);
    }

    /* Method to update a User in the directory */
    public void updateUser(User user, int index){
        history.set(index, user);
    }

    /* Method to delete all Users from the directory */
    public void deleteAll(){
        history.removeAll(history);
    }

    /* Method to login a User */
    public User login(String emailId, String password){
        for(User user: history){
            
        	/* Checks if the emailId and password match a User in the directory */
            if(user.getEmailId().equals(emailId) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    /* Method to get an Account by its name from a specific User */
    public Account getAccount(String accountName, User user){
        for(Account account: user.getUserAccounts()){
            
        	/* Checks if the accountName matches an Account in the User's accounts */
            if(account.getAccountName().equals(accountName)){
                return account;
            }
        }
        return null;
    }

    /* toString method to return a string representation of the UserDirectory object */
    @Override
    public String toString() {
        
    	/* Formats and returns the directory information as a string */
        return "UserDirectory{" + "history=" + history + '}';
    }

}
