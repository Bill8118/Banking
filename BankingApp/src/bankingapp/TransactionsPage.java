package bankingapp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

@SuppressWarnings("serial")
public class TransactionsPage extends JFrame implements ActionListener{

	JLabel depositLabel = new JLabel();
	JLabel savingsLabel = new JLabel("Savings Deposit   ");
	JTextField savingsField = new JTextField(5);
	JButton savingsDeposit = new JButton("Confirm");
	JLabel currentLabel = new JLabel("Current Deposit    ");
	JTextField currentField = new JTextField(5);
	JButton currentDeposit = new JButton("Confirm");
	JLabel withdrawLabel = new JLabel();
	JLabel withdrawSavingsLabel = new JLabel("Savings Withdraw");
	JTextField withdrawSavingsField = new JTextField(5);
	JButton savingsWithdraw = new JButton("Confirm");
	JLabel withdrawCurrentLabel = new JLabel("Current Withdraw ");
	JTextField withdrawCurrentField = new JTextField(5);
	JButton currentWithdraw = new JButton("Confirm");
	JLabel transferOptions = new JLabel();
	JLabel fromSavingsLabel = new JLabel("Savings to Current");
	JTextField fromSavingsField = new JTextField(5);
	JButton fromSavings = new JButton("Confirm");
	JLabel fromCurrentLabel = new JLabel("Current to Savings");
	JTextField fromCurrentField = new JTextField(5);
	JButton fromCurrent = new JButton("Confirm");
	JLabel statementOption = new JLabel();
	JButton statement = new JButton("Account Details");
	JButton logOut = new JButton("Log out");
	JLabel blank = new JLabel("  ");
	
	private static float depositAmount;
	private static float withdrawAmount;
	private static float transferAmount;
	private static float tempValue;
	private static boolean validate = false;

