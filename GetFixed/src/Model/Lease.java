package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lease {
	private int id;
	private String date;
	private Customer customer;
	private Department department;
	private int period;
	private double price;
	private boolean returned;

	public Lease(){
		
	}
	
	public Lease(int id, Customer customer, Department department, int period) {
		this.setId(id);
		date = createDate();
		this.customer = customer;
		this.department = department;
		this.period = period;
		price = getPrice();
		returned = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String createDate() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFormat = new Date();
		String stringDate = sdf.format(dateFormat);
		return stringDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
