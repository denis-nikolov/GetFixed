package DB;

import Control.*;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBSale implements IFDBSale {
	private Connection con;
	CtrCustomer cc = new CtrCustomer();
	CtrDepartment departmentCtr = new CtrDepartment();

	public DBSale() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Sale> getAllSale(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}
	
	@Override
	public ArrayList<Sale> findAllSalesByDepartmentId(int departmentId,
			boolean retriveAssociation) {
		return miscWhere("departmentId="+departmentId, retriveAssociation);
	}

	@Override
	public Sale searchSaleId(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertSale(Sale sale) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Sale");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Sale(id, date, customerId, totalPrice, departmentId)  VALUES('"
				+ nextId
				+ "','"
				+ sale.createDate()
				+ "','"
				+ sale.getCustomer().getId()
				+ "','"
				+ sale.getTotalPrice()
				+ "','"
				+ sale.getDepartment().getId()+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("sale is not inserted correct");
			throw new Exception("sale is not inserted correct");
		}
		return nextId;
	}

	public int deleteSale(int id) {
		int rc = -1;

		String query = "DELETE FROM Sale WHERE id = '" + id + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in Sale db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Sale> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Sale> list = new ArrayList<Sale>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Sale sale = new Sale();
				sale = buildSale(results);
				list.add(sale);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Sale";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Sale buildSale(ResultSet results) {
		Sale sale = new Sale();
		try {
			sale.setId(results.getInt("id"));
			sale.setDate(results.getString("date"));
			sale.setCustomer(cc.findById(results.getInt("customerId")));
			sale.setTotalPrice(results.getInt("totalPrice"));
			sale.setDepartment(departmentCtr.findById(results.getInt("departmentId")));
			
		} catch (Exception e) {
			System.out.println("error in building the Sale object");
		}
		return sale;
	}

	private Sale singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Sale sale = new Sale();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				sale = buildSale(results);
				stmt.close();

			} else { 
				sale = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return sale;
	}

}