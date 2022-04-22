import java.awt.Color;
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
	private JPanel panel, titlePanel, diagramPanel;
	private JLabel titleLabel, statisticsLabel;
	private JButton backButton;
	private JFreeChart barChart;
	private ChartPanel chartPanel;
	private HashMap<Material, Integer> recycled;
	
	public StatisticsFrame() {
		DataBase db = new DataBase();
		signedInAccount = db.signedInAccountDeserialization();
		recycled = signedInAccount.getRecycled();
		
		panel = new JPanel();
		
		//title panel
		titlePanel = new JPanel();
		backButton = new JButton("Back");
		titleLabel = new JLabel("Statistics");
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HomeFrame();
				dispose();
			}
		});
		
		titlePanel.add(backButton);
		titlePanel.add(titleLabel);
		
		//diagram panel
		diagramPanel = new JPanel();
		statisticsLabel = new JLabel("Material Statistics of your account");
		
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
	    
		diagramPanel.add(statisticsLabel);
		diagramPanel.add(chartPanel);
		
		panel.add(titlePanel);
		panel.add(diagramPanel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.setContentPane(panel);
		
		this.setSize(800, 550);
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
