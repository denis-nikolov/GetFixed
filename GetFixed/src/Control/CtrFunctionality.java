package Control;

import java.util.*;

public class CtrFunctionality {
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private int clicks = 0;
	private ArrayList<String> values = new ArrayList<String>();
	private boolean product;

	public CtrFunctionality() {
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
}
