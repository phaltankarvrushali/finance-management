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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import directories.UserDirectory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* Class definition for SignInController, implements Initializable for JavaFX controllers */
public class SignInController implements Initializable {

	/* Instance variables for User, UserDirectory, and Stage */
	User user;
	private UserDirectory userDirectory;
	private Stage stage;

	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button btnLogin;
	@FXML
	private TextField passwordTf;
	@FXML
	private TextField usernameTf;
	@FXML
	private Button signupBtn;

	/* Constructor for SignInController */
	public SignInController(UserDirectory userDirectory, User user, Stage stage) {
		this.userDirectory = userDirectory;
		this.stage = stage;
		this.user = user;
	}

	/* Method to display the dashboard */
	private void showDashboard(Stage stage, User user) throws IOException {
		this.user = user;

		/* Loading and displaying the MainDashboard view */
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboard.fxml"));
			DashboardController controller = new DashboardController(userDirectory, user, stage);
			loader.setController(controller);
			Parent root = loader.load();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Dashboard");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Overridden initialize method from Initializable interface */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/* Event handler for the Login btn */
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (usernameTf.getText().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Username is empty");
					alert.setContentText("Please enter a username");
					alert.showAndWait();
					return;
				}

				if (passwordTf.getText().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Password is empty");
					alert.setContentText("Please enter a password");
					alert.showAndWait();
					return;
				}

				User user = userDirectory.login(usernameTf.getText(), passwordTf.getText());

				if (user == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid Credentials");
					alert.setContentText("Please enter valid credentials");
					alert.showAndWait();
					return;
				}

				if (user != null) {
					try {
						showDashboard(stage, user);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		});

		/* Event handler for the Sign Up btn */
		signupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showDashboardSignUp(stage);
			}
		});
	}

	/* Method to display the sign-up view */
	private void showDashboardSignUp(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpPage.fxml"));
		SignUpController controller = new SignUpController(userDirectory, user, stage);
		loader.setController(controller);
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Sign Up");
		stage.show();
	}
}