package bankingapp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

@SuppressWarnings("serial")
public class ViewStatementPage extends JFrame implements ActionListener{

	private static String firstName;
	private static String lastName;
	private static String streetName;
	private static String streetName2;
	private static String townName;
	private static String countyName;
	private static float savingsBalance;
	private static float currentBalance;
	private static float totalBalances;

	JButton back = new JButton("Back");
	JButton exit = new JButton("Exit");

	ViewStatementPage(String str)
	{
		super(str);
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT,26,15);
		this.setLayout(layout);
		this.setSize(350, 400);
		this.setLocation(550, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_account_history","root","root");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT * FROM bank_account_history.account_table "
					+ "WHERE account_number = '"+NewUserPage.accountNo+"'");
			if(rs.next())

				firstName = ""+rs.getString(2);
				lastName = ""+rs.getString(3);
				streetName = ""+rs.getString(4);
				streetName2 = ""+rs.getString(5);
				townName = ""+rs.getString(6);
				countyName = ""+rs.getString(7);
				savingsBalance = rs.getFloat(8);
				currentBalance = rs.getFloat(9);
				totalBalances = rs.getFloat(10);

			connect.close();
		}catch(Exception e){ 
			System.out.println(e);
		}

		JLabel fName = new JLabel("First Name:");
		JLabel firstN = new JLabel(firstName);
		JLabel blank = new JLabel("                            ");
		JLabel lName = new JLabel("Last Name:");
		JLabel lastN = new JLabel(lastName);
		JLabel blank2 = new JLabel("                            ");
		JLabel street = new JLabel("Street:        ");
		JLabel streetname = new JLabel(streetName);
		JLabel blank3 = new JLabel("                              ");
		JLabel street2 = new JLabel("street 2 * : ");
		JLabel streetname2 = new JLabel(streetName2);
		JLabel blank31 = new JLabel("                               ");
		JLabel town = new JLabel("Town:         ");
		JLabel townname = new JLabel(townName);
		JLabel blank4 = new JLabel("                            ");
		JLabel county = new JLabel("County:      ");
		JLabel countyname = new JLabel(countyName);
		JLabel blank5 = new JLabel("                            ");
		JLabel accountNo = new JLabel("Account Number:");
		JLabel number = new JLabel(""+NewUserPage.accountNo);
		JLabel blank6 = new JLabel("                            ");
		JLabel savings = new JLabel("Savings Balance:");
		JLabel savingsValue = new JLabel("€ "+savingsBalance);
		JLabel blank7 = new JLabel("                            ");
		JLabel current = new JLabel("Current Balance:");
		JLabel currentValue = new JLabel("€ "+currentBalance);
		JLabel blank8 = new JLabel("                            ");
		JLabel total = new JLabel("Total Balance:     ");
		JLabel totalValue = new JLabel("€ "+totalBalances);
		JLabel blank9 = new JLabel("          			      ");
		JLabel blank10 = new JLabel("                                   ");

		this.add(fName);
		this.add(firstN);
		this.add(blank);
		this.add(lName);
		this.add(lastN);
		this.add(blank2);
		this.add(street);
		this.add(streetname);
		this.add(blank31);
		this.add(street2);
		this.add(streetname2);
		this.add(blank3);
		this.add(town);
		this.add(townname);
		this.add(blank4);
		this.add(county);
		this.add(countyname);
		this.add(blank5);
		this.add(accountNo);
		this.add(number);
		this.add(blank6);
		this.add(savings);
		this.add(savingsValue);
		this.add(blank7);
		this.add(current);
		this.add(currentValue);
		this.add(blank8);
		this.add(total);
		this.add(totalValue);
		this.add(blank9);
		this.add(back);
		this.add(blank10);
		this.add(exit);

		back.addActionListener(this);
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();

		if(target == back) {
			this.dispose();
			new TransactionsPage("Transactions");
		}
		if(target == exit) {
			System.exit(0);
		}
	}

}
