import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RedemptFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, labelPanel, ticketsPanel, parkingPanel, cinemaPanel, transportationPanel, bikePanel;
	private JLabel useLabel, parkingLabel, cinemaLabel, transportationLabel, bikeLabel;
	private JButton homeButton, parkingButton, cinemaButton, transportationButton, bikeButton;
	
	public RedemptFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//label panel
		labelPanel = new JPanel();
		homeButton = new JButton("Back");
		useLabel = new JLabel("Use Tokens");
		labelPanel.add(homeButton);
		labelPanel.add(useLabel);
		
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		//tickets panel
		ticketsPanel = new JPanel();
		TicketButtonActionListener listener = new TicketButtonActionListener();
		
		//parking panel
		parkingPanel = new JPanel();
		parkingLabel = new JLabel("Parking.....................................3000 Tokens");
		parkingButton = new JButton("Claim");
		parkingButton.addActionListener(listener);

		parkingPanel.add(parkingLabel);
		parkingPanel.add(parkingButton);
		
		//cinema panel
		cinemaPanel = new JPanel();
		cinemaLabel = new JLabel("Cinema.....................................8000 Tokens");
		cinemaButton = new JButton("Claim");
		cinemaButton.addActionListener(listener);
		
		cinemaPanel.add(cinemaLabel);
		cinemaPanel.add(cinemaButton);
		
		//transportation panel
		transportationPanel = new JPanel();
		transportationLabel = new JLabel("Public Transportation.............5000 Tokens");
		transportationButton = new JButton("Claim");
		transportationButton.addActionListener(listener);
		
		transportationPanel.add(transportationLabel);
		transportationPanel.add(transportationButton);
		
		//bike panel
		bikePanel = new JPanel();
		bikeLabel = new JLabel("Rent a Bike..............................6500 Tokens");
		bikeButton = new JButton("Claim");
		bikeButton.addActionListener(listener);
		
		bikePanel.add(bikeLabel);
		bikePanel.add(bikeButton);
		
		ticketsPanel.add(parkingPanel);
		ticketsPanel.add(cinemaPanel);
		ticketsPanel.add(transportationPanel);
		ticketsPanel.add(bikePanel);
		
		ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
		
		panel.add(labelPanel);
		panel.add(ticketsPanel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(700, 500);
		this.setTitle("Redempt Screen");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public Ticket selectTicket(String name) {
		DataBase db = new DataBase();
		Ticket t = db.getSpecificTicket(name);
		return t;
	}
	
	class TicketButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DataBase db = new DataBase();
			Ticket t = null;
			if(e.getSource() == parkingButton) {
				t = selectTicket("Parking");
			}
			if(e.getSource() == cinemaButton) {
				t = selectTicket("Cinema");
			}
			if(e.getSource() == transportationButton) {
				t = selectTicket("Public Transportation");
			}
			if(e.getSource() == bikeButton) {
				t = selectTicket("Rent a Bike");
			}
			
			if(signedInAccount.isTicketAffordable(t)) {
				signedInAccount.buyTicket(t);
				db.signedInAccountSerialization(signedInAccount);
				new QRFrame();
				dispose();
			}
			else JOptionPane.showMessageDialog(null, "You can't afford this ticket", "Ticket Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class QRFrame extends JFrame {
	private JPanel panel, labelPanel, qrPanel;
	private JLabel useLabel, qrLabel;
	private JButton homeButton;
	
	public QRFrame() {
		panel = new JPanel();
		
		labelPanel = new JPanel();
		homeButton = new JButton("Back");
		useLabel = new JLabel("Use Tokens");
		
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
			
		});
		
		labelPanel.add(homeButton);
		labelPanel.add(useLabel);
		
		qrPanel = new JPanel();
		qrLabel = new JLabel("Congratulations, scan the QR Code to receive your ticket");
		
		qrPanel.add(qrLabel);
		
		panel.add(labelPanel);
		panel.add(qrPanel);
		
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(700, 400);
		this.setTitle("QR Screen");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
