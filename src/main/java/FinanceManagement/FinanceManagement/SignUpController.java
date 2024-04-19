package FinanceManagement.FinanceManagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Account;
import models.User;
import directories.TransactionDirectory;
import directories.UserDirectory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/* Class definition for SignUpController, implements Initializable for JavaFX controllers */
public class SignUpController implements Initializable {

	/* Instance variables for UserDirectory, User, and Stage */
	UserDirectory userDirectory;
	User user;
	Stage stage;

	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button btnSignIn, btnSignup;
	@FXML
	private RadioButton radiobtnfemale, radiobtnmale;
	@FXML
	private TextField txtAge, txtEmail, txtLocation, txtPhoneNumber, txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label valAge, valEmail, valGender, valLocation, valPassword, valUserName, valPhoneNumber;

	/* Constructor for SignUpController */
	public SignUpController(UserDirectory userDirectory, User user, Stage stage) {
		this.userDirectory = userDirectory;
		this.stage = stage;
		this.user = user;
	}

	/* Overridden initialize method from Initializable interface */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/* Configuration for radio btns and their toggle group */
		ToggleGroup toggleGroup = new ToggleGroup();
		radiobtnmale.setToggleGroup(toggleGroup);
		radiobtnfemale.setToggleGroup(toggleGroup);
		radiobtnmale.setSelected(true);

		/* Event handler for the Signup btn */
		btnSignup.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
				boolean val = saveUser(userDirectory, user, txtUsername.getText(), txtLocation.getText(),
						txtPhoneNumber.getText(), txtEmail.getText(), txtAge.getText(), txtPassword.getText(),
						toggleName);

			}
		});

		/* Event handler for the Signin btn */
		btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showDashboardLogin(stage);
			}
		});
	}

	/* Method to display the login view */
	private void showDashboardLogin(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		SignInController controller = new SignInController(userDirectory, user, stage);
		loader.setController(controller);

		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Sign Manager");
		stage.show();
	}

	/* Method to save user data */
	private boolean saveUser(UserDirectory userDirectory, User user, String userName, String Location,
			String Phonenumber, String email, String age, String password, String gender) {

		boolean val = checkValidation(valUserName, valAge, valGender, valEmail, valPassword, valLocation,
				valPhoneNumber);

		if (val == false) {
			return false;
		}

		Boolean val2 = checkUserPresent(userDirectory, email);
		if (val2 == true) {
			return false;
		}
		User user1 = new User(userName, Integer.parseInt(age), gender, email, password, Location,
				Long.parseLong(Phonenumber), new ArrayList<Account>(), new TransactionDirectory());
		userDirectory.addNewUser(user1);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("User added successfully");
		alert.showAndWait();

		txtPhoneNumber.clear();
		txtUsername.clear();
		txtPassword.clear();
		txtLocation.clear();
		txtEmail.clear();
		txtAge.clear();
		radiobtnmale.setSelected(true);
		valUserName.setText("*");
		valPassword.setText("*");
		valLocation.setText("*");
		valEmail.setText("*");
		valAge.setText("*");
		valGender.setText("*");
		valPhoneNumber.setText("*");
		return true;
	}

	/* Method to check input validation */
	private boolean checkValidation(Label valUserName, Label valAge, Label valGender, Label valEmail, Label valPassword,
			Label valLocation, Label valPhoneNumber) {

		boolean flag = true;
		if (txtUsername.getText().isEmpty()) {
			valUserName.setText("Please enter username");
			flag = false;
		} else {
			valUserName.setText("*");
		}

		if (txtPassword.getText().isEmpty()) {
			valPassword.setText("Please enter password");
			flag = false;
		}

		if (txtPassword.getText().length() < 8) {
			valPassword.setText("Password should be atleast 8 characters");
			flag = false;
		} else {
			valPassword.setText("*");
		}

		if (txtLocation.getText().isEmpty()) {
			valLocation.setText("Please enter location");
			flag = false;
		} else {
			valLocation.setText("*");
		}

		if (txtPhoneNumber.getText().isEmpty()) {
			valPhoneNumber.setText("Please enter phone number");
			flag = false;
		}

		if (txtPhoneNumber.getText().length() != 10) {
			valPhoneNumber.setText("Please enter valid phone number");
			flag = false;
		} else {
			try {
				Long val = Long.parseLong(txtPhoneNumber.getText().toString());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("PhoneNumber should be a number");
				alert.showAndWait();
				valPhoneNumber.setText("PhoneNumber should be a number");
				flag = false;
			}
			valLocation.setText("*");
		}

		if (txtEmail.getText().isEmpty()) {
			valEmail.setText("Please enter email");
			flag = false;
		}

		if (!txtEmail.getText().contains("@")) {
			valEmail.setText("Please enter valid email");
			flag = false;
		} else {
			valEmail.setText("*");
		}

		if (txtAge.getText().isEmpty() || txtAge.getText().length() > 3) {
			valAge.setText("Please enter  valid age its between 0-99");
			flag = false;
		} else {
			try {
				Integer val = Integer.parseInt(txtAge.getText().toString());
				if (val > 100) {
					valAge.setText("Please enter valid age");
					flag = false;
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					alert.setContentText("Please enter valid age between 0-100");
					alert.showAndWait();
				}

			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Age should be a number");
				alert.showAndWait();
				valAge.setText("Age should be a number");
				flag = false;
			}
			valAge.setText("*");
		}

		return flag;
	}

	/* Method to check if a user with the provided email already exists */
	public boolean checkUserPresent(UserDirectory userDirectory, String email) {

		for (User user : userDirectory.getHistory()) {
			if (user.getEmailId().equals(email)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("User already present");
				alert.showAndWait();
				return true;
			}
		}
		return false;
	}
}
