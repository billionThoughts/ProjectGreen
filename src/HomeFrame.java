import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HomeFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel profileIconLabel, usernameLabel, tokensLabel, recycleLabel, backgroundLabel;
	private JTextField tokensField, usernameField;
	private JButton useButton, stakeButton, borrowButton, lendButton, donateButton, statisticsButton, logoutButton;
	private JButton infoButton, plasticButton, paperButton, glassButton, metalButton, organicButton;
	
	public HomeFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		profileIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/profil.png")));
		profileIconLabel.setBounds(34, 24, 101, 93);
		panel.add(profileIconLabel);
		
		logoutButton = new JButton("Sign Out");
		logoutButton.setBackground(new Color(255, 102, 102));
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutButton.setBounds(160, 88, 86, 27);
		panel.add(logoutButton);
		
		//ActionListener for logoutButton
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				db.saveSignedInAccount(signedInAccount);
				dispose();
				new SignInFrame();
			}
		});
		
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernameLabel.setBounds(160, 24, 72, 14);
		panel.add(usernameLabel);
		
		usernameField = new JTextField(signedInAccount.getUsername());
		usernameField.setEditable(false);
		usernameField.setBounds(160, 43, 86, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		tokensLabel = new JLabel("Tokens");
		tokensLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		tokensLabel.setBounds(46, 142, 46, 14);
		panel.add(tokensLabel);
		
		tokensField = new JTextField(Integer.toString(signedInAccount.getTokens()));
		tokensField.setEditable(false);
		tokensField.setBounds(46, 161, 86, 20);
		panel.add(tokensField);
		tokensField.setColumns(10);
		
		recycleLabel = new JLabel("Recycle");
		recycleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		recycleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recycleLabel.setBounds(363, 43, 464, 27);
		panel.add(recycleLabel);
		
		RecyclingButtonActionListener recyclingButtonListener = new RecyclingButtonActionListener();
		
		plasticButton = new JButton("Plastic");
		plasticButton.setBackground(new Color(255, 255, 255));
		plasticButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		plasticButton.setBounds(489, 101, 224, 35);
		panel.add(plasticButton);
		plasticButton.addActionListener(recyclingButtonListener);
		
		paperButton = new JButton("Paper");
		paperButton.setBackground(new Color(0, 153, 102));
		paperButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		paperButton.setBounds(489, 160, 224, 35);
		panel.add(paperButton);
		paperButton.addActionListener(recyclingButtonListener);
		
		glassButton = new JButton("Glass");
		glassButton.setBackground(new Color(0, 153, 204));
		glassButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		glassButton.setBounds(489, 219, 224, 35);
		panel.add(glassButton);
		glassButton.addActionListener(recyclingButtonListener);
		
		metalButton = new JButton("Metal");
		metalButton.setBackground(new Color(204, 204, 204));
		metalButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		metalButton.setBounds(489, 280, 224, 35);
		panel.add(metalButton);
		metalButton.addActionListener(recyclingButtonListener);
		
		organicButton = new JButton("Organic");
		organicButton.setBackground(new Color(204, 153, 51));
		organicButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		organicButton.setBounds(489, 339, 224, 35);
		panel.add(organicButton);
		organicButton.addActionListener(recyclingButtonListener);
		
		infoButton = new JButton("Info");
		infoButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		infoButton.setBounds(736, 468, 67, 23);
		panel.add(infoButton);
		
		//ActionListener for infoButton
		infoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoFrame();
				dispose();
			}
		});
		
		useButton = new JButton("Use Tokens");
		useButton.setBackground(new Color(255, 153, 102));
		useButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		useButton.setBounds(34, 237, 125, 40);
		panel.add(useButton);
		
		//ActionListener for useButton
		useButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RedemptFrame();
				dispose();
			}
		});
		
		stakeButton = new JButton("Stake Tokens");
		stakeButton.setBackground(new Color(255, 153, 102));
		stakeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		stakeButton.setBounds(187, 237, 125, 40);
		panel.add(stakeButton);
		
		//ActionListener for stakeButton
		stakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new StakeFrame();
				dispose();
			}
		});
		
		borrowButton = new JButton("Borrow Tokens");
		borrowButton.setBackground(new Color(255, 153, 102));
		borrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		borrowButton.setBounds(34, 307, 125, 40);
		panel.add(borrowButton);
		
		//ActionListener for borrowButton
		borrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new BorrowFrame();
				dispose();
			}
		});
		
		lendButton = new JButton("Lend Tokens");
		lendButton.setBackground(new Color(255, 153, 102));
		lendButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		lendButton.setBounds(187, 307, 125, 40);
		panel.add(lendButton);
		
		//ActionListener for lendButton
		lendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new LendFrame();
				dispose();
			}
		});
		
		donateButton = new JButton("Donate Tokens");
		donateButton.setBackground(new Color(255, 153, 102));
		donateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		donateButton.setBounds(34, 381, 125, 40);
		panel.add(donateButton);
		
		//ActionListener for donateButton
		donateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new DonateFrame();
				dispose();
			}
		});
		
		statisticsButton = new JButton("Statistics");
		statisticsButton.setBackground(new Color(255, 153, 102));
		statisticsButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		statisticsButton.setBounds(187, 381, 125, 40);
		panel.add(statisticsButton);
		
		//ActionListener for statisticsButton
		statisticsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new StatisticsFrame();
				dispose();
			}
		});
		
		backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundLabel.setBounds(348, 0, 515, 525);
		panel.add(backgroundLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Home Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public Material selectMaterial(JButton button) {
		DataBase db = new DataBase();
		String materialName = button.getText();
		Material m = db.getSpecificMaterial(materialName);
		return m;
	}
	
	class RecyclingButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			new FirstRecycleFrame(selectMaterial(button));
			dispose();
		}
	}
}

class FirstRecycleFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel recycleLabel, firstLabel, secondLabel;
	private JButton recycleButton, homeButton;
	
	public FirstRecycleFrame(Material m) {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		homeButton = new JButton("Back");
		recycleLabel = new JLabel("Recycle");
		firstLabel = new JLabel("You chose to recycle " + m.getName().toLowerCase() + "!");
		secondLabel = new JLabel("Please insert " + m.getName().toLowerCase() + " materials to the gap.");
		recycleButton = new JButton("RECYCLE NOW");
		
		homeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		recycleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				signedInAccount.recycleMaterial(m);
				db.signedInAccountSerialization(signedInAccount);
				new SecondRecycleFrame(m);
				dispose();
			}
		});
		
		panel.add(homeButton);
		panel.add(recycleLabel);
		panel.add(firstLabel);
		panel.add(secondLabel);
		panel.add(recycleButton);
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
		this.setTitle("First Recycle Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

class SecondRecycleFrame extends JFrame {
	private JPanel panel;
	private JLabel recycleLabel, firstLabel, secondLabel;
	private JButton homeButton;
	
	public SecondRecycleFrame(Material m) {		
		panel = new JPanel();
		homeButton = new JButton("Back");
		recycleLabel = new JLabel("Recycle");
		firstLabel = new JLabel("Materials accepted.");
		secondLabel = new JLabel("Congratulations you earn " +m.getReward() + " tokens!");
		
		homeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		panel.add(homeButton);
		panel.add(recycleLabel);
		panel.add(firstLabel);
		panel.add(secondLabel);
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
		this.setTitle("Second Recycle Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

