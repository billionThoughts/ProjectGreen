package main;
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
	
	public HomeFrame(UserAccount signedInAccount) {
		this.signedInAccount = signedInAccount;
		
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
				DataBase db = DataBase.getInstance();
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
		infoButton.setBackground(new Color(255, 153, 102));
		infoButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		infoButton.setBounds(736, 468, 67, 23);
		panel.add(infoButton);
		
		//ActionListener for infoButton
		infoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoFrame(signedInAccount);
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
				new RedemptFrame(signedInAccount);
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
				new StakeFrame(signedInAccount);
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
				new BorrowFrame(signedInAccount);
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
				new LendFrame(signedInAccount);
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
				new DonateFrame(signedInAccount);
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
				new StatisticsFrame(signedInAccount);
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
		DataBase db = DataBase.getInstance();
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
	
	class FirstRecycleFrame extends JFrame {
		private JPanel panel;
		private JLabel titleLabel, firstRecycleLabel, secondRecycleLabel, backgroundIconLabel;
		private JButton recycleButton, homeButton;
		
		public FirstRecycleFrame(Material m) {		
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			
			titleLabel = new JLabel("Recycle");
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			titleLabel.setBounds(0, 11, 827, 36);
			panel.add(titleLabel);
			
			homeButton = new JButton("Home");
			homeButton.setBackground(new Color(255, 153, 102));
			homeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
			homeButton.setBounds(10, 455, 97, 36);
			panel.add(homeButton);
			
			//ActionListener for homeButton
			homeButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new HomeFrame(signedInAccount);
					dispose();
				}
			});
			
			firstRecycleLabel = new JLabel("You chose to recycle " + m.getName().toLowerCase() + "!");
			firstRecycleLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			firstRecycleLabel.setForeground(new Color(0, 0, 0));
			firstRecycleLabel.setBounds(182, 129, 185, 18);
			panel.add(firstRecycleLabel);
			
			secondRecycleLabel = new JLabel("Please insert " + m.getName().toLowerCase() + " materials to the gap.");
			secondRecycleLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			secondRecycleLabel.setBounds(182, 182, 260, 18);
			panel.add(secondRecycleLabel);
			
			recycleButton = new JButton("Recycle Now");
			recycleButton.setBackground(new Color(51, 204, 255));
			recycleButton.setFont(new Font("Tahoma", Font.BOLD, 13));
			recycleButton.setBounds(300, 330, 206, 51);
			panel.add(recycleButton);
			
			//ActionListener for recycleButton
			recycleButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					signedInAccount.recycleMaterial(m);
					DataBase db = DataBase.getInstance();
					db.saveSignedInAccount(signedInAccount);
					new SecondRecycleFrame(m);
					dispose();
				}
			});
			
			backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
			backgroundIconLabel.setBounds(0, 0, 860, 515);
			panel.add(backgroundIconLabel);
			
			this.setContentPane(panel);
			
			this.setSize(870, 545);
			this.setResizable(false);
			this.setTitle("First Recycle Screen");
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	class SecondRecycleFrame extends JFrame {
		private JPanel panel;
		private JLabel recycleLabel, firstLabel, secondLabel, thirdLabel, backgroundIconLabel;
		private JButton homeButton;
		private Timer timer;
		
		public SecondRecycleFrame(Material m) {	
			timer = new Timer(3000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					secondLabel.setText("Materials accepted.");
					thirdLabel.setText("Congratulations you earn " + m.getReward() + " Tokens!");
				}
			});
			
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			
			recycleLabel = new JLabel("Recycle");
			recycleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			recycleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			recycleLabel.setBounds(0, 11, 827, 41);
			panel.add(recycleLabel);
			
			homeButton = new JButton("Home");
			homeButton.setBackground(new Color(255, 153, 102));
			homeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
			homeButton.setBounds(10, 455, 97, 36);
			panel.add(homeButton);
			
			//ActionListener for homeButton
			homeButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new HomeFrame(signedInAccount);
					dispose();
				}
			});
			
			firstLabel = new JLabel("Please wait while we check the materials!");
			firstLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			firstLabel.setBounds(165, 143, 282, 18);
			panel.add(firstLabel);
			
			timer.start();
			
			secondLabel = new JLabel(" ... ");
			secondLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			secondLabel.setBounds(165, 250, 145, 18);
			panel.add(secondLabel);
			
			thirdLabel = new JLabel(" ");
			thirdLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			thirdLabel.setBounds(165, 274, 240, 18);
			panel.add(thirdLabel);
			
			backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
			backgroundIconLabel.setBounds(0, 0, 860, 515);
			panel.add(backgroundIconLabel);
			
			this.setContentPane(panel);
			
			this.setSize(870, 545);
			this.setResizable(false);
			this.setTitle("Second Recycle Screen");
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
}

