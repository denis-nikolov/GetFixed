package DB;

import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBCustomer implements IFDBCustomer {
	private Connection con;

	public DBCustomer() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Customer> getAllCustomer(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public Customer searchCustomerByName(String name, boolean retriveAssociation) {
		String wClause = "name like '%" + name + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public Customer searchCustomerById(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertCustomer(Customer cust) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Customer");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Customer(id, name, surname, telephone, email, discount)  VALUES('"
				+ nextId
				+ "','"
				+ cust.getName()
				+ "','"
				+ cust.getSurname()
				+ "','"
				+ cust.getTelephone()
				+ "','"
				+ cust.getEmail() 
				+ "','"
				+ cust.getDiscount()
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("cust is not inserted correct");
			throw new Exception("cust is not inserted correct");
		}
		return (rc);
	}

	@Override
	public int updateCustomer(Customer cust) {
		Customer custObj = cust;
		int rc = -1;

		String query = "UPDATE Customer SET " + "name ='" + custObj.getName()
				+ "', "
				+ "surname ='" + custObj.getSurname()
				+ "', "
				+ "telephone ='" + custObj.getTelephone() 
				+ "', " 
				+ "email ='" + custObj.getEmail()
				+ "', " 
				+ "discount ='" + custObj.getDiscount() 
				+ "' " + " WHERE id = '" + custObj.getId() 
				+ "'";
		System.out.println("Update query:" + query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Update exception in Customer db: " + ex);
		}
		return (rc);
	}

	public int deleteCustomer(int id) {
		int rc = -1;

		String query = "DELETE FROM Customer WHERE id = '" + id + "'";
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}
		catch (Exception ex) {
			System.out.println("Delete exception in Customer db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Customer> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Customer> list = new ArrayList<Customer>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Customer custObj = new Customer();
				custObj = buildCustomer(results);
				list.add(custObj);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Customer";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Customer buildCustomer(ResultSet results) {
		Customer custObj = new Customer();
		try {
			custObj.setId(results.getInt("id"));
			custObj.setName(results.getString("name"));
			custObj.setSurname(results.getString("surname"));
			custObj.setTelephone(results.getString("telephone"));
			custObj.setEmail(results.getString("email"));
			custObj.setDiscount(results.getInt("discount"));
		} catch (Exception e) {
			System.out.println("error in building the Customer object");
		}
		return custObj;
	}

	private Customer singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Customer custObj = new Customer();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				custObj = buildCustomer(results);
				
				stmt.close();

			} else { 
				custObj = null;
			}
		}
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return custObj;
	}

}