import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SignInFrame extends JFrame {
	private JPanel panel, fieldsPanel, buttonsPanel, mainPanel;
	private JLabel titleLabel, usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JButton signInButton, registerButton;
	
	public SignInFrame() {
		panel = new JPanel();
		
		titleLabel = new JLabel("EcoSystems");
		titleLabel.setPreferredSize(new Dimension(300,50));
		titleLabel.setFont(new Font("Comic Sans", Font.BOLD, 35));
		
		//fields panel
		fieldsPanel = new JPanel();
		usernameLabel = new JLabel("Username");
		usernameField = new JTextField("username");
		usernameField.setPreferredSize(new Dimension(100,20));
		passwordLabel = new JLabel("Password");
		passwordField = new JTextField("password");
		passwordField.setPreferredSize(new Dimension(100,20));
		
		fieldsPanel.add(usernameLabel);
		fieldsPanel.add(usernameField);
		fieldsPanel.add(passwordLabel);
		fieldsPanel.add(passwordField);
		
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
		
		//buttons panel
		buttonsPanel = new JPanel();
		signInButton = new JButton("Sign In");
		signInButton.setAlignmentX(CENTER_ALIGNMENT);
		
		//Action listener for signInButton
		signInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				DataBase db = new DataBase();
				
				String username = usernameField.getText();
				String password = passwordField.getText();

				if(db.authentication(username, password)) {
					//new HomeFrame();
					dispose();
				}
				else JOptionPane.showMessageDialog(null, "Wrong Username or Password", 
						"Authentication failed", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		registerButton = new JButton("Register");
		registerButton.setAlignmentX(CENTER_ALIGNMENT);
		
		//Action listener for registerButton
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterFrame();
				dispose();
			}
		});
		
		buttonsPanel.add(signInButton);
		buttonsPanel.add(registerButton);
		
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		
		//main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(fieldsPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel,BorderLayout.SOUTH);
		
		panel.add(titleLabel);
		panel.add(mainPanel);
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
		this.setTitle("Register Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
