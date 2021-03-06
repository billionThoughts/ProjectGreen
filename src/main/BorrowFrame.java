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

public class BorrowFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private UserAccount signedInAccount;
	private JPanel panel;
	private JLabel titleLabel, borrowLabel, amountLabel, apyLabel, periodLabel, borrowingsLabel, 
					backgroundIconLabel, maxPeriodLabel, maxAmount;
	private JButton homeButton, borrowButton, payBackButton;
	private JTextField amountField, periodField;
	private JTable borrowingsTable;
	private JScrollPane scrollPane;
	private Transaction selectedBorrowing;
	
	public BorrowFrame(UserAccount signedInAccount) {
		selectedBorrowing = null;
		DataBase db = DataBase.getInstance();
		this.signedInAccount = signedInAccount;
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Borrow Tokens");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 11, 856, 37);
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
				new HomeFrame(signedInAccount);
				dispose();
			}
		});
		
		borrowLabel = new JLabel("Borrow tokens from ledger portofolio");
		borrowLabel.setHorizontalAlignment(SwingConstants.LEFT);
		borrowLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		borrowLabel.setBounds(87, 99, 236, 14);
		panel.add(borrowLabel);
		
		amountLabel = new JLabel("AMOUNT TO BORROW");
		amountLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		amountLabel.setBounds(87, 142, 194, 14);
		panel.add(amountLabel);
		
		apyLabel = new JLabel("12,5% APY");
		apyLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		apyLabel.setBounds(308, 139, 69, 20);
		panel.add(apyLabel);
		
		amountField = new JTextField();
		amountField.setBounds(87, 156, 199, 20);
		amountField.setColumns(10);
		panel.add(amountField);
		
		maxAmount = new JLabel("Max amount 10000");
		maxAmount.setBounds(308, 159, 170, 14);
		panel.add(maxAmount);
		
		periodLabel = new JLabel("PERIOD OF BORROWING");
		periodLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		periodLabel.setBounds(87, 191, 194, 14);
		panel.add(periodLabel);
		
		periodField = new JTextField();
		periodField.setBounds(87, 205, 199, 20);
		periodField.setColumns(10);
		panel.add(periodField);
		
		maxPeriodLabel = new JLabel("Max period 3 months");
		maxPeriodLabel.setBounds(308, 208, 170, 17);
		panel.add(maxPeriodLabel);
		
		borrowButton = new JButton("Borrow");
		borrowButton.setBackground(new Color(0, 204, 255));
		borrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		borrowButton.setBounds(623, 174, 137, 49);
		panel.add(borrowButton);
		
		//ActionListener for borrowButton
		borrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if((amountField.getText().length() != 0) && (periodField.getText().length() != 0)) {
					int amount = Integer.parseInt(amountField.getText());
					int period = Integer.parseInt(periodField.getText());
					
					if(amount > 10000 || period > 3) {
						JOptionPane.showMessageDialog(null, "Amount or period not accepted");
					}
					else {
						if((signedInAccount.calculateTotalBorrowings() + amount) > 10000) {
							JOptionPane.showMessageDialog(null, "Borrow Limit 10000 Tokens", 
									"Borrow Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							Transaction t = new Borrowing(amount, period);
							signedInAccount.makeTransaction(t);
							db.saveSignedInAccount(signedInAccount);
							
							borrowingsTable.setModel(loadData());
							customizeTable();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please insert amount and period", 
							"Borrow Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		borrowingsLabel = new JLabel("CURRENT BORROWINGS");
		borrowingsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		borrowingsLabel.setBounds(87, 275, 229, 14);
		panel.add(borrowingsLabel);
		
		scrollPane = new JScrollPane();
		borrowingsTable = new JTable(this.loadData());
		customizeTable();
		//borrowing selection
		borrowingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ListSelectionModel listModel = borrowingsTable.getSelectionModel();
	    BorrowingSelectionListener listListener = new BorrowingSelectionListener();
	    listModel.addListSelectionListener(listListener);
		scrollPane.setViewportView(borrowingsTable);
		scrollPane.setMaximumSize(new Dimension(800, 103));
		scrollPane.setBounds(87, 299, 429, 103);
		panel.add(scrollPane);
		
		payBackButton = new JButton("Pay Back");
		payBackButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		payBackButton.setBackground(new Color(0, 204, 255));
		payBackButton.setBounds(623, 319, 137, 49);
		panel.add(payBackButton);
		
		//ActionListener for payBackButton
		payBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedBorrowing != null) {
					if(signedInAccount.isPayBackAffordable(selectedBorrowing)) {
						int amount = signedInAccount.undoTransaction(selectedBorrowing);
						db.saveSignedInAccount(signedInAccount);
					
						borrowingsTable.setModel(loadData());
						customizeTable();
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
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 870, 520);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Borrow Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private DefaultTableModel loadData() {
		
		String[] columnNames = {"#", "Amount", "Period", "APY", "Total Payback"};
		String[][] data = new String[5][5];
		
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
	
	private void customizeTable() {
		borrowingsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		borrowingsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		borrowingsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		borrowingsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
		borrowingsTable.getColumnModel().getColumn(3).setPreferredWidth(45);
		borrowingsTable.getColumnModel().getColumn(4).setPreferredWidth(116);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0; i<borrowingsTable.getColumnCount(); i++) {
			borrowingsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	class BorrowingSelectionListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			
			ListSelectionModel lsm=(ListSelectionModel) e.getSource();
			  if(!lsm.isSelectionEmpty()){
				  int selectedRow=lsm.getMinSelectionIndex();
					
					if(borrowingsTable.getValueAt(selectedRow, 0) != null) {
						String amount = borrowingsTable.getValueAt(selectedRow, 1).toString();
						String period = borrowingsTable.getValueAt(selectedRow, 2).toString();
						selectedBorrowing = signedInAccount.getSelectedTransaction(amount, period, "class main.Borrowing");
					}
					else selectedBorrowing = null;
			  }
		}
	}
}
