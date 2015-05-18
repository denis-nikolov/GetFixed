package DB;

import Control.*;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBService implements IFDBService {
	private Connection con;
	CtrSupplier supplierCtr = new CtrSupplier();
	CtrDepartment departmentCtr = new CtrDepartment();

	public DBService() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Service> findAllServices(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}
	

	@Override
	public Service searchServiceByName(String name, boolean retriveAssociation) {
		String wClause = "name like '%" + name + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Service searchServiceByBarcode(int barcode, boolean retriveAssociation) {
		String wClause = "barcode like '%" + barcode + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertService(Service ser) throws Exception {
		int nextId = GetMax.getMaxId("Select max(barcode) from Service");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Service(barcode, name, price)  VALUES('"
				+ nextId
				+ "','"
				+ ser.getName()
				+ "','"
				+ ser.getPrice()
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("ser is not inserted correct");
			throw new Exception("ser is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateService(Service ser) {
		Service serObj = ser;
		int rc = -1;

		String query = "UPDATE Service SET " 
		        + "name ='" + serObj.getName()
				+ "', "
				+ "price ='" + serObj.getPrice() 
				+ "' " + " WHERE barcode = '" + serObj.getBarcode() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Update exception in Service db: " + ex);
		}
		return (rc);
	}

	public int deleteService(int barcode) {
		int rc = -1;

		String query = "DELETE FROM Service WHERE barcode = '" + barcode + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in Service db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Service> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Service> list = new ArrayList<Service>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Service serObj = new Service();
				serObj = buildService(results);
				list.add(serObj);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Service";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Service buildService(ResultSet results) {
		Service serObj = new Service();
		try {
			serObj.setBarcode(results.getInt("barcode"));
			serObj.setName(results.getString("name"));
			serObj.setPrice(results.getDouble("price"));
		} catch (Exception e) {
			System.out.println("error in building the Service object");
		}
		return serObj;
	}

	private Service singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Service serObj = new Service();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				serObj = buildService(results);
				stmt.close();

			} else {
				serObj = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return serObj;
	}

}