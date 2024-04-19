package FinanceManagement.FinanceManagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Account;
import models.Transaction;
import models.User;
import directories.UserDirectory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Stack;

public class TransactionsController implements Initializable {

	/* Instance variables for UserDirectory, User, and Stage */
	UserDirectory userDirectory;
	Stage stage;
	User user;

	Stack<Transaction> recentTransactions = new Stack<>(); /* To keep track of recent transactions */
	
	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button btnExpenseCancel, btnIncomeCancel, btnIncomeSave, btnSaveExpense, btnTransferCancel,
			btnTransferSave;
	@FXML
	private Button dashboardBtn, btnTransaction, btnAccount, btnProfile, btnLogOut;
	@FXML
	private ComboBox<String> choiceBoxTransferTo, choiceBoxTransferFrom, choiceboxExpenseAccount,
			choiceboxExpenseCategory, choiceboxIncomeAcount, choiceboxIncomeCategory;
	@FXML
	private DatePicker dateExpense, dateIncome, dateTransfer;
	@FXML
	private TextField txtExpenseAmount, txtExpenseDescription, txtExpenseNote, txtIncomeAmount, txtIncomeDescription,
			txtIncomeNote, txtTransferAmount, txtTransferDescription, txtTransferNote;
	@FXML
	private Label valExpenseAccount, valExpenseAmount, valExpenseCategory, valExpenseDate, valExpenseDescription,
			valExpenseNote, valIncomeAccount, valIncomeAmount, valIncomeCategory, valIncomeDate, valIncomeDescription,
			valIncomeNote, valTransferAmount, valTransferDate, valTransferDescription, valTransferFrom, valTransferNote,
			valTransferTo;
	@FXML
	private Text userNameTxt;

	/* Constructor for TransactionsController */
	public TransactionsController(UserDirectory userDirectory, User user, Stage stage) {
		this.userDirectory = userDirectory;
		this.stage = stage;
		this.user = user;
	}

	/* Overridden initialize method from Initializable interface */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		userNameTxt.setText(user.getName());
		for (Account userAccount : user.getUserAccounts()) {
			choiceboxExpenseAccount.getItems().add(userAccount.getAccountName().toString());
			choiceboxIncomeAcount.getItems().add(userAccount.getAccountName().toString());
			choiceBoxTransferFrom.getItems().add(userAccount.getAccountName().toString());
			choiceBoxTransferTo.getItems().add(userAccount.getAccountName().toString());
		}

		choiceboxExpenseCategory.getItems().add("Food");
		choiceboxExpenseCategory.getItems().add("Transportation");
		choiceboxExpenseCategory.getItems().add("Entertainment");
		choiceboxExpenseCategory.getItems().add("Shopping");
		choiceboxExpenseCategory.getItems().add("Health");
		choiceboxExpenseCategory.getItems().add("Education");
		choiceboxExpenseCategory.getItems().add("Bills");
		choiceboxExpenseCategory.getItems().add("Other");
		choiceboxIncomeCategory.getItems().add("Salary");
		choiceboxIncomeCategory.getItems().add("Gift");
		choiceboxIncomeCategory.getItems().add("Other");

