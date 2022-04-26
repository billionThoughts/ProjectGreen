import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class StakeFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel titleLabel, stakeLabel, amountLabel, apyLabel, backgroundIconLabel;
	private JTextField amountField;
	private JButton homeButton, stakeButton, unstakeButton;
	private JTable stakingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedStaking;
	
	public StakeFrame() {
		selectedStaking = null;
		DataBase db = DataBase.getInstance();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
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
		
		titleLabel = new JLabel("Stake Tokens");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(0, 11, 827, 35);
		panel.add(titleLabel);
		
		stakeLabel = new JLabel("Stake your tokens to earn passive rewards");
		stakeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		stakeLabel.setBounds(109, 86, 264, 14);
		panel.add(stakeLabel);
		
		amountLabel = new JLabel("AMOUNT TO STAKE");
		amountLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		amountLabel.setBounds(109, 140, 117, 14);
		panel.add(amountLabel);
		
		amountField = new JTextField();
		amountField.setBounds(108, 156, 181, 20);
		amountField.setColumns(10);
		panel.add(amountField);
		
		apyLabel = new JLabel("12% APY");
		apyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apyLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		apyLabel.setBounds(299, 159, 78, 14);
		panel.add(apyLabel);
		
		stakeButton = new JButton("Stake");
		stakeButton.setBackground(new Color(0, 204, 255));
		stakeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		stakeButton.setBounds(654, 129, 117, 46);
		panel.add(stakeButton);
		
		//ActionListener for stakeButton
		stakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int amount = Integer.parseInt(amountField.getText());
				
				if(amount <= signedInAccount.getTokens()) {
					Transaction t = new Staking(amount);
					signedInAccount.makeTransaction(t);
					db.saveSignedInAccount(signedInAccount);
					stakingsTable.setModel(loadData());
				}
				else JOptionPane.showMessageDialog(null, "You don't have enough tokens",
						"Staking Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		stakingsTable = new JTable(this.loadData());
		
		//staking selection
		stakingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = stakingsTable.getSelectionModel();
	    listModel.addListSelectionListener(new StakingSelectionListener());
	    
	    scrollPane = new JScrollPane();
		scrollPane.getViewport().add(stakingsTable);
		scrollPane.setPreferredSize(new Dimension(400, 183));
		scrollPane.setBounds(109, 256, 435, 103);
		panel.add(scrollPane);
		
		unstakeButton = new JButton("Unstake");
		unstakeButton.setBackground(new Color(0, 204, 255));
		unstakeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		unstakeButton.setBounds(654, 274, 117, 46);
		panel.add(unstakeButton);
		
		//ActionListener for unstakeButton
		unstakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedStaking != null) {
					int amount = signedInAccount.undoTransaction(selectedStaking);
					db.saveSignedInAccount(signedInAccount);
					
					stakingsTable.setModel(loadData());
					JOptionPane.showMessageDialog(null, "Successful unstake, you get " + amount + " tokens!");
					selectedStaking = null;
				}
				else JOptionPane.showMessageDialog(null, "You haven't selected a staking",
						"Staking Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 870, 520);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Stake Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected DefaultTableModel loadData() {
		
		String columnNames[] = {"#", "Amount", "Period", "APY", "Reward", "Total Amount"};
		String data[][] = new String[10][6];
		
		//get data from signedInAccount
		int i=0;
		int j=0;
		for(Transaction t : signedInAccount.getTransactions()) {
			if(t instanceof Staking) {
				data[i][j] = Integer.toString(i+1);
				data[i][j+1] = Integer.toString(t.getAmount());
				data[i][j+2] = t.getStringPeriod();
				data[i][j+3] = Double.toString(t.getAPY());
				data[i][j+4] = Integer.toString(t.getInterestAmount());
				data[i][j+5] = Integer.toString(t.getTotalAmount());
				i++;
			}
		}
		return new DefaultTableModel(data, columnNames);
	}
	
	class StakingSelectionListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if(!lsm.isSelectionEmpty()){
				int selectedRow = lsm.getMinSelectionIndex();

				if((stakingsTable.getValueAt(selectedRow, 0) != null)) {
					
					JOptionPane.showMessageDialog(null, "You have selected Staking # " 
							+ stakingsTable.getValueAt(selectedRow, 0).toString());
					
					for(Transaction t : signedInAccount.getTransactions()) {
						String tAmount = Integer.toString(t.getAmount());
						String selectedStakingAmount = stakingsTable.getValueAt(selectedRow, 1).toString();		
						String tPeriod = t.getStringPeriod();
						String selectedStakingPeriod = stakingsTable.getValueAt(selectedRow, 2).toString();
											
						if((tAmount.equals(selectedStakingAmount)) 
								&& (tPeriod.equals(selectedStakingPeriod)) && (t instanceof Staking)) {
							selectedStaking = t;
						}
					}
				}
				else selectedStaking = null;
			 }
		}
	}
}
