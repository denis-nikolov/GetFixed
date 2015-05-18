package Model;

public class Service {
	private int barcode;
	private String name;
	private double price;
	
	public Service(){
		
	}

	public Service(int barcode, String name, double price) {
		this.barcode = barcode;
		this.name = name;
		this.price = price;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
