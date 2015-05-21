package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrSale {

	CtrCustomer customerCtr = new CtrCustomer();
	CtrProduct productCtr = new CtrProduct();
	CtrService serviceCtr = new CtrService();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrFunctionality funcionalityCtr = new CtrFunctionality();

	/** Creates a new instance of CtrSale */
	public CtrSale() {

	}

	public ArrayList<Sale> findAllSales() {
		IFDBSale dbSale = new DBSale();
		ArrayList<Sale> allSale = new ArrayList<Sale>();
		allSale = dbSale.getAllSale(false);
		return allSale;
	}

	public ArrayList<Sale> findAllSalesByDepartmentId(int departmentId) {
		IFDBSale dbSale = new DBSale();
		ArrayList<Sale> allSale = new ArrayList<Sale>();
		allSale = dbSale.findAllSalesByDepartmentId(departmentId, false);
		return allSale;
	}

	public Sale findById(int id) {
		IFDBSale dbSale = new DBSale();
		return dbSale.searchSaleId(id, true);
	}

	public int insertSale(int customerId, int departmentId, double totalPrice) throws Exception {
		Sale sale = new Sale();
		sale.setCustomer(customerCtr.findById(customerId));
		sale.setDepartment(departmentCtr.findById(departmentId));
		sale.setTotalPrice(totalPrice);

		try {
			DBConnection.startTransaction();
			DBSale dbSale = new DBSale();
			int id = dbSale.insertSale(sale);
			DBConnection.commitTransaction();
			return id;
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Sale not inserted");
		}
	}

	public double calculatePrice(double pricePerPiece, int amount) {
		double price = 0;
		price = pricePerPiece * amount;
		return price;
	}

	public double calculateDiscount(int customerId, double subTotal) {
		double discount = 0;
		int percent = customerCtr.findById(customerId).getDiscount();
		discount = (subTotal * percent) / 100;
		return discount;
	}

	public double calculateTotalPrice(double subTotal, double discount) {
		double total = 0;
		total = subTotal - discount;
		return total;
	}

	public void deleteSale(int id) throws Exception {

		try {
			DBConnection.startTransaction();
			DBSale dbSale = new DBSale();
			dbSale.deleteSale(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Sale not deleted");
		}
	}

	public ArrayList<PartSale> findAllPartSalesBySaleId(int saleId) {
		IFDBPartSale dbPartSale = new DBPartSale();
		ArrayList<PartSale> allPartSale = new ArrayList<PartSale>();
		allPartSale = dbPartSale.getAllPartSaleBySaleId(saleId, false);
		return allPartSale;
	}

	// ÃŒ∆≈ ƒ¿ —≈ Õ”∆ƒ¿≈ Œ“ œ–ŒÃ≈Õ»
	public void insertPartSale(int saleId, int barcode, String name, double pricePerPiece, int amount, double price)
			throws Exception {
		Sale sale = findById(saleId);
		int departmentId = sale.getDepartment().getId();
		PartSale partSale = new PartSale();
		partSale.setSale(findById(saleId));
		partSale.setBarcode(barcode);
		partSale.setName(name);
		partSale.setPricePerPiece(pricePerPiece);
		partSale.setAmount(amount);
		partSale.setPrice(price);

		try {
			DBConnection.startTransaction();
			DBPartSale dbPartSale = new DBPartSale();
			dbPartSale.insertPartSale(partSale);
			if (funcionalityCtr.isProduct(barcode)) {
				productCtr.recalculateProAmount(barcode, amount, departmentId);
			}
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("PartSale not inserted");
		}
	}

	public void deletePartSale(int saleId) throws Exception {
		try {
			DBConnection.startTransaction();
			DBPartSale dbPartSale = new DBPartSale();
			dbPartSale.deletePartSale(saleId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("PartSale not deleted");
		}
	}
	
	public ArrayList<Object[]> addAllSales() {
		ArrayList<Sale> saleList = findAllSales();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Sale sale : saleList) {
			object = new Object[] { sale.getId(), sale.getDate(), sale.getCustomer().getId(),
					sale.getDepartment().getName(), sale.getTotalPrice() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public ArrayList<Object[]> addAllSalesForDepartment(int departmentId) {
		ArrayList<Sale> saleList = findAllSalesByDepartmentId(departmentId);
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Sale sale : saleList) {
			object = new Object[] { sale.getId(), sale.getDate(), sale.getCustomer().getId(),
					sale.getDepartment().getName(), sale.getTotalPrice() };
			objectArray.add(object);
		}
		return objectArray;
	}
}
