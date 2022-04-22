import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InfoFrame extends JFrame {
	private JPanel panel, titlePanel, tablePanel;
	private JLabel titleLabel;
	private JButton backButton;
	private JTable infoTable;
	private JScrollPane scrollPane;
	
	public InfoFrame() {

		panel = new JPanel();
		
		//title panel
		titlePanel = new JPanel();
		backButton = new JButton("Back");
		titleLabel = new JLabel("Info");
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		titlePanel.add(backButton);
		titlePanel.add(titleLabel);
		
		//table panel
		tablePanel = new JPanel();
		scrollPane = new JScrollPane();
		infoTable = new JTable(loadData());
		infoTable.setEnabled(false);
		
		scrollPane.getViewport().add(infoTable);
		scrollPane.setPreferredSize(new Dimension(400, 103));
		
		tablePanel.add(scrollPane);
		
		panel.add(titlePanel);
		panel.add(tablePanel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.setContentPane(panel);
		this.setSize(800, 550);
		this.setTitle("Info Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected DefaultTableModel loadData() {
		String columnNames[] = {"Material", "Token Reward"};
		String data[][] = new String[5][2];
		DataBase db = new DataBase();
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
