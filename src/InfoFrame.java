import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InfoFrame extends JFrame {
	private JPanel panel;
	private JLabel titleLabel, backgroundIconLabel;
	private JButton backButton;
	private JTable infoTable;
	private JScrollPane scrollPane;
	
	public InfoFrame() {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Info");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(0, 11, 784, 36);
		panel.add(titleLabel);
		
		backButton = new JButton("Home");
		backButton.setBackground(new Color(255, 153, 102));
		backButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		backButton.setBounds(10, 455, 97, 36);
		panel.add(backButton);
		
		//ActionListeber for backButton
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 150, 465, 103);
		infoTable = new JTable(loadData());
		infoTable.setEnabled(false);
		scrollPane.setViewportView(infoTable);
		//scrollPane.setPreferredSize(new Dimension(400, 103));
		panel.add(scrollPane);
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 860, 515);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Info Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected DefaultTableModel loadData() {
		String columnNames[] = {"Material", "Token Reward"};
		String data[][] = new String[5][2];
		DataBase db = DataBase.getInstance();
		int i=0;
		int j=0;
		
		for(Material m: db.getMaterials()) {
			data[i][j] = m.getName();
			data[i][j+1] = Integer.toString(m.getReward());
			i++;
		}
		return new DefaultTableModel(data, columnNames);
	}
}
