package DB;

import Control.CtrProduct;
import Control.CtrLease;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBPartLease implements IFDBPartLease {
	private Connection con;
	CtrProduct productCtr = new CtrProduct();
	CtrLease leaseCtr = new CtrLease();

	public DBPartLease() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<PartLease> getAllPartLeaseByLeaseId(int leaseId, boolean retriveAssociation) {
		return miscWhere("leaseId=" + leaseId, retriveAssociation);
	}

	// ÃŒ∆≈ ƒ¿ —≈ Õ”∆ƒ¿≈ Œ“ œ–ŒÃ≈Õ»
	@Override
	public int insertPartLease(PartLease part) throws Exception {
		int rc = -1;
		String query = "INSERT INTO PartLease(leaseId, barcode, name, pricePerPiece, amount, price)  VALUES('"
				+ part.getLease().getId() + "','" + part.getBarcode() + "','" + part.getName() + "','"
				+ part.getPricePerPiece() + "','" + part.getAmount() + "','" + part.getPrice() + "')";

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

	private ArrayList<PartLease> miscWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<PartLease> list = new ArrayList<PartLease>();

		String query = buildQuery(wClause);

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				PartLease partObj = new PartLease();
				partObj = buildPartLease(results);
				list.add(partObj);
			}
			stmt.close();
			// if (retrieveAssociation) { // The partervisor and department is
			// to
			// be
			// // build as well
			// for (PartLease partObj : list) {
			// PartLease parterEmp = singleWhere(
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
		String query = "SELECT * FROM PartLease";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private PartLease buildPartLease(ResultSet results) {
		PartLease partObj = new PartLease();
		try {
			partObj.setLease(leaseCtr.findById(results.getInt("leaseId")));
			partObj.setBarcode(results.getInt("barcode"));
			partObj.setName(results.getString("name"));
			partObj.setPricePerPiece(results.getDouble("pricePerPiece"));
			partObj.setAmount(results.getInt("amount"));
			partObj.setPrice(results.getDouble("price"));
		} catch (Exception e) {
			System.out.println("error in building the PartLease object");
		}
		return partObj;
	}

	private PartLease singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		PartLease partObj = new PartLease();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				partObj = buildPartLease(results);
				stmt.close();

			} else {
				partObj = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return partObj;
	}

	@Override
	public int deletePartLease(int leaseId) {
		int rc = -1;

		String query = "DELETE FROM PartLease WHERE leaseId = '" + leaseId + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in partLease db: " + ex);
		}
		return (rc);
	}
}