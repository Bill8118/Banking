package bankingapp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;



@SuppressWarnings("serial")
public class LoginPage extends JFrame implements ActionListener{

	JLabel nameLabel = new JLabel("Username");
	JTextField nameField = new JTextField(18);
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(18);
	JButton login = new JButton("Login ");
	JButton back = new JButton("Back ");
	JButton cancel = new JButton("Cancel");

	static String userName;
	static String password = "";
	static String temp;
	static char[] pass;
	static boolean validate = false;

	LoginPage(String str)
	{
		super(str);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT,30,20);
		this.setLayout(layout);
		this.setSize(350, 200);
		this.setLocation(550, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(nameLabel);
		this.add(nameField);
		this.add(passwordLabel);
		this.add(passwordField);
		this.add(login);
		this.add(back);
		this.add(cancel);

		login.addActionListener(this);
		back.addActionListener(this);
		cancel.addActionListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();

		if(target == login) {
			if(nameField.getText().isEmpty() == false && passwordField.getText().isBlank() == false) {

				userName = nameField.getText();
				pass = passwordField.getPassword();
				passwordField.setEchoChar('*');
				for(int i = 0; i < pass.length; i++) {
					password += pass[i];
				}
				nameField.setText(null);
				passwordField.setText(null);
				try {
					password = NewUserPage.encryptPassword(password);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				try{
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.user_login "
							+ "WHERE username = '"+userName+"'and password = '"+password+"'");
					if(rs.next())

						temp = rs.getString(3);

					if(password.equals(temp)) {
						validate = true;
					}
					else {
						JOptionPane optionPane = new JOptionPane("Incorrect Username or Password", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Login Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
						validate = false;
					}
					connect.close();
				}catch(Exception ex){ 
					System.out.println(ex);
				}

			}else{
				JOptionPane optionPane = new JOptionPane("You must enter your username and password", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Login Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();
				validate = false;
			}
			if(validate == true) {
				try{
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
					Statement stmt = connect.createStatement();
					ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.user_login "
							+ "WHERE username = '"+userName+"'and password = '"+password+"'");
					if(rs.next()) {
						NewUserPage.accountNo = rs.getInt(1);
						this.dispose();
						new TransactionsPage("Transactions");
						connect.close();
					}else {
						JOptionPane optionPane = new JOptionPane("No matching account for details entered!", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Account Error");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						dialog.dispose();
					}
				}
				catch(Exception ex){ 
					System.out.println(ex);
				}
			}
		}
		if(target == back) {
			this.dispose();
			new WelcomePage("Your Banking App");
		}
		if(target == cancel) {
			nameField.setText(null);
			passwordField.setText(null);
		}
	}	
}
