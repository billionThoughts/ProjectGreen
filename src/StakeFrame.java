import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class StakeFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, titlePanel, stakePanel, stakingsPanel;
	private JLabel titleLabel, stakeLabel, amountLabel, apyLabel;
	private JTextField amountField;
	private JButton backButton, stakeButton, unstakeButton;
	private JTable stakingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedStaking;
	
	public StakeFrame() {
		selectedStaking = null;
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//title panel
		titlePanel = new JPanel();
		
		titleLabel = new JLabel("Stake Tokens");
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		titlePanel.add(titleLabel);
		titlePanel.add(backButton);
		
		//stake panel
		stakePanel = new JPanel();
		stakeLabel = new JLabel("Stake your tokens to earn passive rewards");
		amountLabel = new JLabel("AMOUNT TO STAKE");
		amountField = new JTextField("amount");
		apyLabel = new JLabel("12% APY");
		stakeButton = new JButton("STAKE");
		stakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int amount = Integer.parseInt(amountField.getText());
				
				if(amount <= signedInAccount.getTokens()) {
					Transaction t = new Staking(amount);
					signedInAccount.makeTransaction(t);
					db.signedInAccountSerialization(signedInAccount);
					stakingsTable.setModel(loadData());
				}
				else JOptionPane.showMessageDialog(null, "You don't have enough tokens");
			}
		});
		
		stakePanel.add(stakeLabel);
		stakePanel.add(amountLabel);
		stakePanel.add(amountField);
		stakePanel.add(apyLabel);
		stakePanel.add(stakeButton);
		
		//stakings panel
		stakingsPanel = new JPanel();
		scrollPane = new JScrollPane();
		//stakingsTable creation
		stakingsTable = new JTable(this.loadData());
		//staking selection
		stakingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = stakingsTable.getSelectionModel();
	    listModel.addListSelectionListener(new StakingSelectionListener());
		
		
		unstakeButton = new JButton("UNSTAKE");
		
		unstakeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedStaking != null) {
					int amount = signedInAccount.undoTransaction(selectedStaking);
					db.signedInAccountSerialization(signedInAccount);
					
					stakingsTable.setModel(loadData());
					JOptionPane.showMessageDialog(null, "Successful unstake, you get " + amount + " tokens!");
					selectedStaking = null;
				}
				else JOptionPane.showMessageDialog(null, "You haven't selected a staking");
			}
		});
		
		scrollPane.getViewport().add(stakingsTable);
		scrollPane.setPreferredSize(new Dimension(400, 183));
		
		stakingsPanel.add(scrollPane);
		stakingsPanel.add(unstakeButton);
		
		panel.add(titlePanel);
		panel.add(stakePanel);
		panel.add(stakingsPanel);
		
		this.setContentPane(panel);
		this.setSize(800, 550);
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