		/* Event handler for income save btn */
		btnIncomeSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean val = saveIncomeTransaction(userDirectory, user, dateIncome.getValue(),
						txtIncomeAmount.getText(), txtIncomeDescription.getText(), txtIncomeNote.getText(),
						choiceboxIncomeCategory.getValue(), choiceboxIncomeAcount.getValue());
			}
		});

		/* Event handler for expense save btn */
		btnSaveExpense.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean val = saveExpenseTransaction(userDirectory, user, dateExpense.getValue(),
						txtExpenseAmount.getText(), txtExpenseDescription.getText(), txtExpenseNote.getText(),
						choiceboxExpenseCategory.getValue(), choiceboxExpenseAccount.getValue());
			}
		});

		/* Event handler for transfer save btn */
		btnTransferSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean val = saveTransferTransaction(userDirectory, user, dateTransfer.getValue(),
						txtTransferAmount.getText(), txtTransferDescription.getText(), txtTransferNote.getText(),
						choiceBoxTransferFrom.getValue(), choiceBoxTransferTo.getValue());
			}
		});

		/* Event handler for the Account btn */
		btnAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));
					AccountController accountController = new AccountController(userDirectory, user, stage, "test");
					loader.setController(accountController);
					Parent root = loader.load();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		/* Event handler for the Dashboard btn */
		dashboardBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					showDashboard(stage);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		/* Event handler for the Logout btn */
		btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					showLogInController(stage);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		/* Event handler for the Income Cancel btn */
		btnIncomeCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtIncomeDescription.setText("");
				txtIncomeAmount.setText("");
				txtIncomeNote.setText("");
				choiceboxIncomeCategory.setValue(null);
				choiceboxIncomeAcount.setValue(null);
				dateIncome.setValue(null);
			}
		});

		/* Event handler for the Transfer Cancel btn */
		btnTransferCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtTransferDescription.setText("");
				txtTransferAmount.setText("");
				txtTransferNote.setText("");
				choiceBoxTransferFrom.setValue(null);
				choiceBoxTransferTo.setValue(null);
				dateTransfer.setValue(null);
			}
		});

	}

	/* Method to show the login controller */
	private void showLogInController(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		SignInController controller = new SignInController(userDirectory, user, stage);
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Financial Management System");
		stage.show();
	}

	/* Method to validate income transaction fields */
	private boolean checkvalidationIncome(Label valIncomeDescription, Label valIncomeAmount, Label valIncomeCategory,
			Label valIncomeAccount, Label valIncomeDate, Label valIncomeNote) {
		boolean flag = true;
		if (txtIncomeDescription.getText().isEmpty()) {
			valIncomeDescription.setText("Description is required");
			flag = false;
		} else {
			valIncomeDescription.setText("*");
		}

		if (txtIncomeAmount.getText().isEmpty()) {
			valIncomeAmount.setText("Amount is required");
			flag = false;
		} else {
			try {
				double val = Double.parseDouble(txtIncomeAmount.getText().toString());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Amount should be a number");
				alert.showAndWait();
				valIncomeAmount.setText("Amount should be a number");
				flag = false;
			}
			valIncomeAmount.setText("*");
		}

		if (choiceboxIncomeCategory.getValue() == null) {
			valIncomeCategory.setText("Category is required");
			flag = false;
		} else {
			valIncomeCategory.setText("*");
		}

		if (choiceboxIncomeAcount.getValue() == null) {
			valIncomeAccount.setText("Account is required");
			flag = false;
		} else {
			valIncomeAccount.setText("*");
		}

		if (dateIncome.getValue() == null) {
			valIncomeDate.setText("Date is required");
			flag = false;
		} else {
			valIncomeDate.setText("*");
		}

		if (txtIncomeNote.getText().isEmpty()) {
			valIncomeNote.setText("Note is required");
			flag = false;
		} else {
			valIncomeNote.setText("*");
		}
		return flag;
	}

	/* Method to save a transfer transaction */
	private boolean saveTransferTransaction(UserDirectory userDirectory, User user, LocalDate value, String text,
			String text1, String text2, String value1, String value2) {

		boolean val = checkValidationTransfer(valIncomeDescription, valIncomeAmount, valIncomeCategory,
				valIncomeAccount, valIncomeDate, valIncomeNote);
		if (!val) {
			return false;
		}
		if (userDirectory.getAccount(value1, user).getAmount() - Double.parseDouble(text) < 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText(
					"Insufficient Balance your balance is " + userDirectory.getAccount(value1, user).getAmount());
			alert.showAndWait();
			return false;
		}

		Date date1 = Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
		userDirectory.getAccount(value1, user)
				.setAmount(userDirectory.getAccount(value1, user).getAmount() - Double.parseDouble(text));
		userDirectory.getAccount(value2, user)
				.setAmount(userDirectory.getAccount(value2, user).getAmount() + Double.parseDouble(text));
		Transaction transaction = new Transaction(Double.parseDouble(text), userDirectory.getAccount(value1, user),
				date1, value2, text1, text2);
		Transaction transaction1 = new Transaction(Double.parseDouble(text), userDirectory.getAccount(value2, user),
				date1, value1, text1, text2);
		user.getTransactionDirectory().addNewTransaction(transaction);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Transaction Successful");
		alert.setContentText(
				"Transaction Successful New balance is" + userDirectory.getAccount(value1, user).getAmount());
		alert.showAndWait();

		txtTransferAmount.setText("");
		txtTransferDescription.setText("");
		txtTransferNote.setText("");
		choiceBoxTransferFrom.setValue("");
		choiceBoxTransferTo.setValue("");
		dateTransfer.setValue(null);
		valTransferAmount.setText("*");
		valTransferDescription.setText("*");
		valTransferNote.setText("*");
		valTransferFrom.setText("*");
		valTransferTo.setText("*");
		valTransferDate.setText("*");
		return true;
	}

	/* Method to validate transfer transaction fields */
	private boolean checkValidationTransfer(Label valIncomeDescription, Label valIncomeAmount, Label valIncomeCategory,
			Label valIncomeAccount, Label valIncomeDate, Label valIncomeNote) {
		boolean flag = true;
		if (txtTransferDescription.getText().isEmpty()) {
			valTransferDescription.setText("Description is required");
			flag = false;
		} else {
			valTransferDescription.setText("*");
		}

		if (txtTransferAmount.getText().isEmpty()) {
			valTransferAmount.setText("Amount is required");
			flag = false;
		} else {
			try {
				double val = Double.parseDouble(txtTransferAmount.getText().toString());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Amount should be a number");
				alert.showAndWait();
				valTransferAmount.setText("Amount should be a number");
				flag = false;
			}
			valTransferAmount.setText("*");
		}

		if (choiceBoxTransferFrom.getValue() == null) {
			valTransferFrom.setText("From is required");
			flag = false;
		} else {
			valTransferFrom.setText("*");
		}

		if (choiceBoxTransferTo.getValue() == null) {
			valTransferTo.setText("To is required");
			flag = false;
		} else {
			valTransferTo.setText("*");
		}

		if (dateTransfer.getValue() == null) {
			valTransferDate.setText("Date is required");

			flag = false;
		} else {
			valTransferDate.setText("*");
		}

		if (txtTransferNote.getText().isEmpty()) {
			valTransferNote.setText("Note is required");
			flag = false;
		} else {
			valTransferNote.setText("*");
		}
		return flag;
	}

	/* Method to save an income transaction */
	private boolean saveIncomeTransaction(UserDirectory userDirectory, User user, LocalDate date, String amount,
			String income, String incomedescription, String incomecategory, String accountname) {

		boolean val = checkvalidationIncome(valIncomeDescription, valIncomeAmount, valIncomeCategory, valIncomeAccount,
				valIncomeDate, valIncomeNote);
		if (!val) {
			return false;
		}

		Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		userDirectory.getAccount(accountname, user)
				.setAmount(userDirectory.getAccount(accountname, user).getAmount() + Double.parseDouble(amount));
		Transaction transaction = new Transaction(Double.parseDouble(amount),
				userDirectory.getAccount(accountname, user), date1, incomecategory, incomedescription, "Income");
		
		recentTransactions.push(transaction);
		user.getTransactionDirectory().addNewTransaction(transaction);
		System.out.println(transaction.toString());

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Transaction Successful");
		alert.setContentText(
				"Transaction Successful New balance in " + userDirectory.getAccount(accountname, user).getAccountName()
						+ " = " + userDirectory.getAccount(accountname, user).getAmount());
		alert.showAndWait();

		txtIncomeAmount.setText("");
		txtIncomeDescription.setText("");
		txtIncomeNote.setText("");
		choiceboxIncomeAcount.setValue("");
		choiceboxIncomeCategory.setValue("");
		dateIncome.setValue(null);
		valIncomeCategory.setText("*");
		valIncomeAccount.setText("*");
		valIncomeAmount.setText("*");
		valIncomeDate.setText("*");
		valIncomeDescription.setText("*");
		valIncomeNote.setText("*");

		return true;
	}

	/* Method to save an expense transaction */
	private boolean saveExpenseTransaction(UserDirectory userDirectory, User user, LocalDate date, String amount,
			String expense, String expensedescription, String expensecategory, String accountname) {

		boolean val = checkValdationExpense(valExpenseDescription, valExpenseAmount, valExpenseCategory,
				valExpenseAccount, valExpenseDate, valExpenseNote);
		if (val == false) {
			return false;
		}
		Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		if (userDirectory.getAccount(accountname, user).getAmount() - Double.parseDouble(amount) < 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Insufficient Balance");
			alert.setContentText("Insufficient Balance" + userDirectory.getAccount(accountname, user).getAmount());
			alert.showAndWait();
			return true;
		}
		userDirectory.getAccount(accountname, user)
				.setAmount(userDirectory.getAccount(accountname, user).getAmount() - Double.parseDouble(amount));
		Transaction transaction = new Transaction(Double.parseDouble(amount),
				userDirectory.getAccount(accountname, user), date1, expensecategory, expensedescription, "Expense");
		
		recentTransactions.push(transaction);
		user.getTransactionDirectory().addNewTransaction(transaction);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Transaction Successful");
		alert.setContentText(
				"Transaction Successful New balance is" + userDirectory.getAccount(accountname, user).getAmount());
		alert.showAndWait();

		txtExpenseAmount.setText("");
		txtExpenseDescription.setText("");
		txtExpenseNote.setText("");
		choiceboxExpenseAccount.setValue("");
		choiceboxExpenseCategory.setValue("");
		dateExpense.setValue(null);
		valExpenseAmount.setText("*");
		valExpenseAccount.setText("*");
		valExpenseCategory.setText("*");
		valExpenseDate.setText("*");
		valExpenseDescription.setText("*");
		valExpenseNote.setText("*");

		return true;
	}

	/* Method to show the dashboard */
	private void showDashboard(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboard.fxml"));
		DashboardController controller = new DashboardController(userDirectory, user, stage);
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Dashboard");
		stage.show();
	}

	/* Method to validate expense transaction fields */
	private boolean checkValdationExpense(Label valExpenseDescription, Label valExpenseAmount, Label valExpenseCategory,
			Label valExpenseAccount, Label valExpenseDate, Label valExpenseNote) {
		boolean flag = true;
		if (txtExpenseDescription.getText().isEmpty()) {
			valExpenseDescription.setText("Description is required");
			flag = false;
		} else {
			valExpenseDescription.setText("*");
		}

		if (txtExpenseAmount.getText().isEmpty()) {

			valExpenseAmount.setText("Amount is required");
			flag = false;
		} else {
			valExpenseAmount.setText("*");
		}

		if (choiceboxExpenseCategory.getValue() == null) {
			valExpenseCategory.setText("Category is required");
			flag = false;
		} else {
			valExpenseCategory.setText("*");
		}

		if (choiceboxExpenseAccount.getValue() == null) {
			valExpenseAccount.setText("Account is required");
			flag = false;
		} else {
			try {
				Double.parseDouble(txtExpenseAmount.getText());
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Amount should be a number");
				alert.setContentText("Amount should be a number");
				alert.showAndWait();
				valExpenseAmount.setText("Amount should be a number");
				flag = false;
			}
			valExpenseAccount.setText("*");
		}

		if (dateExpense.getValue() == null) {
			valExpenseDate.setText("Date is required");
			flag = false;
		} else {
			valExpenseDate.setText("*");
		}

		if (txtExpenseNote.getText().isEmpty()) {
			valExpenseNote.setText("Note is required");
			flag = false;
		} else {
			valExpenseNote.setText("*");
		}
		return flag;
	}
}
