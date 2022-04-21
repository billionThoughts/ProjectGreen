import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HomeFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, userPanel, actionsPanel, recyclePanel;
	private JLabel usernameLabel, tokensLabel, recycleLabel;
	private JButton useButton, stakeButton, borrowButton, lendButton, donateButton, statisticsButton, logoutButton;
	private JButton infoButton, plasticButton, paperButton, glassButton, metalButton, organicButton;
	
	public HomeFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//userPanel
		userPanel = new JPanel();
		
		usernameLabel = new JLabel("User: " + signedInAccount.getUsername());
		tokensLabel = new JLabel("Tokens: " + Integer.toString(signedInAccount.getTokens()));
		logoutButton = new JButton("Logout");
		
		//ActionListener for logoutButton
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				db.saveSignedInAccount(signedInAccount);
				dispose();
				new SignInFrame();
			}
		});
		
		userPanel.add(logoutButton);
		userPanel.add(usernameLabel);
		userPanel.add(tokensLabel);
		
		//recyclePanel
		recyclePanel = new JPanel();
				
		recycleLabel = new JLabel("Recycle");
		infoButton = new JButton("Info");
		plasticButton = new JButton("Plastic");
		paperButton = new JButton("Paper");
		glassButton = new JButton("Glass");
		metalButton = new JButton("Metal");
		organicButton = new JButton("Organic");
				
		infoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new InfoFrame();
				dispose();
			}
		});
		
		RecyclingButtonActionListener recyclingButtonListener = new RecyclingButtonActionListener();
		plasticButton.addActionListener(recyclingButtonListener);
		paperButton.addActionListener(recyclingButtonListener);
		glassButton.addActionListener(recyclingButtonListener);
		metalButton.addActionListener(recyclingButtonListener);
		organicButton.addActionListener(recyclingButtonListener);
		
		recyclePanel.add(recycleLabel);
		recyclePanel.add(infoButton);
		recyclePanel.add(plasticButton);
		recyclePanel.add(paperButton);
		recyclePanel.add(glassButton);
		recyclePanel.add(metalButton);
		recyclePanel.add(organicButton);
		
		//actions panel
		actionsPanel = new JPanel();
		useButton = new JButton("Use Tokens");
		useButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new UseTokensFrame();
				dispose();
			}
		});
		donateButton = new JButton("Donate Tokens");
		donateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new DonationFrame();
				dispose();
			}
		});
		statisticsButton = new JButton("Statistics");
		statisticsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new StatisticsFrame();
				dispose();
			}
			
		});
		stakeButton = new JButton("Stake Tokens");
		stakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new StakeFrame();
				dispose();
			}
			
		});
		borrowButton = new JButton("Borrow Tokens");
		borrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new BorrowFrame();
				dispose();
			}
			
		});
		lendButton = new JButton("Lend Tokens");
		lendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new LendFrame();
				dispose();
			}
			
		});
		
		actionsPanel.add(useButton);
		actionsPanel.add(stakeButton);
		actionsPanel.add(borrowButton);
		actionsPanel.add(lendButton);
		actionsPanel.add(donateButton);
		actionsPanel.add(statisticsButton);
		
		panel.add(userPanel);
		panel.add(recyclePanel);
		panel.add(actionsPanel);
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
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

