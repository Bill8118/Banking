package bankingapp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.*;

@SuppressWarnings("serial")
public class NewUserPage extends JFrame implements ActionListener{

	JLabel firstName = new JLabel("First Name      ");
	JTextField fName = new JTextField(15);
	JLabel lastName = new JLabel("Last Name       ");
	JTextField lName = new JTextField(15);
	JLabel streetAddress = new JLabel("Street Address");
	JTextField street = new JTextField(15);
	JLabel streetAddress2 = new JLabel("Street 2             ");
	JTextField street2 = new JTextField(15);
	JLabel town = new JLabel("Town/City         ");
	JTextField town_city = new JTextField(15);
	JLabel county = new JLabel("County/State    ");
	JTextField county_state = new JTextField(15);
	JLabel userPassword = new JLabel();
	JLabel user = new JLabel("Username         ");
	JTextField newUser = new JTextField(15);
	JLabel password = new JLabel("Password         ");
	JPasswordField newPassword = new JPasswordField(15);
	JButton create = new JButton("Create");
	JButton back = new JButton("Back");
	JButton cancel = new JButton("Cancel");
	

	static int accountNo;
	private static String name;
	private static String surname;
	private static String streetName;
	private static String streetName2;
	private static String city;
	private static String state;
	private static String username;
	private static String passWord = "";
	private static char[] temp;
	private static boolean validated = false;

	NewUserPage(String str)
	{
		super(str);
		FlowLayout layout = new FlowLayout(FlowLayout.RIGHT,30,20);
		this.setLayout(layout);
		this.setSize(350, 450);
		this.setLocation(550, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		userPassword.setFont(new Font("Please create a username and Password",Font.ITALIC + Font.BOLD,13));
		userPassword.setForeground(Color.RED);
		userPassword.setText("Please create a username and Password     ");

		this.getContentPane().add(firstName);
		this.getContentPane().add(fName);
		this.getContentPane().add(lastName);
		this.getContentPane().add(lName);
		this.getContentPane().add(streetAddress);
		this.getContentPane().add(street);
		this.getContentPane().add(streetAddress2);
		this.getContentPane().add(street2);
		this.getContentPane().add(town);
		this.getContentPane().add(town_city);
		this.getContentPane().add(county);
		this.getContentPane().add(county_state);
		this.getContentPane().add(userPassword);
		this.getContentPane().add(user);
		this.getContentPane().add(newUser);
		this.getContentPane().add(password);
		this.getContentPane().add(newPassword);
		this.getContentPane().add(cancel);
		this.getContentPane().add(back);
		this.getContentPane().add(create);
		
		back.addActionListener(this);
		create.addActionListener(this);
		cancel.addActionListener(this);
	}

	public static String encryptPassword(String input) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("sha256");

		byte[] messageDigest = md.digest(input.getBytes());

		BigInteger bigInt = new BigInteger(1,messageDigest);

		return bigInt.toString(2);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();

		if(target == create) {
			
			if(fName.getText().isEmpty() == false && lName.getText().isEmpty() == false && street.getText().isEmpty() == false && town_city.getText().isEmpty() == false 
					&& county_state.getText().isEmpty() == false && newUser.getText().isEmpty() == false && newPassword.getText().isBlank() == false) {
				
				validated = true;
					
			}else {
				JOptionPane optionPane = new JOptionPane("You must fill in all fields!", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Please provide all details");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				dialog.dispose();

				validated = false;
			}
			if(validated == true) {
				boolean passed = false;
				while( passed == false) {
					accountNo = (int)(Math.random() * 100000000);
				
					if(accountNo >= 10000000 && accountNo <= 999999999) {
						passed = true;
					}
					else {
						passed = false;
					}
				}
				NewUserPage.name = fName.getText();
				fName.setText(null);
				NewUserPage.surname = lName.getText();
				lName.setText(null);
				NewUserPage.streetName = street.getText();
				street.setText(null);
				if(street2.getText().isEmpty() == true) {
					NewUserPage.streetName2 = "None Given";
				}else {
					NewUserPage.streetName2 = street2.getText();
				}
				street2.setText(null);
				NewUserPage.city = town_city.getText();
				town_city.setText(null);
				NewUserPage.state = county_state.getText();
				county_state.setText(null);
				NewUserPage.username = newUser.getText();
				newUser.setText(null);
				newPassword.setEchoChar('*');
				temp = newPassword.getPassword();
				for(int i = 0; i < temp.length; i++) {
					passWord += temp[i];
				}
				try {
					passWord = NewUserPage.encryptPassword(passWord);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				newPassword.setText(null);

				try{				  
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");

					String query = " insert into bank_account_history.account_table"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement stmt = connect.prepareStatement(query);
					stmt.setInt(1, NewUserPage.accountNo);
					stmt.setString(2, NewUserPage.name);
					stmt.setString(3, NewUserPage.surname);
					stmt.setString(4, NewUserPage.streetName);
					stmt.setString(5, NewUserPage.streetName2);
					stmt.setString(6, NewUserPage.city);
					stmt.setString(7, NewUserPage.state);
					stmt.setFloat(8, (float) 0.0);
					stmt.setFloat(9, (float) 0.0);
					stmt.setFloat(10, (float) 0.0);

					stmt.executeUpdate();
					System.out.println("Account created successfully");

					String query2 = " insert into bank_account_history.user_login"
							+ " values (?, ?, ?)";
					PreparedStatement stmt2 = connect.prepareStatement(query2);
					stmt2.setInt(1, NewUserPage.accountNo);
					stmt2.setString(2, NewUserPage.username);
					stmt2.setString(3, NewUserPage.passWord);

					stmt2.executeUpdate();
					System.out.println("Login details saved");
					this.dispose();
					new TransactionsPage("Transactions");

					connect.close();
				}
				catch(Exception ex){ 
					System.err.println("Got an exception!");
					ex.printStackTrace();
					System.out.println(ex);
				}
			}
		}
		if(target == back) {
			this.dispose();
			new WelcomePage("Your Loan Tracking App");
		}
		if(target == cancel) {
			fName.setText(null);
			lName.setText(null);
			street.setText(null);
			town_city.setText(null);
			county_state.setText(null);
			newUser.setText(null);
			newPassword.setText(null);
		}
	}

}
