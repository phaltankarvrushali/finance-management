package FinanceManagement.FinanceManagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import directories.TransactionDirectory;
import directories.UserDirectory;

/* Class definition for LoggedInController, implements Initializable for JavaFX controllers */
public class LoggedInController implements Initializable {

	/* Instance variables for UserDirectory and TransactionDirectory */
	private UserDirectory userDirectory;
	private TransactionDirectory transactionDirectory;

	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button btnLogout;
	@FXML
	private Label favouriteChannel;
	@FXML
	private Button financeTracker;
	@FXML
	private Label lblWelcome;

	/* Constructor for LoggedInController */
	public LoggedInController(UserDirectory userDirectory, TransactionDirectory transactionDirectory) {
		this.userDirectory = userDirectory;
		this.transactionDirectory = transactionDirectory;
	}

	/* Overridden initialize method from Initializable interface */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/* Configuration for btns and their event handlers */

		/* Event handler for the logout btn */
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// DbUtil.changeScene(event, "LoginPage.fxml", "log in!", null, null);
			}
		});

		/* Event handler for the finance tracker btn */
		financeTracker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// DbUtil.changeScene(event,"financetracker.fxml","log in",null,null);
			}
		});
	}

	/* Method to set user information on the screen */
	public void setUserInformation(String name, String favChannel) {
		lblWelcome.setText("Welcome " + name);
		favouriteChannel.setText("Your favorite Youtube channel is " + favChannel + "!");
	}
}
