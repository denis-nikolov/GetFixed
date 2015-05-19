package DB;

import Control.CtrCustomer;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBLease implements IFDBLease {
	private Connection con;
	CtrCustomer cc = new CtrCustomer();

	public DBLease() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Lease> getAllLease(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public Lease searchLeaseId(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertLease(Lease lease) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Lease");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Lease(id, date, customerId, period, price, returned)  VALUES('" + nextId + "','"
				+ lease.createDate() + "','" + lease.getCustomer().getId() + "','" + lease.getPeriod() + "','"
				+ lease.getPrice() + "','" + lease.isReturned() + "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("lease is not inserted correct");
			throw new Exception("lease is not inserted correct");
		}
		return nextId;
	}
	
	@Override
	public int endLease(Lease lease){
		Lease leaseObj = lease;
		int rc = -1;

		String query = "UPDATE Lease SET " 
		        + "returned ='" + leaseObj.isReturned()
				+ "' " + " WHERE id = '" + leaseObj.getId() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Update exception in Lease db: " + ex);
		}
		return (rc);
	}

	public int deleteLease(int id) {
		int rc = -1;

		String query = "DELETE FROM Lease WHERE id = '" + id + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in Lease db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Lease> miscWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Lease> list = new ArrayList<Lease>();

		String query = buildQuery(wClause);

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Lease leaseObj = new Lease();
				leaseObj = buildLease(results);
				list.add(leaseObj);
			}
			stmt.close();
			// if (retrieveAssociation) { // The leaseervisor and department is
			// to
			// be
			// // build as well
			// for (Lease leaseObj : list) {
			// Lease leaseerEmp = singleWhere(
			// " ssn = '" + leaseerssn + "'", false);
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
		String query = "SELECT * FROM Lease";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Lease buildLease(ResultSet results) {
		Lease leaseObj = new Lease();
		try {
			leaseObj.setId(results.getInt("id"));
			leaseObj.setDate(results.getString("date"));
			leaseObj.setCustomer(cc.findById(results.getInt("customerId")));
			leaseObj.setPeriod(results.getInt("period"));
			leaseObj.setPrice(results.getInt("price"));
			leaseObj.setReturned(results.getBoolean("returned"));
		} catch (Exception e) {
			System.out.println("error in building the Lease object");
		}
		return leaseObj;
	}

	private Lease singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Lease leaseObj = new Lease();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				leaseObj = buildLease(results);
				stmt.close();

			} else {
				leaseObj = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return leaseObj;
	}
}