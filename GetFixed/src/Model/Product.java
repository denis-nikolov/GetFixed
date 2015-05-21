package Model;

public class Product {
	private int barcode;
	private String name;
	private double sellingPrice;
	private double leasingPrice;
	private double orderingPrice;
	private double priceForDiscount;
	private int amountForDiscount;
	private int amount;
	private int minAmount;
	private int maxAmount;
	private int orderAmount;
	private Supplier supplier;
	private Department department;

	public Product() {

	}

	public Product(int barcode, String name, double sellingPrice,
			double leasingPrice, double orderingPrice, double priceForDiscount,
			int amountForDiscount, int amount, int minAmount, int maxAmount,
			int orderAmount, Supplier supplier, Department department) {
		this.barcode = barcode;
		this.name = name;
		this.sellingPrice = sellingPrice;
		this.leasingPrice = leasingPrice;
		this.orderingPrice = orderingPrice;
		this.priceForDiscount = priceForDiscount;
		this.amountForDiscount = amountForDiscount;
		this.amount = amount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.orderAmount = orderAmount;
		this.supplier = supplier;
		this.department = department;
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

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public double getLeasingPrice() {
		return leasingPrice;
	}

	public void setLeasingPrice(double leasingPrice) {
		this.leasingPrice = leasingPrice;
	}

	public double getPriceForDiscount() {
		return priceForDiscount;
	}

	public void setPriceForDiscount(double priceForDiscount) {
		this.priceForDiscount = priceForDiscount;
	}

	public int getAmountForDiscount() {
		return amountForDiscount;
	}

	public void setAmountForDiscount(int amountForDiscount) {
		this.amountForDiscount = amountForDiscount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public double getOrderingPrice() {
		return orderingPrice;
	}

	public void setOrderingPrice(double orderingPrice) {
		this.orderingPrice = orderingPrice;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
