import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class LendFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, titlePanel, amountApyPanel, lendPanel, lendingsTablePanel, lendingsPanel;
	private JLabel titleLabel, lendLabel, amountLabel, apyLabel, depositsLabel;
	private JButton backButton, depositButton, withdrawButton;
	private JTextField amountField;
	private JTable lendingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedLending;
	
	public LendFrame() {
		selectedLending = null;
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//title panel
		titlePanel = new JPanel();
		titleLabel = new JLabel("Lend Tokens");
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
		
		//amountField - apyLabel panel
		amountApyPanel = new JPanel();
		amountField = new JTextField("amount");
		apyLabel = new JLabel("19.2% APY");
		
		amountApyPanel.add(amountField);
		amountApyPanel.add(apyLabel);
		
		//lend panel
		lendPanel = new JPanel();
		lendLabel = new JLabel("Lend your tokens to earn passive rewards");
		amountLabel = new JLabel("AMOUNT TO DEPOSIT");
		depositButton = new JButton("DEPOSIT");
		depositButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int amount = Integer.parseInt(amountField.getText());
				
				if(amount <= signedInAccount.getTokens()) {
					Transaction t = new Lending(amount);
					signedInAccount.makeTransaction(t);
					db.signedInAccountSerialization(signedInAccount);
					
					lendingsTable.setModel(loadData());
				}
				else JOptionPane.showMessageDialog(null, "You don't have enough tokens",
						"Lending Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		lendPanel.add(lendLabel);
		lendPanel.add(amountLabel);
		lendPanel.add(amountApyPanel);
		lendPanel.add(depositButton);
		
		//lendingsTable panel
		lendingsTablePanel = new JPanel();
		depositsLabel = new JLabel("CURRENT DEPOSITS");
		scrollPane = new JScrollPane();
		
		//create lendings table
		lendingsTable = new JTable(this.loadData());
		//lending selection
		lendingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = lendingsTable.getSelectionModel();
	    LendingSelectionListener listListener = new LendingSelectionListener();
	    listModel.addListSelectionListener(listListener);
		
		scrollPane.getViewport().add(lendingsTable);
		scrollPane.setMaximumSize(new Dimension(800, 103));
		
		lendingsTablePanel.add(depositsLabel);
		lendingsTablePanel.add(scrollPane);
		
		lendingsTablePanel.setLayout(new BoxLayout(lendingsTablePanel, BoxLayout.Y_AXIS));
		
		//lendings panel
		lendingsPanel = new JPanel();
		withdrawButton = new JButton("WITHDRAW");
		
		withdrawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedLending != null) {
					
					int amount = signedInAccount.undoTransaction(selectedLending);
					db.signedInAccountSerialization(signedInAccount);
					
					lendingsTable.setModel(loadData());
					JOptionPane.showMessageDialog(null, "Successful withdraw, you get back " + amount + " tokens");
					selectedLending = null;
				}
				else JOptionPane.showMessageDialog(null, "You haven't selected a lending",
						"List Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		lendingsPanel.add(lendingsTablePanel);
		lendingsPanel.add(withdrawButton);
		
		panel.add(titlePanel);
		panel.add(lendPanel);
		panel.add(lendingsPanel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.setContentPane(panel);
		
		this.setContentPane(panel);
		this.setSize(800, 550);
		this.setTitle("Borrow Screen");
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
			if(t instanceof Lending) {
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
	
	class LendingSelectionListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			
			ListSelectionModel lsm=(ListSelectionModel) e.getSource();
			  if(!lsm.isSelectionEmpty()){
				  int selectedRow=lsm.getMinSelectionIndex();
					
					if(lendingsTable.getValueAt(selectedRow, 0) != null) {
						JOptionPane.showMessageDialog(null, "You have selected Lending # " 
								+ lendingsTable.getValueAt(selectedRow, 0).toString());
						for(Transaction t : signedInAccount.getTransactions()) {
						String tAmount = Integer.toString(t.getAmount());
						String selectedLendingAmount = lendingsTable.getValueAt(selectedRow, 1).toString();
											
						String tPeriod = t.getStringPeriod();
						String selectedLendingPeriod = lendingsTable.getValueAt(selectedRow, 2).toString();
											
							if((tAmount.equals(selectedLendingAmount))
									&& (tPeriod.equals(selectedLendingPeriod)) && (t instanceof Lending)) {
								selectedLending = t;
							}
						}
					}
					else selectedLending = null;
			  }
		}
	}
}