	TransactionsPage(String str)
	{
		super(str);
		FlowLayout layout = new FlowLayout(FlowLayout.RIGHT,26,20);
		this.setLayout(layout);
		this.setSize(350, 550);
		this.setLocation(550, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		depositLabel.setFont(new Font("Deposit Options",Font.ITALIC + Font.BOLD,13));
		depositLabel.setForeground(Color.GREEN);
		depositLabel.setText("Deposit Options                           ");

		withdrawLabel.setFont(new Font("Withdraw Options",Font.ITALIC + Font.BOLD,13));
		withdrawLabel.setForeground(Color.RED);
		withdrawLabel.setText("Withdraw Options                         ");

		transferOptions.setFont(new Font("Transfer Options",Font.ITALIC + Font.BOLD,13));
		transferOptions.setForeground(Color.BLUE);
		transferOptions.setText("Transfer Options                         ");

		statementOption.setFont(new Font("Statement", Font.ITALIC + Font.BOLD,13));
		statementOption.setForeground(Color.MAGENTA);
		statementOption.setText("Click below for account details and balances   ");

		this.add(depositLabel);				this.add(savingsLabel);				this.add(savingsField);
		this.add(savingsDeposit);			this.add(currentLabel);				this.add(currentField);
		this.add(currentDeposit);			this.add(withdrawLabel);			this.add(withdrawSavingsLabel);
		this.add(withdrawSavingsField);		this.add(savingsWithdraw);			this.add(withdrawCurrentLabel);
		this.add(withdrawCurrentField);		this.add(currentWithdraw);			this.add(transferOptions);
		this.add(fromSavingsLabel);			this.add(fromSavingsField);			this.add(fromSavings);
		this.add(fromCurrentLabel);			this.add(fromCurrentField);			this.add(fromCurrent);
		this.add(statementOption);			this.add(statement);				this.add(logOut);
		this.add(blank);

		savingsDeposit.addActionListener(this);
		currentDeposit.addActionListener(this);
		savingsWithdraw.addActionListener(this);
		currentWithdraw.addActionListener(this);
		fromSavings.addActionListener(this);
		fromCurrent.addActionListener(this);
		statement.addActionListener(this);
		logOut.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();

		if(target == savingsDeposit) {
			if(savingsField.getText().isEmpty() == false) {
				try {
					depositAmount = Float.parseFloat(savingsField.getText());
					savingsField.setText(null);
					validate = true;
				}
				catch(NumberFormatException nfe) {
					JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Input Error");
					savingsField.setText(null);
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					validate = false;
				}
			}
			else{
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET savings_balance = savings_balance + ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					stmt.setFloat(1, depositAmount);
					stmt.setInt(2, NewUserPage.accountNo);

					stmt.executeUpdate();
					JOptionPane optionPane = new JOptionPane("Deposit Successfull!", JOptionPane.INFORMATION_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Transaction Confirmation");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					connect.close();
				
				}
				catch(SQLException ex){ 
					System.err.println("Got an SQL exception depositing to savings account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == currentDeposit) {
			if(currentField.getText().isEmpty() == false) {
				try {
					depositAmount = Float.parseFloat(currentField.getText());
					currentField.setText(null);
					validate = true;
				}
				catch(NumberFormatException nfe) {
					JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Input Error");
					currentField.setText(null);
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					validate = false;
				}
			}
			else {
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET current_balance = current_balance + ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					stmt.setFloat(1, depositAmount);
					stmt.setInt(2, NewUserPage.accountNo);

					stmt.executeUpdate();
					JOptionPane optionPane = new JOptionPane("Deposit Successfull!", JOptionPane.INFORMATION_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Transaction Confirmation");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					connect.close();
				}catch(SQLException ex){ 
					System.err.println("Got an SQL exception depositing to current account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == savingsWithdraw) {
			if(withdrawSavingsField.getText().isEmpty() == false) {
				try {
					withdrawAmount = Float.parseFloat(withdrawSavingsField.getText());
					withdrawSavingsField.setText(null);
					validate = true;
				}
				catch(NumberFormatException nfe){
						JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Input Error");
						withdrawSavingsField.setText(null);
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
				}
				try{
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.account_table "
													+"WHERE account_number = '"+NewUserPage.accountNo+"'");		
					if(rs.next())

						tempValue = rs.getFloat(8);
					
					connect.close();
				}catch(SQLException ex){ 
					System.out.println("Got an SQL exception connecting to savings account");
				}
			}
			else {
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET savings_balance = savings_balance - ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					if(tempValue > 0 && withdrawAmount <= tempValue) {
						stmt.setFloat(1, withdrawAmount);
						stmt.setInt(2, NewUserPage.accountNo);

						stmt.executeUpdate();
						JOptionPane optionPane = new JOptionPane("Withdraw Successfull!", JOptionPane.INFORMATION_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Transaction Confirmation");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						connect.close();	
					}
					else {
						JOptionPane optionPane = new JOptionPane("Insufficient Funds available", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Withdraw Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
					}
				}catch(Exception ex){ 
					System.err.println("Got an exception withdrawing from savings account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == currentWithdraw) {
			if(withdrawCurrentField.getText().isEmpty() == false) {
				try {
					withdrawAmount = Float.parseFloat(withdrawCurrentField.getText());
					withdrawCurrentField.setText(null);
					validate = true;
				}
				catch(NumberFormatException nfe) {
					JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Input Error");
					withdrawCurrentField.setText(null);
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					validate = false;
				}
				try{
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.account_table "
													+ "Where account_number = '"+NewUserPage.accountNo+"'");		
					if(rs.next())

						tempValue = rs.getFloat(9);
						
					connect.close();
				}catch(SQLException ex){ 
					System.out.println("Got an SQL exception connecting to current account!");
				}
			}
			else {
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET current_balance = current_balance - ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					if(tempValue > 0 && withdrawAmount <= tempValue ) {
						stmt.setFloat(1, withdrawAmount);
						stmt.setInt(2, NewUserPage.accountNo);
						stmt.executeUpdate();
						JOptionPane optionPane = new JOptionPane("Withdraw Successfull!", JOptionPane.INFORMATION_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Transaction Confirmation");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						connect.close();
					}
					else {
						JOptionPane optionPane = new JOptionPane("Insufficient Funds available", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Withdraw Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
					}
					
				}catch(Exception ex){ 
					System.err.println("Got an SQL exception withdrawing from current account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == fromSavings) {
			if(fromSavingsField.getText().isEmpty() == false) {
				try {
					transferAmount = Float.parseFloat(fromSavingsField.getText());
					fromSavingsField.setText(null);
					validate = true;
				}
				catch(NumberFormatException nfe) {
					JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Input Error");
					fromSavingsField.setText(null);
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					validate = false;
				}
			try{
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.account_table "
												+"WHERE account_number = '"+NewUserPage.accountNo+"'");		
				if(rs.next())

					tempValue = rs.getFloat(8);

				connect.close();
			}catch(SQLException ex){ 
				System.out.println("Got an SQL exception connecting from savings transfer!");
			}
		}
			else {
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET savings_balance = savings_balance - ?, "
							+ "current_balance = current_balance + ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					if(tempValue > 0 && transferAmount <= tempValue) {
					stmt.setFloat(1, transferAmount);
					stmt.setFloat(2, transferAmount);
					stmt.setInt(3, NewUserPage.accountNo);

					stmt.executeUpdate();
					JOptionPane optionPane = new JOptionPane("Transfer Successfull!", JOptionPane.INFORMATION_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Transaction Confirmation");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					connect.close();
					}
					else {
						JOptionPane optionPane = new JOptionPane("Insufficient Funds available in Savings account", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Transfer Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
					}
				}catch(SQLException ex){ 
					System.err.println("Got an SQL exception transferring from savings account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == fromCurrent) {
			if(fromCurrentField.getText().isEmpty() == false) {
				try {
				transferAmount = Float.parseFloat(fromCurrentField.getText());
				fromCurrentField.setText(null);
				validate = true;
				}
				catch(NumberFormatException nfe) {
					JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Input Error");
					fromCurrentField.setText(null);
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					dialog.dispose();
					validate = false;
				}
				try{
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.account_table "
													+"WHERE account_number = '"+NewUserPage.accountNo+"'");		
					if(rs.next())

						tempValue = rs.getFloat(9);

					connect.close();
				}catch(SQLException ex){ 
					System.out.println("Got an SQL exception connecting to current transfer!");
				}
			}
			else {
				JOptionPane optionPane = new JOptionPane("You must enter a numeric value...", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Input Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String update = " UPDATE bank_account_history.account_table SET current_balance = current_balance - ?, "
							+ "savings_balance = savings_balance + ?, "
							+ "total_balances = savings_balance + current_balance "
							+ "WHERE (account_number = ?);";

					PreparedStatement stmt = connect.prepareStatement(update);
					if(tempValue > 0 && transferAmount <= tempValue) {
						stmt.setFloat(1, transferAmount);
						stmt.setFloat(2, transferAmount);
						stmt.setInt(3, NewUserPage.accountNo);
	
						stmt.executeUpdate();
						JOptionPane optionPane = new JOptionPane("Transfer Successfull!", JOptionPane.INFORMATION_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Transaction Confirmation");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						connect.close();
					}
					else {
						JOptionPane optionPane = new JOptionPane("Insufficient Funds available in Current account", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Transfer Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
					}
				}catch(SQLException ex){ 
					System.err.println("Got an SQL exception transferring from current account!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}	
		}
		if(target == statement) {
			this.dispose();
			new ViewStatementPage("Account Details and Balances");	
		}
		if(target == logOut) {
			System.exit(0);
		}
	}
}
