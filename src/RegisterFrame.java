import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegisterFrame extends JFrame {
	private JPanel panel;
	private JLabel titleLabel, firstNameLabel, lastNameLabel, emailLabel, usernameLabel, passwordLabel, confirmPasswordLabel, imageLabel;
	private JTextField firstNameField, lastNameField, emailField, usernameField, passwordField, confirmPasswordField;
	private JButton registerButton, backButton;
	private JCheckBox termsCheckBox;
	
	public RegisterFrame() {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Register");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 11, 784, 30);
		panel.add(titleLabel);
		
		backButton = new JButton("Back");
		backButton.setBackground(new Color(224, 224, 224));
		backButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		backButton.setBounds(31, 455, 99, 30);
		panel.add(backButton);
		
		//Action listener for backButton
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SignInFrame();
				dispose();
			}
		});
		
		firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		firstNameLabel.setBounds(73, 82, 63, 14);
		panel.add(firstNameLabel);
		
		firstNameField = new JTextField("");
		firstNameField.setHorizontalAlignment(SwingConstants.CENTER);
		firstNameField.setBounds(73, 107, 152, 35);
		panel.add(firstNameField);
		
		lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lastNameLabel.setBounds(264, 82, 63, 14);
		panel.add(lastNameLabel);
		
		lastNameField = new JTextField("");
		lastNameField.setHorizontalAlignment(SwingConstants.CENTER);
		lastNameField.setBounds(264, 107, 152, 35);
		panel.add(lastNameField);
		
		emailLabel = new JLabel("E-mail");
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		emailLabel.setBounds(73, 165, 46, 14);
		panel.add(emailLabel);
		
		emailField = new JTextField("");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setBounds(73, 190, 152, 35);
		panel.add(emailField);
		
		usernameLabel = new JLabel("Choose Username");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameLabel.setBounds(73, 248, 117, 14);
		panel.add(usernameLabel);
		
		usernameField = new JTextField("");
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBounds(73, 273, 152, 35);
		panel.add(usernameField);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordLabel.setBounds(73, 334, 63, 14);
		panel.add(passwordLabel);
		
		passwordField = new JTextField("");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(73, 359, 152, 35);
		panel.add(passwordField);
		
		confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		confirmPasswordLabel.setBounds(264, 334, 109, 14);
		panel.add(confirmPasswordLabel);
		
		confirmPasswordField = new JTextField("");
		confirmPasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		confirmPasswordField.setBounds(264, 359, 152, 35);
		panel.add(confirmPasswordField);
		
		termsCheckBox = new JCheckBox("I accept the Terms of Use & Privacy Policy");
		termsCheckBox.setBounds(500, 425, 275, 23);
		termsCheckBox.setOpaque(true);
		termsCheckBox.setBackground(new Color(153, 204, 255));
		panel.add(termsCheckBox);
		
		registerButton = new JButton("Register Now");
		registerButton.setBackground(new Color(153, 204, 255));
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		registerButton.setBounds(571, 455, 117, 30);
		panel.add(registerButton);
		
		//Action listener for registerButton
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				DataBase db = new DataBase();
					
				String firstName, lastName, email, username, password, confirmPassword;
						
				firstName = firstNameField.getText();
				lastName = lastNameField.getText();
				email = emailField.getText();
				username = usernameField.getText();
				password = passwordField.getText();
				confirmPassword = confirmPasswordField.getText();
				
				// checking particulars
				if(termsCheckBox.isSelected()) {
					if(db.checkParticularsAvailability(username, email)) {
						if(password.equals(confirmPassword)) {
							if(password.length() >= 8) {
								UserAccount acc = new UserAccount(firstName, lastName, email, username, password);
								db.addUserAccount(acc);
								new SignInFrame();
								dispose();
							}
							else JOptionPane.showMessageDialog(null, "Please enter a password at least 8 characters long", 
									"Too Short Password", JOptionPane.ERROR_MESSAGE);
						}
						else JOptionPane.showMessageDialog(null, "Fields Password and Confirm Password don't match", 
								"Confirm Password Error", JOptionPane.ERROR_MESSAGE);
					}
					else JOptionPane.showMessageDialog(null, "This Username/Email is already taken", 
							"Username/Email Error", JOptionPane.ERROR_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "You need to accept the Terms of Use & Privacy Policy to register", 
						"Terms of Use/Privacy Policy", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		imageLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		imageLabel.setBounds(0, 0, 865, 511);
		panel.add(imageLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Register Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
