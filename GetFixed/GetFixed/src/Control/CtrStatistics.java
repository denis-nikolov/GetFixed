package Control;

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

import UI.BarChart;
import DB.DBConnection;
import DB.DBStatistics;

public class CtrStatistics {
	CtrProduct productCtr = new CtrProduct();
	CtrService serviceCtr = new CtrService();
	
	
	  public DefaultCategoryDataset getMostSoldProduct() {
	    	Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
	    	 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    	 final String category1 = "Most sold Products";
	    	 
	    	try {
				DBConnection.startTransaction();
				DBStatistics dbStatistics = new DBStatistics();
				myMap = dbStatistics.getTopProducts();
				DBConnection.commitTransaction();
			} catch (Exception e) {
				DBConnection.rollbackTransaction();
			}
	    	
	    	for (Map.Entry<Integer, Integer> entry : myMap.entrySet())
	    	{
	    	    System.out.println(entry.getKey() + "/" + entry.getValue());
	    	    dataset.addValue(entry.getKey(), productCtr.findByBarcode(entry.getValue()).getName(), category1);
	    	}
	    	
	    	
	        return dataset;

	    }
	  
	  
	  public DefaultCategoryDataset getMostSoldService() {
	    	Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
	    	 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    	 final String category1 = "Most sold Services";
	    	 
	    	try {
				DBConnection.startTransaction();
				DBStatistics dbStatistics = new DBStatistics();
				myMap = dbStatistics.getTopServices();
				DBConnection.commitTransaction();
			} catch (Exception e) {
				DBConnection.rollbackTransaction();
			}
	    	
	    	for (Map.Entry<Integer, Integer> entry : myMap.entrySet())
	    	{
	    	    System.out.println(entry.getKey() + "/" + entry.getValue());
	    	    dataset.addValue(entry.getKey(), serviceCtr.findByBarcode(entry.getValue()).getName(), category1);
	    	}
	    	
	    	
	        return dataset;

	    }
	
}
