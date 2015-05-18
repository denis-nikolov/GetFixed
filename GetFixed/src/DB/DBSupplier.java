package DB;

import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBSupplier implements IFDBSupplier {
	private Connection con;

	public DBSupplier() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Supplier> getAllSupplier(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public Supplier searchSupplierByName(String name, boolean retriveAssociation) {
		String wClause = "name like '%" + name + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Supplier searchSupplierById(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertSupplier(Supplier sup) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Supplier");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Supplier(id, name, surname, address, telephone, email)  VALUES('"
				+ nextId
				+ "','"
				+ sup.getName()
				+ "','"
				+ sup.getSurname()
				+ "','"
				+ sup.getAddress()
				+ "','"
				+ sup.getTelephone()
				+ "','"
				+ sup.getEmail() 
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("sup is not inserted correct");
			throw new Exception("sup is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateSupplier(Supplier sup) {
		Supplier supObj = sup;
		int rc = -1;

		String query = "UPDATE Supplier SET " + "name ='" + supObj.getName()
				+ "', "
				+ "surname ='" + supObj.getSurname()
				+ "', "
				+ "address ='" + supObj.getAddress() 
				+ "', " 
				+ "telephone ='" + supObj.getTelephone() 
				+ "', " 
				+ "email ='" + supObj.getEmail()
				+ "' " + " WHERE id = '" + supObj.getId() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Update exception in Supplier db: " + ex);
		}
		return (rc);
	}

	public int deleteSupplier(int id) {
		int rc = -1;

		String query = "DELETE FROM Supplier WHERE id = '" + id + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in Supplier db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Supplier> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Supplier> list = new ArrayList<Supplier>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Supplier supObj = new Supplier();
				supObj = buildSupplier(results);
				list.add(supObj);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Supplier";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Supplier buildSupplier(ResultSet results) {
		Supplier supObj = new Supplier();
		try {
			supObj.setId(results.getInt("id"));
			supObj.setName(results.getString("name"));
			supObj.setSurname(results.getString("surname"));
			supObj.setAddress(results.getString("address"));
			supObj.setTelephone(results.getString("telephone"));
			supObj.setEmail(results.getString("email"));
		} catch (Exception e) {
			System.out.println("error in building the Supplier object");
		}
		return supObj;
	}

	private Supplier singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Supplier supObj = new Supplier();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				supObj = buildSupplier(results);
				
				stmt.close();

			} else { 
				supObj = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return supObj;
	}

}
