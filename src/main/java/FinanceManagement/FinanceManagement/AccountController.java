package FinanceManagement.FinanceManagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Account;
import models.User;
import directories.UserDirectory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* Class definition for AccountController, implements Initializable for JavaFX controllers */
public class AccountController implements Initializable {

	/* Instance variables for UserDirectory, User, and Stage */
	UserDirectory userDirectory;
	User user;
	Stage stage;

	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button btnAccount, btnAdd, btnCancel, btnLogout, btnTansaction, dashboardBtn, btnLogOut;
	@FXML
	private ChoiceBox<String> choiceboxType;
	@FXML
	private TextField txtAccountName, txtAmount, txtNote;
	@FXML
	private Text accountNameVal, accountTypeVal, amountVal, note, userNameTxt;

	/* Constructor for AccountController */
	public AccountController(UserDirectory userDirectory, User user, Stage stage, String designdynamos) {
		this.stage = stage;
		this.userDirectory = userDirectory;
		this.user = user;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		/* Setting initial values and event handlers for the UI components */
		userNameTxt.setText(user.getName());
		choiceboxType.getItems().addAll("Savings", "Checking", "Investment", "Retirement", "Credit Card");

		/* Event handler for the Add btn */
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Boolean val = SaveUser(txtAccountName.getText(), choiceboxType.getValue(), txtAmount.getText(),
						txtNote.getText());
			}
		});

		/* Event handler for the Transaction btn */
		btnTansaction.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Transactions.fxml"));
					loader.setController(new TransactionsController(userDirectory, user, stage));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
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

		/* Event handler for the Cancel btn */
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtAmount.setText("");
				txtAccountName.setText("");
				txtNote.setText("");
				choiceboxType.setValue("");
			}
		});
	}

	/* Methods for showing different views like Login and Dashboard */
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

	/* Method to save user account details */
	private Boolean SaveUser(String AccounName, String AccountType, String Amount, String Note) {

		boolean val = checkvalidation(accountNameVal, accountTypeVal, amountVal, note);
		if (val) {
			Account account = new Account(AccounName, AccountType, Double.parseDouble(Amount), Note);
			user.getUserAccounts().add(account);

			txtAccountName.setText("");
			choiceboxType.setValue("");
			txtAmount.setText("");
			txtNote.setText("");
			accountNameVal.setText("*");
			accountTypeVal.setText("*");
			amountVal.setText("*");
			note.setText("*");

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Account");
			alert.setHeaderText("Account Added");
			alert.setContentText("Account Added Successfully");
			alert.showAndWait();
			return true;
		} else {
			return false;
		}
	}

	/* Method to check input validation */
	private boolean checkvalidation(Text accountNameVal, Text accountTypeVal, Text amountVal, Text note) {

		boolean val = true;
		if (txtAccountName.getText().isEmpty()) {
			accountNameVal.setText("Account Name is required");
			val = false;
		} else {
			accountNameVal.setText("*");
		}

		if (choiceboxType.getValue() == null) {
			accountTypeVal.setText("Account Type is required");
			val = false;
		} else {
			accountTypeVal.setText("*");
		}

		if (txtAmount.getText().isEmpty()) {
			amountVal.setText("Amount is required");
			val = false;
		} else {
			try {
				double value = Double.parseDouble(txtAmount.getText().toString());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Amount should be a number");
				alert.showAndWait();
				amountVal.setText("Amount should be a number");
				val = false;
			}
			amountVal.setText("*");
		}

		if (txtNote.getText().isEmpty()) {
			note.setText("Note is required");
			val = false;
		} else {
			note.setText("*");
		}

		return val;
	}
}