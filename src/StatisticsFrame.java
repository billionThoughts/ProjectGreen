import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsFrame extends JFrame {
	private UserAccount signedInAccount;
	private JPanel panel, diagramPanel;
	private JLabel titleLabel, statisticsLabel;
	private JButton homeButton;
	private JFreeChart barChart;
	private ChartPanel chartPanel;
	private HashMap<Material, Integer> recycled;
	private JLabel backgroundIconLabel;
	
	public StatisticsFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		recycled = signedInAccount.getRecycled();
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		titleLabel = new JLabel("Statistics");
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
				new HomeFrame();
				dispose();
			}
		});
		
		statisticsLabel = new JLabel("Material Statistics of your account");
		statisticsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		statisticsLabel.setBounds(91, 68, 253, 14);
		panel.add(statisticsLabel);
		
	    barChart = ChartFactory.createBarChart("Bar Chart", "Materials", "Quantity", loadData(), PlotOrientation.VERTICAL,           
	             true, true, false);
	    
	    // get a reference to the plot for further customisation
        CategoryPlot plot = barChart.getCategoryPlot();
        
        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    
	    // disable bar outlines
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.10);
        
        // set up gradient paints for series
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, Color.lightGray);
        renderer.setSeriesPaint(0, gp);
        
	    chartPanel = new ChartPanel(barChart);
	    chartPanel.setBounds(80, 100, 698, 344);
	    //chartPanel.setMaximumSize(new Dimension(300, 300));
		panel.add(chartPanel);
		
		backgroundIconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/background.jpg")));
		backgroundIconLabel.setBounds(0, 0, 870, 520);
		panel.add(backgroundIconLabel);
		
		this.setContentPane(panel);
		
		this.setSize(870, 545);
		this.setResizable(false);
		this.setTitle("Statistics Screen");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected DefaultCategoryDataset loadData() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		Iterator<Material> it = recycled.keySet().iterator();
		
		while(it.hasNext()) {
			Material key = it.next();
			dataset.addValue(recycled.get(key), "Materials", key.getName());
		}
		return dataset;
	}
}
