import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class BorrowFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, titlePanel, borrowFieldsPanel, amountApyPanel, borrowPanel, borrowingsTablePanel, borrowingsPanel;
	private JLabel titleLabel, borrowLabel, amountLabel, apyLabel, periodLabel, borrowingsLabel;
	private JButton backButton, borrowButton, payBackButton;
	private JTextField amountField, periodField;
	private JTable borrowingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedBorrowing;
	
	public BorrowFrame() {
		selectedBorrowing = null;
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		
		panel = new JPanel();
		
		//title panel
		titlePanel = new JPanel();
		titleLabel = new JLabel("Borrow Tokens");
		backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});

		titlePanel.add(backButton);
		titlePanel.add(titleLabel);
		
		//amount apy panel
		amountApyPanel = new JPanel();
		amountField = new JTextField("Max amount 10.000");
		
		apyLabel = new JLabel("12,5% APY");
		amountApyPanel.add(amountField);
		amountApyPanel.add(apyLabel);
		
		amountField.setAlignmentX(LEFT_ALIGNMENT);
		amountField.setPreferredSize(new Dimension(260,20));
		
		//borrow Fields/labels panel
		borrowFieldsPanel = new JPanel();
		borrowLabel = new JLabel("Borrow tokens from ledger portfolio");
		
		amountLabel = new JLabel("AMOUNT TO BORROW");
		
		periodLabel = new JLabel("PERIOD OF BORROWING");
		periodField = new JTextField("Max 3 months");
		
		
		borrowFieldsPanel.setLayout(new BoxLayout(borrowFieldsPanel, BoxLayout.Y_AXIS));
		borrowFieldsPanel.add(borrowLabel);
		borrowFieldsPanel.add(amountLabel);
		borrowFieldsPanel.add(amountApyPanel);
		
		borrowFieldsPanel.add(periodLabel);
		borrowFieldsPanel.add(periodField);
		
		borrowLabel.setAlignmentX(LEFT_ALIGNMENT);
		amountLabel.setAlignmentX(LEFT_ALIGNMENT);
		amountApyPanel.setAlignmentX(LEFT_ALIGNMENT);
		periodLabel.setAlignmentX(LEFT_ALIGNMENT);
		periodField.setAlignmentX(LEFT_ALIGNMENT);
		
		//borrow panel
		borrowPanel = new JPanel();
		borrowButton = new JButton("BORROW");
		
		borrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int amount = Integer.parseInt(amountField.getText());
				int period = Integer.parseInt(periodField.getText());
				
				if(amount > 10000 || period > 3) {
					JOptionPane.showMessageDialog(null, "Amount or period not accepted");
				}
				else {
					if((signedInAccount.calculateTotalBorrowings() + amount) > 10000) {
						JOptionPane.showMessageDialog(null, "Borrow Limit 10000");
					}
					else {
						Transaction t = new Borrowing(amount, period);
						signedInAccount.makeTransaction(t);
						db.signedInAccountSerialization(signedInAccount);
						
						borrowingsTable.setModel(loadData());
					}
				}
			}
		});
		
		borrowPanel.add(borrowFieldsPanel);
		borrowPanel.add(borrowButton);
		
		borrowFieldsPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		borrowButton.setAlignmentX(RIGHT_ALIGNMENT);
		borrowButton.setAlignmentY(BOTTOM_ALIGNMENT);
		
		//borrowings table panel
		borrowingsTablePanel = new JPanel();
		borrowingsLabel = new JLabel("CURENT BORROWING");
		scrollPane = new JScrollPane();
		
		//create borrowingTable
		borrowingsTable = new JTable(this.loadData());
		//borrowing selection
		borrowingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = borrowingsTable.getSelectionModel();
	    BorrowingSelectionListener listListener = new BorrowingSelectionListener();
	    listModel.addListSelectionListener(listListener);
		
		scrollPane.getViewport().add(borrowingsTable);
		scrollPane.setMaximumSize(new Dimension(800, 103));
		
		borrowingsTablePanel.add(borrowingsLabel);
		borrowingsTablePanel.add(scrollPane);
		
		
		borrowingsTablePanel.setLayout(new BoxLayout(borrowingsTablePanel, BoxLayout.Y_AXIS));
		
		//borrowings panel
		borrowingsPanel = new JPanel();
		
		payBackButton = new JButton("PAY BACK");
		
		payBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedBorrowing != null) {
					if(signedInAccount.isPayBackAffordable(selectedBorrowing)) {
						int amount = signedInAccount.undoTransaction(selectedBorrowing);
						db.signedInAccountSerialization(signedInAccount);
					
						borrowingsTable.setModel(loadData());
						JOptionPane.showMessageDialog(null, "Successful pay back, you paid " + amount + " tokens");
						selectedBorrowing = null;
					}
					else JOptionPane.showMessageDialog(null, "You don't have enought tokens.",
							"Pay Back Error", JOptionPane.ERROR_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "You haven't selected a borrowing",
						"Borrowing Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		borrowingsPanel.setLayout(new BoxLayout(borrowingsPanel, BoxLayout.LINE_AXIS));
		borrowingsPanel.add(borrowingsTablePanel);
		borrowingsPanel.add(payBackButton);
		
		panel.add(titlePanel);
		panel.add(borrowPanel);
		panel.add(borrowingsPanel);
		
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
		
		String columnNames[] = {"#", "Amount", "Period", "APY", "Total Payback"};
		String data[][] = new String[5][5];
		
		//get data from signedInAccount
		int i=0;
		int j=0;
		for(Transaction t : signedInAccount.getTransactions()) {
			if(t instanceof Borrowing) {
				data[i][j] = Integer.toString(i+1);
				data[i][j+1] = Integer.toString(t.getAmount());
				data[i][j+2] = t.getStringPeriod();
				data[i][j+3] = Double.toString(t.getAPY());
				data[i][j+4] = Integer.toString(t.getTotalAmount());
				i++;
			}
		}
		
		return new DefaultTableModel(data, columnNames);
	}
	
class BorrowingSelectionListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			
			ListSelectionModel lsm=(ListSelectionModel) e.getSource();
			  if(!lsm.isSelectionEmpty()){
				  int selectedRow=lsm.getMinSelectionIndex();
					
					if(borrowingsTable.getValueAt(selectedRow, 0) != null) {
						JOptionPane.showMessageDialog(null, "You have selected Borrowing # " 
								+ borrowingsTable.getValueAt(selectedRow, 0).toString());
						for(Transaction t : signedInAccount.getTransactions()) {
						String tAmount = Integer.toString(t.getAmount());
						String selectedBorrowingAmount = borrowingsTable.getValueAt(selectedRow, 1).toString();
											
						String tPeriod = t.getStringPeriod();
						String selectedBorrowingPeriod = borrowingsTable.getValueAt(selectedRow, 2).toString();
											
							if((tAmount.equals(selectedBorrowingAmount))
									&& (tPeriod.equals(selectedBorrowingPeriod)) && (t instanceof Borrowing)) {
								selectedBorrowing = t;
							}
						}
					}
					else selectedBorrowing = null;
			  }
		}
	}
}
