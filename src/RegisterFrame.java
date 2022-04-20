import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegisterFrame extends JFrame {
	private JPanel panel;
	private JLabel titleLabel;
	private JTextField firstNameField, lastNameField, emailField, usernameField, passwordField, confirmPasswordField;
	private JButton registerButton, backButton;
	private JCheckBox termsCheckBox;
	
	public RegisterFrame() {
		panel = new JPanel();
		
		titleLabel = new JLabel("Register");
		backButton = new JButton("Back");
		
		//Action listener for backButton
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SignInFrame();
				dispose();
			}
		});
		
		firstNameField = new JTextField("First name");
		lastNameField = new JTextField("Last name");
		emailField = new JTextField("Email");
		usernameField = new JTextField("Username");
		passwordField = new JTextField("Password");
		confirmPasswordField = new JTextField("Confirm Password");
		termsCheckBox = new JCheckBox("I accept the Terms of Use & Privacy Policy");
		registerButton = new JButton("Register Now");
		
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
		
		panel.add(backButton);
		panel.add(titleLabel);
		panel.add(firstNameField);
		panel.add(lastNameField);
		panel.add(emailField);
		panel.add(usernameField);
		panel.add(passwordField);
		panel.add(confirmPasswordField);
		panel.add(termsCheckBox);
		panel.add(registerButton);
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
		this.setTitle("Register Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
