package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private int id;
	private String date;
	private Product product;
	private String productName;
	private double pricePerPiece;
	private int amount;
	private double price;
	
	public Order(){
		
	}
	
	public Order(int id, Product product, String productName, double pricePerPiece, int amount){
		this.id = id;
		date = createDate();
		this.product = product;
		productName = getProduct().getName();
		pricePerPiece = getProduct().getOrderingPrice();
		this.amount = amount;
	    price = getPrice();
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

	public double getPricePerPiece() {
		return pricePerPiece;
	}

	public void setPricePerPiece(double pricePerPiece) {
		this.pricePerPiece = pricePerPiece;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
