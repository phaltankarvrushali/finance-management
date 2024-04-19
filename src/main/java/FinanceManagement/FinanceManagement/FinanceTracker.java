package FinanceManagement.FinanceManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Account;
import models.Transaction;
import models.User;
import directories.TransactionDirectory;
import directories.UserDirectory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* Class definition for FinanceTracker, extends Application for JavaFX */
public class FinanceTracker extends Application {

	/* Static variables for userDirectory and user */
	static UserDirectory userDirectory;
	static User user;

	/* Method to load transaction data into a TransactionDirectory */
	private static TransactionDirectory loadTransactionData() {

		TransactionDirectory transactionDirectory = new TransactionDirectory();

		/* Creating a new Account object with sample values */
		Account account2 = new Account("Checking Account", "Checking", 2500.0, "Paycheck deposit");

		/* Creating a SimpleDateFormat object to parse date strings */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		/* Array of sample transaction data */
		String[][] transactionData = { { "2023-01-01", "Food", "Bought groceries for the week", "85.23", "Expense" },
				{ "2023-01-02", "Transportation", "Filled up gas", "45.12", "Expense" },
				{ "2023-01-03", "Culture", "Went to a museum", "22.50", "Expense" },
				{ "2023-01-04", "Apparel", "Bought new shoes", "89.99", "Expense" },
				{ "2023-01-05", "Household", "Bought cleaning supplies", "25.00", "Expense" },
				{ "2023-01-06", "Social Life", "Had dinner with friends", "67.89", "Expense" },
				{ "2023-01-07", "Beauty", "Bought skincare products", "45.00", "Expense" },
				{ "2023-01-08", "Transportation", "Filled up gas", "50.00", "Expense" },
				{ "2023-01-09", "Food", "Bought groceries for the week", "92.75", "Expense" },
				{ "2023-01-10", "Culture", "Went to a concert", "75.00", "Expense" },
				{ "2023-01-11", "Apparel", "Bought new clothes", "100.00", "Expense" },
				{ "2023-01-12", "Social Life", "Had drinks with colleagues", "38.00", "Expense" },
				{ "2023-01-13", "Household", "Bought new curtains", "70.00", "Expense" },
				{ "2023-01-14", "Beauty", "Bought makeup products", "30.00", "Expense" },
				{ "2023-01-15", "Transportation", "Filled up gas", "55.00", "Expense" },
				{ "2023-01-16", "Food", "Bought groceries for the week", "78.32", "Expense" },
				{ "2023-01-17", "Culture", "Went to the movies", "20.00", "Expense" },
				{ "2023-01-18", "Apparel", "Bought new jeans", "50.00", "Expense" },
				{ "2023-01-19", "Social Life", "Had dinner with family", "112.45", "Expense" },
				{ "2023-01-20", "Beauty", "Bought hair care products", "25.00", "Expense" },
				{ "2023-01-21", "Transportation", "Filled up gas", "48.80", "Expense" },
				{ "2023-01-22", "Food", "Bought groceries for the week", "87.12", "Expense" },
				{ "2023-01-23", "Culture", "Went to an art gallery", "15.00", "Expense" } };

		/* Loop to create Transaction objects from the sample data */
		for (int i = 0; i < transactionData.length; i++) {
			try {
				/* Parsing the date string from the transaction data array */
				Date transactionDate = dateFormat.parse(transactionData[i][0]);

				/* Creating a new Transaction object and adding it to the directory */
				transactionDirectory.addNewTransaction(new Transaction(Double.parseDouble(transactionData[i][3]), // amount
						account2, // account
						transactionDate, // transactionDate
						transactionData[i][1], // category
						transactionData[i][2], // note
						transactionData[i][4] // transactionType
				));
			} catch (ParseException e) {
				System.out.println("Error parsing date");
				e.printStackTrace();
			}
		}
		return transactionDirectory;
	}

	/* Main method to start the application */
	public static void main(String[] args) {

		/* Initializing userDirectory */
		userDirectory = new UserDirectory();

		/* Creating and adding accounts to the accountList */
		ArrayList<Account> accountList = new ArrayList<>();

		/* account creation */
		Account account1 = new Account("Savings Account", "Savings", 5000.0, "Initial deposit");
		accountList.add(account1);

		Account account2 = new Account("Checking Account", "Checking", 2500.0, "Paycheck deposit");
		accountList.add(account2);

		Account account3 = new Account("Credit Card", "Credit", -1000.0, "Monthly payment");
		accountList.add(account3);

		Account account4 = new Account("Investment Account", "Investment", 10000.0, "Stock purchase");
		accountList.add(account4);

		Account account5 = new Account("Retirement Account", "Retirement", 50000.0, "401(k) contribution");
		accountList.add(account5);

		/* Creating a new User and loading it into the user directory */
		User user = new User("John Doe", 35, "Male", "123", "123", "New York City", 5551234567L, accountList,
				loadTransactionData());

		userDirectory.addNewUser(user);

		/* Launch the JavaFX application */
		launch();
	}

	/* Overridden start method from Application */
	@Override
	public void start(Stage stage) throws IOException {

		/* Method call to show the login page */
		showLogInController(stage);
	}

	/* Method to show the login controller */
	private void showLogInController(Stage stage) throws IOException {

		/* Setting up the FXMLLoader for the login page */
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		SignInController controller = new SignInController(userDirectory, user, stage);

		/* Configuring the scene and showing the stage */
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Financial Management System");
		stage.show();
	}

	/* Method to show the dashboard controller */
	private void showDashboard(Stage stage) throws IOException {

		/* Setting up the FXMLLoader for the dashboard page */
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboard.fxml"));
		DashboardController controller = new DashboardController(userDirectory, user, stage);

		/* Configuring the scene and showing the stage */
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Dashboard");
		stage.show();
	}
}