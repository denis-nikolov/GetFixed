package UI;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.jfree.data.category.DefaultCategoryDataset;

import Control.*;

public class StatisticsUI {
	private JTable table;
	private JPanel contentPanel;
	private JPanel secondaryMenuPanel;
	private CtrStatistics ctrStatistics = new CtrStatistics();
	
	public StatisticsUI	(JPanel contentPanel, JPanel secondaryMenuPanel){
		this.contentPanel = contentPanel;
		this.secondaryMenuPanel = secondaryMenuPanel;
	}
	

    void makeMostSoldProduct() {
    	final DefaultCategoryDataset dataset = ctrStatistics.getMostSoldProduct();
         new BarChart("GetFixed",dataset ,"", "Products","Quantity", contentPanel);
    }
    void makeMostSoldService() {

    	final DefaultCategoryDataset dataset = ctrStatistics.getMostSoldService();
    	new BarChart("GetFixed", dataset,"", "Services","Quantity", contentPanel);
    }

    void makeDepartmentSales() {
    	final DefaultCategoryDataset dataset = ctrStatistics.getDepartmentSales();
    	new BarChart("GetFixed", dataset,"", "Department","DKK", contentPanel);
    }
}
