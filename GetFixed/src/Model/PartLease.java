package Model;

public class PartLease {

	private Lease lease;
	private Product product;
	private int barcode;
	private String name;
	private double pricePerPiece;
	private int amount;
	private double price;

	public PartLease() {
	}

	public PartLease(Lease lease, Product product, double pricePerPiece, int amount, int price) {
		this.setLease(lease);
		this.setProduct(product);
		barcode = getProduct().getBarcode();
		name = getProduct().getName();
		this.setAmount(amount);
		this.setPricePerPiece(pricePerPiece);
		this.setPrice(price);
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
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
