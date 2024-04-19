package FinanceManagement.FinanceManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Account;
import models.Transaction;
import models.User;
import directories.UserDirectory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/* Class definition for DashboardController, implements Initializable for JavaFX controllers */
public class DashboardController implements Initializable {

	/* Instance variables for UserDirectory, User, and Stage */
	public UserDirectory userDirectory;
	public User user;
	private Stage stage;

	/* FXML annotations for linking with JavaFX UI components */
	@FXML
	private Button transactionBtn, accountBtn, dashboardBtn, btnLogOut, statsBtn, budgetBtn, notesBtn;
	@FXML
	private TabPane tabPane;
	@FXML
	private Text totalBalanceTxt, totalExpenseTxt, userNameTxt;
	@FXML
	private BarChart<String, Number> barExpense, barIncome;
	@FXML
	private TableView<Transaction> tableTransactions;

	/* Constructor for DashboardController */
	public DashboardController(UserDirectory userDirectory, User user, Stage stage) {
		this.userDirectory = userDirectory;
		this.stage = stage;
		this.user = user;
	}

	/* Method to show expense chart */
	public void showExpenseChart() {

		Map<String, Double> expensesByCategory = new HashMap<>();
		expensesByCategory.put("Food", 0.0);
		expensesByCategory.put("Shopping", 0.0);
		expensesByCategory.put("Self Development", 0.0);
		expensesByCategory.put("Transportation", 0.0);
		expensesByCategory.put("Culture", 0.0);
		expensesByCategory.put("Household", 0.0);
		expensesByCategory.put("Apparel", 0.0);
		expensesByCategory.put("Bills", 0.0);
		expensesByCategory.put("Health", 0.0);
		expensesByCategory.put("Education", 0.0);
		expensesByCategory.put("Gift", 0.0);
		expensesByCategory.put("Other", 0.0);
		expensesByCategory.put("Phone", 0.0);
		expensesByCategory.put("Entertainment", 0.0);

		for (Transaction transaction : user.getTransactionDirectory().getHistory()) {
			System.out.println(transaction);
			if (Objects.equals(transaction.getTransactionType(), "Expense")) {
				expensesByCategory.put(transaction.getCategory(),
						expensesByCategory.get(transaction.getCategory()) + transaction.getAmount());
			}
		}

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		barExpense.setTitle("Expense Chart");

		xAxis.setLabel("Category");
		yAxis.setLabel("Amount");

		barExpense.setPrefSize(566, 350);
		barExpense.setLegendVisible(true);

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
			String category = entry.getKey();
			Double amount = entry.getValue();
			if (amount > 0) {
				XYChart.Data<String, Number> data = new XYChart.Data<>(category, amount);
				series.getData().add(data);
			}
		}
		series.setName("Expenses");

		ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList(series);
		barExpense.setData(data);
	}

	/* Method to show income chart */
	public void showIncomeChart() {

		Map<String, Double> incomeByAccountType = new HashMap<>();
		for (Account account : user.getUserAccounts()) {
			if (account.getAmount() > 0) {
				incomeByAccountType.put(account.getAccountType(),
						incomeByAccountType.getOrDefault(account.getAccountType(), 0.0) + account.getAmount());
			}
		}

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		barIncome.setTitle("Income Chart");

		xAxis.setLabel("Account Type");
		yAxis.setLabel("Amount");

		barIncome.setPrefSize(566, 350);
		barIncome.setLegendVisible(true);

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (Map.Entry<String, Double> entry : incomeByAccountType.entrySet()) {
			String accountType = entry.getKey();
			Double amount = entry.getValue();
			XYChart.Data<String, Number> data = new XYChart.Data<>(accountType, amount);
			series.getData().add(data);
		}
		series.setName("Income");

		ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList(series);
		barIncome.setData(data);
	}

	/* Method to show transactions */
	public void showTransactions() {
		
		System.out.println("I am in");
		TableColumn<Transaction, Date> dateCol = new TableColumn<>("Date");
		TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
		TableColumn<Transaction, String> noteCol = new TableColumn<>("Transactions Note");

		dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
		StringConverter<Date> dateConverter = new StringConverter<Date>() {

			@Override
			public String toString(Date date) {
				return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}

			@Override
			public Date fromString(String string) {
				try {
					return Date
							.from(LocalDate.parse(string, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
				} catch (DateTimeParseException e) {
					return null;
				}
			}
		};

		dateCol.setCellFactory(column -> new TableCell<Transaction, Date>() {

			@Override
			protected void updateItem(Date item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(dateConverter.toString(item));
				}
			}
		});

		List<Transaction> transactionList = user.getTransactionDirectory().getHistory();
		Comparator<Transaction> dateComparator = Comparator.comparing(Transaction::getTransactionDate).reversed();
		Collections.sort(transactionList, dateComparator);
		ObservableList<Transaction> data = FXCollections.observableArrayList(transactionList);
		
		tableTransactions.getColumns().add(dateCol);
		tableTransactions.getColumns().add(amountCol);
		tableTransactions.getColumns().add(noteCol);
		tableTransactions.setItems(data);
	}

	/* Method to display the transactions dashboard */
	private void showDashboardTransaction(Stage stage) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Transactions.fxml"));
		TransactionsController controller = new TransactionsController(userDirectory, user, stage);
		loader.setController(controller);

		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Transaction Manager");
		stage.show();
	}

	/* Method to display the accounts dashboard */
	private void showDashboardAccount(Stage stage) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));
		AccountController controller = new AccountController(userDirectory, user, stage, "designdynamos");
		loader.setController(controller);

		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Account Manager");
		stage.show();
	}

	/* Method to display the accounts dashboard */
	private void updateTotalBalance() {

		double totalBalance = 0;
		for (Account account : user.getUserAccounts()) {
			totalBalance += account.getAmount();
		}
		if (totalBalance > 0) {
			totalBalanceTxt.setText(String.valueOf(totalBalance));
		}
	}

	/* Method to update total expense */
	private void updateTotalExpense() {

		double totalExpense = 0;
		for (Transaction transaction : user.getTransactionDirectory().getHistory()) {
			if (transaction.getTransactionType().equals("Expense"))
				totalExpense += transaction.getAmount();
		}
		totalExpenseTxt.setText(String.valueOf(totalExpense));
	}

	/* Method to show login controller */
	private void showLogInController(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		SignInController controller = new SignInController(userDirectory, user, stage);
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Dashboard");
		stage.show();
	}

	/* Overridden initialize method from Initializable interface */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		updateTotalBalance();
		updateTotalExpense();
		showIncomeChart();
		showTransactions();
		showExpenseChart();
		userNameTxt.setText(user.getName());

		/* Event handler for the transaction btn */
		transactionBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showDashboardTransaction(stage);
			}
		});

		/* Event handler for the account btn */
		accountBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showDashboardAccount(stage);
			}
		});

		/* Event handler for the logout btn */
		btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Attempting to show the login controller, with exception handling */
				try {
					showLogInController(stage);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
