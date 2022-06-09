package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SignInFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel signInLabel, ecoSystemsLabel, imageLabel, usernameLabel, passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton signInButton, registerButton;
	
	public SignInFrame() {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		signInLabel = new JLabel("Sign In");
		signInLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signInLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		signInLabel.setBounds(0, 11, 223, 23);
		panel.add(signInLabel);
		
		ecoSystemsLabel = new JLabel("EcoSystems");
		ecoSystemsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ecoSystemsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		ecoSystemsLabel.setForeground(new Color(0, 102, 0));
		ecoSystemsLabel.setBounds(706, 477, 121, 14);
		panel.add(ecoSystemsLabel);
		
		imageLabel = new JLabel(new ImageIcon(getClass().getResource("/images/image.png")));
		imageLabel.setBounds(250, 5, 605, 502);
		panel.add(imageLabel);
		
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameLabel.setBounds(53, 70, 66, 14);
		panel.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBounds(53, 95, 121, 38);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordLabel.setBounds(53, 159, 66, 14);
		panel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(53, 184, 121, 38);
		panel.add(passwordField);
		passwordField.setColumns(10);
		
		signInButton = new JButton("Sign in");
		signInButton.setBackground(new Color(190, 235, 113));
		signInButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		signInButton.setBounds(69, 252, 89, 23);
		panel.add(signInButton);
		
		//Action listener for signInButton
		signInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				DataBase db = DataBase.getInstance();
						
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());

				UserAccount signedInAccount = db.authentication(username, password);
				if(signedInAccount != null) {
					new HomeFrame(signedInAccount);
					dispose();
				}
				else JOptionPane.showMessageDialog(null, "Wrong Username or Password", 
						"Authentication failed", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		
		registerButton = new JButton("Register");
		registerButton.setBackground(new Color(190, 235, 113));
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		registerButton.setBounds(69, 286, 89, 23);
		panel.add(registerButton);
		
		//Action listener for registerButton
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterFrame();
				dispose();
			}
		});
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Sign In Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
