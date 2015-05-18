package Model;

public class PartSale {

	private Sale sale;
	private Product product;
	private Service service;
	private int barcode;
	private String name;
	private double pricePerPiece;
	private int amount;
	private double price;

	public PartSale() {

	}

	public PartSale(Sale sale, Product product, double pricePerPiece,
			int amount, int price) {
		this.setSale(sale);
		this.setProduct(product);
		barcode = getProduct().getBarcode();
		name = getProduct().getName();
		this.setAmount(amount);
		this.setPricePerPiece(pricePerPiece);
		this.setPrice(price);
	}
	
	public PartSale(Sale sale, Service service, double pricePerPiece,
			int amount, int price) {
		this.setSale(sale);
		this.setService(service);
		barcode = getService().getBarcode();
		name = getService().getName();
		this.setAmount(amount);
		this.setPricePerPiece(pricePerPiece);
		this.setPrice(price);
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	
}
