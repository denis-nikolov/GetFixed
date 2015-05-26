package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrProduct {
	CtrSupplier supplierCtr = new CtrSupplier();
	CtrDepartment departmentCtr = new CtrDepartment();

	public CtrProduct() {

	}

	public ArrayList<Product> findAllProducts() {
		IFDBProduct dbProduct = new DBProduct();
		ArrayList<Product> allProduct = new ArrayList<Product>();
		allProduct = dbProduct.findAllProducts(false);
		return allProduct;
	}

	public ArrayList<Product> findAllProductsByDepartmentId(int departmentId) {
		IFDBProduct dbProduct = new DBProduct();
		ArrayList<Product> allProduct = new ArrayList<Product>();
		allProduct = dbProduct.findAllProductsByDepartmentId(departmentId, false);
		return allProduct;
	}

	public Product findByName(String name) {
		IFDBProduct dbProduct = new DBProduct();
		return dbProduct.searchProductByName(name, true);
	}

	public Product findByBarcode(int barcode) {
		IFDBProduct dbProduct = new DBProduct();
		return dbProduct.searchProductByBarcode(barcode, true);
	}

	public Product findByBarcodeAndDepartmentId(int barcode, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();
		return dbProduct.searchProductByBarcodeAndDepartmentId(barcode, departmentId, true);
	}

	public Product findByBarcodeAndDepartmentName(int barcode, String departmentName) {
		ArrayList<Department> departmentList = departmentCtr.findAllDepartments();
		int departmentId = 0;
		for (Department department : departmentList) {
			if (department.getName().equals(departmentName)) {
				departmentId = department.getId();
			}
		}

		Product product = findByBarcodeAndDepartmentId(barcode, departmentId);
		return product;
	}

	public boolean isAmountAvailable(int amount, Product product) {
		if (product.getAmount() >= amount) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDiscount(int amount, Product product) {
		if (amount >= product.getAmountForDiscount()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAvailable(int barcode) {
		if (findByBarcode(barcode) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLeasable(Product product) {
		if (product.getLeasingPrice() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int saleProductCalculation(int barcode, int amount, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();

		Product product = new Product();
		product = findByBarcodeAndDepartmentId(barcode, departmentId);

		int newAmount = (product.getAmount() - amount);
		product.setAmount(newAmount);

		if (product.getAmount() < product.getMinAmount()) {
			int orderAmount = (product.getMaxAmount() - product.getAmount()) - product.getOrderAmount();
			double price = (product.getOrderingPrice() * orderAmount);
			CtrOrder orderCtr = new CtrOrder();
			try {
				orderCtr.insertOrder(barcode, orderAmount, price, product.getDepartment().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			product.setOrderAmount(product.getMaxAmount() - product.getAmount());
		}
		return dbProduct.recalculateProductAmount(product);
	}
	
	public int leaseProductCalculation(int barcode, int amount, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();

		Product product = new Product();
		product = findByBarcodeAndDepartmentId(barcode, departmentId);

		int newAmount = (product.getAmount() - amount);
		product.setAmount(newAmount);

		return dbProduct.recalculateProductAmount(product);
	}
	
	public int orderProductCalculation(int barcode, int amount, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();

		Product product = new Product();
		product = findByBarcodeAndDepartmentId(barcode, departmentId);

		int newOrderAmount = (product.getOrderAmount() + amount);
		product.setOrderAmount(newOrderAmount);

		return dbProduct.recalculateProductAmount(product);
	}

	public int returnProductCalculation(int barcode, int amount, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();

		Product product = new Product();
		product = findByBarcodeAndDepartmentId(barcode, departmentId);

		int newAmount = (product.getAmount() + amount);
		product.setAmount(newAmount);

		return dbProduct.recalculateProductAmount(product);
	}

	public int receiveProductCalculation(int barcode, int amount, int departmentId) {
		IFDBProduct dbProduct = new DBProduct();

		Product product = new Product();
		product = findByBarcodeAndDepartmentId(barcode, departmentId);

		int newAmount = (product.getAmount() + amount);
		product.setAmount(newAmount);

		int newOrderAmount = (product.getOrderAmount() - amount);
		product.setOrderAmount(newOrderAmount);

		return dbProduct.recalculateProductAmount(product);
	}

	public int updateProduct(int barcode, String name, double sellingPrice, double leasingPrice, double orderingPrice,
			double priceForDiscount, int amountForDiscount, int amount, int minAmount, int maxAmount, int orderAmount,
			int supplierId) {

		IFDBProduct dbProduct = new DBProduct();
		Product product = new Product();

		product.setBarcode(barcode);
		product.setName(name);
		product.setSellingPrice(sellingPrice);
		product.setLeasingPrice(leasingPrice);
		product.setOrderingPrice(orderingPrice);
		product.setPriceForDiscount(priceForDiscount);
		product.setAmountForDiscount(amountForDiscount);
		product.setAmount(amount);
		product.setMinAmount(minAmount);
		product.setMaxAmount(maxAmount);
		product.setOrderAmount(orderAmount);
		product.setSupplier(supplierCtr.findById(supplierId));
		return dbProduct.updateProduct(product);

	}

	public void insertProduct(int barcode, String name, double sellingPrice, double leasingPrice, double orderingPrice,
			double priceForDiscount, int amountForDiscount, int amount, int minAmount, int maxAmount, int orderAmount,
			int departmentId, int supplierId) throws Exception {
		Product product = new Product();
		product.setBarcode(barcode);
		product.setName(name);
		product.setSellingPrice(sellingPrice);
		product.setLeasingPrice(leasingPrice);
		product.setOrderingPrice(orderingPrice);
		product.setPriceForDiscount(priceForDiscount);
		product.setAmountForDiscount(amountForDiscount);
		product.setAmount(amount);
		product.setMinAmount(minAmount);
		product.setMaxAmount(maxAmount);
		product.setOrderAmount(orderAmount);
		product.setDepartment(departmentCtr.findById(departmentId));
		product.setSupplier(supplierCtr.findById(supplierId));
		try {
			DBConnection.startTransaction();
			DBProduct dbProduct = new DBProduct();
			dbProduct.insertProduct(product);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Product not inserted");
		}
	}

	public void deleteProduct(int barcode, int departmentId) throws Exception {

		try {
			DBConnection.startTransaction();
			DBProduct dbProduct = new DBProduct();
			dbProduct.deleteProduct(barcode, departmentId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Product not deleted");
		}
	}

	public ArrayList<Object[]> addAllProductsForDepartment(int departmentId) {
		ArrayList<Product> productList = findAllProductsByDepartmentId(departmentId);
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Product product : productList) {
			object = new Object[] { product.getBarcode(), product.getName(), product.getSellingPrice(),
					product.getLeasingPrice(), product.getOrderingPrice(), product.getPriceForDiscount(),
					product.getAmountForDiscount(), product.getAmount(), product.getMinAmount(),
					product.getMaxAmount(), product.getOrderAmount(), product.getDepartment().getName(),
					product.getSupplier().getId() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public ArrayList<Object[]> addAllProducts() {
		ArrayList<Product> productList = findAllProducts();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Product product : productList) {
			object = new Object[] { product.getBarcode(), product.getName(), product.getSellingPrice(),
					product.getLeasingPrice(), product.getOrderingPrice(), product.getPriceForDiscount(),
					product.getAmountForDiscount(), product.getAmount(), product.getMinAmount(),
					product.getMaxAmount(), product.getOrderAmount(), product.getDepartment().getName(),
					product.getSupplier().getId() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addProductByBarcode(int barcode) {
		Product product = findByBarcode(barcode);
		Object[] object = null;
		object = new Object[] { product.getBarcode(), product.getName(), product.getSellingPrice(),
				product.getLeasingPrice(), product.getOrderingPrice(), product.getPriceForDiscount(),
				product.getAmountForDiscount(), product.getAmount(), product.getMinAmount(), product.getMaxAmount(),
				product.getOrderAmount(), product.getDepartment().getName(), product.getSupplier().getId() };

		return object;
	}

	public Object[] addProductByName(String name) {
		Product product = findByName(name);
		Object[] object = null;
		object = new Object[] { product.getBarcode(), product.getName(), product.getSellingPrice(),
				product.getLeasingPrice(), product.getOrderingPrice(), product.getPriceForDiscount(),
				product.getAmountForDiscount(), product.getAmount(), product.getMinAmount(), product.getMaxAmount(),
				product.getOrderAmount(), product.getDepartment().getName(), product.getSupplier().getId() };

		return object;
	}
}
