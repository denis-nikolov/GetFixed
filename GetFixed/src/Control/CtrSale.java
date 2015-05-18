package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrSale {

	CtrCustomer customerCtr = new CtrCustomer();
	CtrProduct productCtr = new CtrProduct();
	CtrService serviceCtr = new CtrService();
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

	public Sale findById(int id) {
		IFDBSale dbSale = new DBSale();
		return dbSale.searchSaleId(id, true);
	}

	public int insertSale(int customerId, double totalPrice) throws Exception {
		Sale sale = new Sale();
		sale.setCustomer(customerCtr.findById(customerId));
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
	
	public double calculatePrice(double pricePerPiece, int amount){
		double price = 0;
		price = pricePerPiece * amount;
		return price;
	}
	
	public double calculateDiscount(int customerId, double subTotal){
		double discount = 0;
		int percent = customerCtr.findById(customerId).getDiscount();
		discount = (subTotal * percent) / 100;
		return discount;
	}
	
	public double calculateTotalPrice(double subTotal, double discount){
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
	//ÃŒ∆≈ ƒ¿ —≈ Õ”∆ƒ¿≈ Œ“ œ–ŒÃ≈Õ»
	public void insertPartSale(int saleId, int barcode, String name, 
			double pricePerPiece, int amount, double price) throws Exception {
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
			if(funcionalityCtr.isProduct(barcode)){
			    productCtr.recalculateProAmount(barcode, amount);
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
}
