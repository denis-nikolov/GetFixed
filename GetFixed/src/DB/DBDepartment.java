package DB;

import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBDepartment implements IFDBDepartment {
	private Connection con;

	public DBDepartment() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Department> getAllDepartment(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public Department searchDepartmentByLocation(String location,
			boolean retriveAssociation) {
		String wClause = "location like '%" + location + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Department searchDepartmentById(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertDepartment(Department dep) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Department");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Department(id, name, address, location, telephone, email)  VALUES('"
				+ nextId
				+ "','"
				+ dep.getName()
				+ "','"
				+ dep.getAddress()
				+ "','"
				+ dep.getLocation()
				+ "','"
				+ dep.getTelephone()
				+ "','" + dep.getEmail() + "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("dep is not inserted correct");
			throw new Exception("dep is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateDepartment(Department dep) {
		Department depObj = dep;
		int rc = -1;

		String query = "UPDATE Department SET " + "name ='" + depObj.getName()
				+ "', " 
				+ "address ='" + depObj.getAddress()
				+ "', " 
				+ "location ='" + depObj.getLocation()
				+ "', " 
				+ "telephone ='" + depObj.getTelephone() 
				+ "', " 
				+ "email ='" + depObj.getEmail() 
				+ "' " + " WHERE id = '" + depObj.getId()
				+ "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		} catch (Exception ex) {
			System.out.println("Update exception in Department db: " + ex);
		}
		return (rc);
	}

	public int deleteDepartment(int id) {
		int rc = -1;

		String query = "DELETE FROM Department WHERE id = '" + id + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in Department db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Department> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Department> list = new ArrayList<Department>();

		String query = buildQuery(wClause);

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Department depObj = new Department();
				depObj = buildDepartment(results);
				list.add(depObj);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Department";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Department buildDepartment(ResultSet results) {
		Department depObj = new Department();
		try {
			depObj.setId(results.getInt("id"));
			depObj.setName(results.getString("name"));
			depObj.setAddress(results.getString("address"));
			depObj.setLocation(results.getString("location"));
			depObj.setTelephone(results.getString("telephone"));
			depObj.setEmail(results.getString("email"));
		} catch (Exception e) {
			System.out.println("error in building the Department object");
		}
		return depObj;
	}

	private Department singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Department depObj = new Department();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				depObj = buildDepartment(results);

				stmt.close();

			} else {
				depObj = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return depObj;
	}

}
