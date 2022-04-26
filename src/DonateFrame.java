import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DonateFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, actionsPanel;
	private JLabel titleLabel, donationLabel, amountLabel;
	private JLabel actionsLabel, treesLabel, beachesLabel, roadsLabel, backgroundIconLabel;
	private JTextField amountField;
	private JButton donateButton, homeButton;
	private JLabel tokensLabel;
	private JLabel allDonationsLabel;
	
	public DonateFrame() {
		DataBase db = DataBase.getInstance();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Donate Tokens");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 11, 860, 32);
		panel.add(titleLabel);
		
		tokensLabel = new JLabel("Tokens: " + signedInAccount.getTokens());
		tokensLabel.setHorizontalAlignment(SwingConstants.LEFT);
		tokensLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		tokensLabel.setBounds(154, 120, 148, 14);
		panel.add(tokensLabel);
		
		allDonationsLabel = new JLabel("All donations: " + db.getDonations());
		allDonationsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		allDonationsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		allDonationsLabel.setBounds(154, 95, 148, 14);
		panel.add(allDonationsLabel);
		
		homeButton = new JButton("Home");
		homeButton.setBackground(new Color(255, 153, 102));
		homeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		homeButton.setBounds(23, 451, 97, 36);
		panel.add(homeButton);
		
		//ActionListener for homeButton
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		donationLabel = new JLabel("Donate tokens for environmental actions");
		donationLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		donationLabel.setBounds(154, 172, 271, 14);
		panel.add(donationLabel);
		
		amountLabel = new JLabel("AMOUNT TO DONATE");
		amountLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		amountLabel.setBounds(154, 224, 148, 14);
		panel.add(amountLabel);
		
		amountField = new JTextField("");
		amountField.setBounds(154, 244, 251, 23);
		panel.add(amountField);
		
		donateButton = new JButton("Donate");
		donateButton.setBounds(563, 237, 112, 36);
		donateButton.setBackground(new Color(0, 204, 255));
		donateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(donateButton);
		
		//ActionListener for donateButton
		donateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String amountText = amountField.getText();
				int amount = Integer.parseInt(amountText);
				if(amount <= signedInAccount.getTokens()) {
					db.tokenDonation(amount, signedInAccount);
					db.saveSignedInAccount(signedInAccount);
					tokensLabel.setText("Tokens: " + signedInAccount.getTokens());
					allDonationsLabel.setText("All donations: " + db.getDonations());
					JOptionPane.showMessageDialog(null, "You donated " + amount + " tokens!");
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot afford to donate this amount",
							"Donation Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		actionsLabel = new JLabel("List of actions");
		actionsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		actionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actionsLabel.setBounds(0, 327, 856, 23);
		panel.add(actionsLabel);
		
		//actions panel
		actionsPanel = new JPanel();
		actionsPanel.setBackground(new Color(204, 255, 204));
		actionsPanel.setBounds(358, 348, 148, 85);
		
		treesLabel = new JLabel("1. Planting Trees");
		treesLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		actionsPanel.add(treesLabel);
		
		beachesLabel = new JLabel("2. Cleaning Beaches");
		beachesLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		actionsPanel.add(beachesLabel);
		
		roadsLabel = new JLabel("3. Cleaning Roads");
		roadsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		actionsPanel.add(roadsLabel);
		
		actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
		panel.add(actionsPanel);
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 860, 515);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Donate Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
