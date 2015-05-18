package DB;

import Control.*;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBProduct implements IFDBProduct {
	private Connection con;
	CtrSupplier supplierCtr = new CtrSupplier();
	CtrDepartment departmentCtr = new CtrDepartment();

	public DBProduct() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Product> findAllProducts(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public ArrayList<Product> findAllProductsByDepartmentId(int departmentId,
			boolean retriveAssociation) {
		return miscWhere("departmentId="+departmentId, retriveAssociation);
	}

	@Override
	public Product searchProductByName(String name, boolean retriveAssociation) {
		String wClause = "name like '%" + name + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Product searchProductByBarcode(int barcode, boolean retriveAssociation) {
		String wClause = "barcode like '%" + barcode + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}
	
	public Product searchProductByBarcodeAndDepartmentId(int barcode, int departmentId, boolean retriveAssociation) {
		String wClause = "barcode like '%" + barcode + "%' and departmentId=" + departmentId;
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}
	@Override
	public int recalculateProductAmount(Product pro){
		Product proObj = pro;
		int rc = -1;
		String query = "UPDATE Product SET "
				+ "amount ='" + proObj.getAmount()
				+ "', "
				+ "orderAmount ='" + proObj.getOrderAmount() 
				+ "' " + " WHERE barcode = '" + proObj.getBarcode() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Recalculate exception in Product db: " + ex);
		}
		return (rc);
	}

	@Override
	public int insertProduct(Product pro) throws Exception {

		int rc = -1;
		String query = "INSERT INTO Product(barcode, name, sellingPrice, "
				+ "leasingPrice, orderingPrice, priceForDiscount, amountForDiscount, amount, "
				+ "minAmount, maxAmount, orderAmount, departmentId, supplierId)  VALUES('"
				+ pro.getBarcode()
				+ "','"
				+ pro.getName()
				+ "','"
				+ pro.getSellingPrice()
				+ "','"
				+ pro.getLeasingPrice()
				+ "','"
				+ pro.getOrderingPrice()
				+ "','"
				+ pro.getPriceForDiscount() 
				+ "','"
				+ pro.getAmountForDiscount()
				+ "','"
				+ pro.getAmount()
				+ "','"
				+ pro.getMinAmount()
				+ "','"
				+ pro.getMaxAmount()
				+ "','"
				+ pro.getOrderAmount()
				+ "','"
				+ pro.getDepartment().getId()
				+ "','"
				+ pro.getSupplier().getId()
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("pro is not inserted correct");
			throw new Exception("pro is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateProduct(Product pro) {
		Product proObj = pro;
		int rc = -1;

		String query = "UPDATE Product SET " 
		        + "name ='" + proObj.getName()
				+ "', "
				+ "sellingPrice ='" + proObj.getSellingPrice() 
				+ "', "
				+ "leasingPrice ='" + proObj.getLeasingPrice() 
				+ "', " 
				+ "orderingPrice ='" + proObj.getOrderingPrice() 
				+ "', " 
				+ "priceForDiscount ='" + proObj.getPriceForDiscount()
				+ "', "
				+ "amountForDiscount ='" + proObj.getAmountForDiscount()
				+ "', "
				+ "amount ='" + proObj.getAmount() 
				+ "', " 
				+ "minAmount ='" + proObj.getMinAmount()
				+ "', " 
				+ "maxAmount ='" + proObj.getMaxAmount()
				+ "', "
				+ "orderAmount ='" + proObj.getOrderAmount()
				+ "', " 
				+ "supplierId ='" + proObj.getSupplier().getId()
				+ "' " + " WHERE barcode = '" + proObj.getBarcode() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Update exception in Product db: " + ex);
		}
		return (rc);
	}

	public int deleteProduct(int barcode, int departmentId) {
		int rc = -1;

		String query = "DELETE FROM Product WHERE barcode = '" + barcode + "' AND departmentId = '" + departmentId + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in Product db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Product> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Product> list = new ArrayList<Product>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Product proObj = new Product();
				proObj = buildProduct(results);
				list.add(proObj);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Product";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Product buildProduct(ResultSet results) {
		Product proObj = new Product();
		try {
			proObj.setBarcode(results.getInt("barcode"));
			proObj.setName(results.getString("name"));
			proObj.setSellingPrice(results.getDouble("sellingPrice"));
			proObj.setLeasingPrice(results.getDouble("leasingPrice"));
			proObj.setOrderingPrice(results.getDouble("orderingPrice"));
			proObj.setPriceForDiscount(results.getDouble("priceForDiscount"));
			proObj.setAmountForDiscount(results.getInt("amountForDiscount"));
			proObj.setAmount(results.getInt("amount"));
			proObj.setMinAmount(results.getInt("minAmount"));
			proObj.setMaxAmount(results.getInt("maxAmount"));
			proObj.setOrderAmount(results.getInt("orderAmount"));
			proObj.setDepartment(departmentCtr.findById(results.getInt("departmentId")));
			proObj.setSupplier(supplierCtr.findById(results.getInt("supplierId")));
		} catch (Exception e) {
			System.out.println("error in building the Product object");
		}
		return proObj;
	}

	private Product singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Product proObj = new Product();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				proObj = buildProduct(results);
				stmt.close();

			} else {
				proObj = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return proObj;
	}

}