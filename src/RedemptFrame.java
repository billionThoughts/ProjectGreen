import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RedemptFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel titleLabel, parkingLabel, cinemaLabel, transportationLabel, bikeLabel, backgroundIconLabel;
	private JButton homeButton, parkingButton, cinemaButton, transportationButton, bikeButton;
	
	public RedemptFrame() {
		DataBase db = DataBase.getInstance();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Use Tokens");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(0, 11, 827, 37);
		panel.add(titleLabel);
		
		homeButton = new JButton("Home");
		homeButton.setBackground(new Color(255, 153, 102));
		homeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		homeButton.setBounds(10, 455, 97, 36);
		panel.add(homeButton);
		
		//ActionListener for homeButton
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		parkingLabel = new JLabel("Parking ................................................. 3000 Tokens");
		parkingLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		parkingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		parkingLabel.setBounds(152, 110, 340, 37);
		panel.add(parkingLabel);
		
		cinemaLabel = new JLabel("Cinema / Theater ................................ 8000 Tokens");
		cinemaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cinemaLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		cinemaLabel.setBounds(152, 186, 340, 37);
		panel.add(cinemaLabel);
		
		transportationLabel = new JLabel("Public Transportation ......................... 5000 Tokens");
		transportationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		transportationLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		transportationLabel.setBounds(152, 258, 340, 37);
		panel.add(transportationLabel);
		
		bikeLabel = new JLabel("Rent a bike ........................................... 7500 Tokens");
		bikeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bikeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		bikeLabel.setBounds(152, 330, 340, 37);
		panel.add(bikeLabel);
		
		TicketButtonActionListener listener = new TicketButtonActionListener();
		
		parkingButton = new JButton("Claim");
		parkingButton.setBackground(new Color(0, 204, 255));
		parkingButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		parkingButton.setBounds(596, 110, 138, 37);
		parkingButton.addActionListener(listener);
		panel.add(parkingButton);
		
		cinemaButton = new JButton("Claim");
		cinemaButton.setBackground(new Color(0, 204, 255));
		cinemaButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cinemaButton.setBounds(596, 186, 138, 37);
		cinemaButton.addActionListener(listener);
		panel.add(cinemaButton);
		
		transportationButton = new JButton("Claim");
		transportationButton.setBackground(new Color(0, 204, 255));
		transportationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		transportationButton.setBounds(596, 258, 138, 37);
		transportationButton.addActionListener(listener);
		panel.add(transportationButton);
		
		bikeButton = new JButton("Claim");
		bikeButton.setBackground(new Color(0, 204, 255));
		bikeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		bikeButton.setBounds(596, 330, 138, 37);
		bikeButton.addActionListener(listener);
		panel.add(bikeButton);
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 880, 520);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Redempt Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public Ticket selectTicket(String name) {
		DataBase db = DataBase.getInstance();
		Ticket t = db.getSpecificTicket(name);
		return t;
	}
	
	class TicketButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DataBase db = DataBase.getInstance();
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
				db.saveSignedInAccount(signedInAccount);
				new QRFrame();
				dispose();
			}
			else JOptionPane.showMessageDialog(null, "You can't afford this ticket", "Ticket Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class QRFrame extends JFrame {
	private JPanel panel;
	private JLabel titleLabel, redemptLabel_1, redemptLabel_2, redemptLabel_3, redemptLabel_4, backgroundIconLabel;
	private JButton homeButton;
	private Timer timer;
	
	public QRFrame() {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Use Tokens");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 11, 827, 30);
		panel.add(titleLabel);
		
		homeButton = new JButton("Home");
		homeButton.setBackground(new Color(255, 153, 102));
		homeButton.setBounds(10, 455, 97, 36);
		panel.add(homeButton);
		
		//ActionListener for homeButton
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		redemptLabel_1 = new JLabel("Please wait");
		redemptLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		redemptLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		redemptLabel_1.setBounds(115, 95, 84, 23);
		panel.add(redemptLabel_1);
		
		redemptLabel_2 = new JLabel("...");
		redemptLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		redemptLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		redemptLabel_2.setBounds(197, 95, 20, 23);
		panel.add(redemptLabel_2);
		
		redemptLabel_3 = new JLabel("");
		redemptLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		redemptLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		redemptLabel_3.setBounds(115, 152, 341, 23);
		panel.add(redemptLabel_3);
		
		redemptLabel_4 = new JLabel("");
		redemptLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		redemptLabel_4.setBounds(368, 291, 84, 84);
		panel.add(redemptLabel_4);
		
		timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redemptLabel_1.setText("");
				redemptLabel_2.setText("");
				redemptLabel_3.setText("Congratulations, scan the QR code to receive your ticket.");
				redemptLabel_4.setIcon(new ImageIcon(getClass().getResource("/images/barcode.gif")));
			}
		});
		
		timer.start();
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 860, 515);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Redempt Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
