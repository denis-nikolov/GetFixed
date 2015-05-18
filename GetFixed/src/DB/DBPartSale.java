package DB;

import Control.CtrProduct;
import Control.CtrSale;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBPartSale implements IFDBPartSale {
	private Connection con;
	CtrProduct productCtr = new CtrProduct();
	CtrSale saleCtr = new CtrSale();

	public DBPartSale() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<PartSale> getAllPartSaleBySaleId(int saleId, boolean retriveAssociation) {
		return misaleCtrWhere("saleId="+saleId, retriveAssociation);
	}
	//ÃŒ∆≈ ƒ¿ —≈ Õ”∆ƒ¿≈ Œ“ œ–ŒÃ≈Õ»
	@Override
	public int insertPartSale(PartSale part) throws Exception {
		int rc = -1;
		String query = "INSERT INTO PartSale(saleId, barcode, name, pricePerPiece, amount, price)  VALUES('"
				+ part.getSale().getId()
				+ "','"
				+ part.getBarcode()
				+ "','"
				+ part.getName()
				+ "','"
				+ part.getPricePerPiece()
				+ "','"
				+ part.getAmount()
				+ "','"
				+ part.getPrice()
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (SQLException ex) {
			System.out.println("part is not inserted correct");
			throw new Exception("part is not inserted correct");
		}
		return (rc);
	}

	private ArrayList<PartSale> misaleCtrWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<PartSale> list = new ArrayList<PartSale>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				PartSale partObj = new PartSale();
				partObj = buildPartSale(results);
				list.add(partObj);
			}
			stmt.close();
			// if (retrieveAssociation) { // The partervisor and department is to
			// be
			// // build as well
			// for (PartSale partObj : list) {
			// PartSale parterEmp = singleWhere(
			// " ssn = '" + parterssn + "'", false);
			// System.out.println("Supervisor is seleceted");
			// // here the department has to be selected as well
			// }
			// }// end if

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM PartSale";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private PartSale buildPartSale(ResultSet results) {
		PartSale partObj = new PartSale();
		try {
			partObj.setSale(saleCtr.findById(results.getInt("saleId")));
			partObj.setBarcode(results.getInt("barcode"));
			partObj.setName(results.getString("name"));
			partObj.setPricePerPiece(results.getDouble("pricePerPiece"));
			partObj.setAmount(results.getInt("amount"));
			partObj.setPrice(results.getDouble("price"));
		} catch (Exception e) {
			System.out.println("error in building the PartSale object");
		}
		return partObj;
	}

	private PartSale singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		PartSale partObj = new PartSale();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				partObj = buildPartSale(results);
				stmt.close();

			} else { 
				partObj = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return partObj;
	}

	@Override
	public int deletePartSale(int saleId) {
		int rc = -1;

		String query = "DELETE FROM PartSale WHERE saleId = '" + saleId + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in partSale db: " + ex);
		}
		return (rc);
	}

}