package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LendFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel titleLabel, lendLabel, amountLabel, apyLabel, depositsLabel, backgroundIconLabel;
	private JButton homeButton, depositButton, withdrawButton;
	private JTextField amountField;
	private JTable lendingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedLending;
	
	public LendFrame(UserAccount signedInAccount) {
		selectedLending = null;
		DataBase db = DataBase.getInstance();
		this.signedInAccount = signedInAccount;
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Lend Tokens");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(0, 11, 856, 36);
		panel.add(titleLabel);
		
		homeButton = new JButton("Home");
		homeButton.setBackground(new Color(255, 153, 102));
		homeButton.setBounds(10, 455, 97, 36);
		panel.add(homeButton);
		
		//ActionListener for homeButton
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame(signedInAccount);
				dispose();
			}
		});
		
		lendLabel = new JLabel("Lend your tokens to earn passive rewards");
		lendLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lendLabel.setBounds(91, 94, 253, 14);
		panel.add(lendLabel);
		
		amountLabel = new JLabel("AMOUNT TO DEPOSIT");
		amountLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		amountLabel.setBounds(91, 143, 161, 14);
		panel.add(amountLabel);
		
		amountField = new JTextField();
		amountField.setBounds(91, 158, 190, 20);
		amountField.setColumns(10);
		panel.add(amountField);
		
		apyLabel = new JLabel("19,2% APY");
		apyLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		apyLabel.setBounds(291, 158, 86, 20);
		panel.add(apyLabel);
		
		depositButton = new JButton("Deposit");
		depositButton.setBackground(new Color(0, 204, 255));
		depositButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		depositButton.setBounds(604, 143, 137, 49);
		panel.add(depositButton);
		
		//ActionListener for depositButton
		depositButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(amountField.getText().length() != 0){
					int amount = Integer.parseInt(amountField.getText());
					
					if(amount <= signedInAccount.getTokens()) {
						Transaction t = new Lending(amount);
						signedInAccount.makeTransaction(t);
						db.saveSignedInAccount(signedInAccount);
						
						lendingsTable.setModel(loadData());
						customizeTable();
					}
					else {
						JOptionPane.showMessageDialog(null, "You don't have enough tokens",
								"Lending Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please insert amount", 
							"Lend Error", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		depositsLabel = new JLabel("CURRENT DEPOSITS");
		depositsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		depositsLabel.setBounds(91, 261, 161, 14);
		panel.add(depositsLabel);

		scrollPane = new JScrollPane();
		lendingsTable = new JTable(this.loadData());
		customizeTable();
		
		//lending selection
		lendingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = lendingsTable.getSelectionModel();
	    LendingSelectionListener listListener = new LendingSelectionListener();
	    listModel.addListSelectionListener(listListener);
		scrollPane.setViewportView(lendingsTable);
		scrollPane.setPreferredSize(new Dimension(400, 183));
		scrollPane.setBounds(91, 281, 435, 103);
		panel.add(scrollPane);
		
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setBackground(new Color(0, 204, 255));
		withdrawButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		withdrawButton.setBounds(604, 296, 137, 49);
		panel.add(withdrawButton);
		
		//ActionListener for withdrawButton
		withdrawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedLending != null) {
					
					int amount = signedInAccount.undoTransaction(selectedLending);
					db.saveSignedInAccount(signedInAccount);
					
					lendingsTable.setModel(loadData());
					customizeTable();
					JOptionPane.showMessageDialog(null, "Successful withdraw, you get back " + amount + " tokens");
					selectedLending = null;
				}
				else JOptionPane.showMessageDialog(null, "You haven't selected a lending",
						"List Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 870, 520);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Lend Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private DefaultTableModel loadData() {
		
		String[] columnNames = {"#", "Amount", "Period", "APY", "Reward", "Total Amount"};
		String[][] data = new String[10][6];
		
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
	
	private void customizeTable() {
		lendingsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		lendingsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		lendingsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		lendingsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
		lendingsTable.getColumnModel().getColumn(3).setPreferredWidth(38);
		lendingsTable.getColumnModel().getColumn(4).setPreferredWidth(64);
		lendingsTable.getColumnModel().getColumn(5).setPreferredWidth(85);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0; i<lendingsTable.getColumnCount(); i++) {
			lendingsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	class LendingSelectionListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			
			ListSelectionModel lsm=(ListSelectionModel) e.getSource();
			  if(!lsm.isSelectionEmpty()){
				  int selectedRow=lsm.getMinSelectionIndex();
					
					if(lendingsTable.getValueAt(selectedRow, 0) != null) {
						String amount = lendingsTable.getValueAt(selectedRow, 1).toString();
						String period = lendingsTable.getValueAt(selectedRow, 2).toString();
						selectedLending = signedInAccount.getSelectedTransaction(amount, period, "class main.Lending");
					}
					else selectedLending = null;
			  }
		}
	}
}
