import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DonateFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, titlePanel, donationPanel, actionsPanel;
	private JLabel donateTitleLabel, tokensLabel, allDonationsLabel, donationLabel, amountLabel;
	private JLabel actionsLabel, treesLabel, beachesLabel, roadsLabel;
	private JTextField amountField;
	private JButton donateButton, backButton;
	
	public DonateFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//titlePanel
		titlePanel = new JPanel();
		backButton = new JButton("Back");
		tokensLabel = new JLabel("Tokens: " + signedInAccount.getTokens());
		donateTitleLabel = new JLabel("Donate Tokens");
		allDonationsLabel = new JLabel("All donations: " + db.getDonations());
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		titlePanel.add(backButton);
		titlePanel.add(tokensLabel);
		titlePanel.add(donateTitleLabel);
		titlePanel.add(allDonationsLabel);
		
		//donation panel
		donationPanel = new JPanel();
		donationLabel = new JLabel("Donate tokens for environmental actions");
		amountLabel = new JLabel("AMOUNT TO DONATE");
		amountField = new JTextField("Amount");
		donateButton = new JButton("DONATE");
		
		donateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String amountText = amountField.getText();
				int amount = Integer.parseInt(amountText);
				if(amount <= signedInAccount.getTokens()) {
					db.tokenDonation(amount, signedInAccount);
					db.signedInAccountSerialization(signedInAccount);
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
		
		donationPanel.add(donationLabel);
		donationPanel.add(amountLabel);
		donationPanel.add(amountField);
		donationPanel.add(donateButton);
		
		//actions panel
		actionsPanel = new JPanel();
		actionsLabel = new JLabel("List Of Actions");
		treesLabel = new JLabel("1. Planting Trees");
		beachesLabel = new JLabel("2. Cleaning Beaches");
		roadsLabel = new JLabel("3. Cleaning Roads");
		
		actionsPanel.add(actionsLabel);
		actionsPanel.add(treesLabel);
		actionsPanel.add(beachesLabel);
		actionsPanel.add(roadsLabel);
		
		actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
		
		
		panel.add(titlePanel);
		panel.add(donationPanel);
		panel.add(actionsPanel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
		this.setTitle("Donate Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
