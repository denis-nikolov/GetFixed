package Control;

import java.util.*;

public class CtrFunctionality {
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private int clicks = 0;
	private int departmentId;
	private int addWidth;
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<String> saleId = new ArrayList<String>();
	private ArrayList<String> leaseId = new ArrayList<String>();
	private boolean product;

	public CtrFunctionality() {
		addWidth = 200;
	}

	public ArrayList<Integer> getIds() {
		return ids;
	}

	public void removeId(int id) {
		if (getIds().contains(id)) {
			getIds().remove(id);
		}
	}

	public void removeAllIds() {
		getIds().clear();
	}

	public void addId(int id) {
		getIds().add(id);
	}

	public int getClicks() {
		return clicks;
	}

	public void addClick() {
		clicks++;
	}

	public void removeAllClicks() {
		clicks = 0;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void addValue(String value) {
		getValues().add(value);
	}
	
	public void removeValue(String value) {
		if (getValues().contains(value)) {
			getValues().remove(value);
		}
	}

	public void removeAllValues() {
		getValues().clear();
	}

	public boolean isProduct(int barcode) {
		if(barcode < 5000){
			product = true;
		}else{
			product = false;
		}
		return product;
	}
	
	public boolean isProduct(){
		return product;
	}

	public ArrayList<String> getSaleId() {
		return saleId;
	}

	public void setSaleId(ArrayList<String> saleId) {
		this.saleId = saleId;
	}

	public ArrayList<String> getLeaseId() {
		return leaseId;
	}

	public void setLeaseId(ArrayList<String> leaseId) {
		this.leaseId = leaseId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getAddWidth() {
		return addWidth;
	}

	public void setAddWidth(int addWidth) {
		this.addWidth = addWidth;
	}
}
