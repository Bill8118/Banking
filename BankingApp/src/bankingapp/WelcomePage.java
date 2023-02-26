package bankingapp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class WelcomePage extends JFrame implements ActionListener{

	private JLabel message = new JLabel("Please Select an option");
	private JButton login = new JButton("Login");
	private JButton newUser = new JButton("New User");
	private JButton cancel = new JButton("Quit");

	WelcomePage(String str)
	{
		super(str);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER,100,20);
		this.setLayout(layout);
		this.setSize(350, 240);
		this.setLocation(550, 150);
		this.setBackground(Color.green);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		message.setSize(350, 30);
		this.add(message);

		login.setSize(30, 10);
		login.addActionListener(this);
		this.add(login);

		newUser.setSize(30, 10);
		newUser.addActionListener(this);
		this.add(newUser);
		
		cancel.setSize(30, 10);
		cancel.addActionListener(this);
		this.add(cancel);
	}

	@Override
	public void actionPerformed (ActionEvent e) {

		Object target = e.getSource();

		if(target == login) {
			this.dispose();
			new LoginPage("Login Page");	
		}
		if(target == newUser) {
			this.dispose();
			new NewUserPage("Create New Account");
		}
		if(target == cancel) {
			System.exit(0);
		}
	}
}
