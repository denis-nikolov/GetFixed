package DB;

import Control.*;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBEmployee implements IFDBEmployee {
	private Connection con;
	CtrDepartment departmentCtr = new CtrDepartment();

	public DBEmployee() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Employee> findAllEmployees(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public ArrayList<Employee> findAllEmployeesByDepartmentId(int departmentId,
			boolean retriveAssociation) {
		return miscWhere("departmentId=" + departmentId, retriveAssociation);
	}

	@Override
	public Employee searchEmployeeByName(String name, boolean retriveAssociation) {
		String wClause = "name like '%" + name + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Employee searchEmployeeById(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchP " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertEmployee(Employee emp) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Employee");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Employee(id, name, surname, "
				+ "address, telephone, email, password, departmentId)  VALUES('"
				+ nextId + "','" + emp.getName() + "','" + emp.getSurname()
				+ "','" + emp.getAddress() + "','" + emp.getTelephone() + "','"
				+ emp.getEmail() + "','" + emp.getPassword() + "','"
				+ emp.getDepartment().getId() + "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("emp is not inserted correct");
			throw new Exception("emp is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateEmployee(Employee emp) {
		Employee empObj = emp;
		int rc = -1;

		String query = "UPDATE Employee SET " 
		        + "name ='" + empObj.getName()
				+ "', " 
		        + "surname ='" + empObj.getSurname() 
		        + "', "
				+ "address ='" + empObj.getAddress() 
				+ "', "
				+ "telephone ='" + empObj.getTelephone() 
				+ "', "
				+ "email ='" + empObj.getEmail()
				+ "', "
				+ "password ='" + empObj.getPassword()
				+ "', "
				+ "departmentId ='" + empObj.getDepartment().getId() 
				+ "' "
				+ " WHERE id = '" + empObj.getId() + "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		} catch (Exception ex) {
			System.out.println("Update exception in Employee db: " + ex);
		}
		return (rc);
	}

	public int deleteEmployee(int id) {
		int rc = -1;

		String query = "DELETE FROM Employee WHERE id = '" + id + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in Employee db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Employee> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Employee> list = new ArrayList<Employee>();

		String query = buildQuery(wClause);

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Employee empObj = new Employee();
				empObj = buildEmployee(results);
				list.add(empObj);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Employee";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Employee buildEmployee(ResultSet results) {
		Employee empObj = new Employee();
		try {
			empObj.setId(results.getInt("id"));
			empObj.setName(results.getString("name"));
			empObj.setSurname(results.getString("surname"));
			empObj.setAddress(results.getString("address"));
			empObj.setTelephone(results.getString("telephone"));
			empObj.setEmail(results.getString("email"));
			empObj.setPassword(results.getString("password"));
			empObj.setDepartment(departmentCtr.findById(results
					.getInt("departmentId")));
		} catch (Exception e) {
			System.out.println("error in building the Employee object");
		}
		return empObj;
	}

	private Employee singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Employee empObj = new Employee();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				empObj = buildEmployee(results);
				stmt.close();

			} else {
				empObj = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return empObj;
	}

}