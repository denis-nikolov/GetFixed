package DB;

import Control.*;
import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBOrder implements IFDBOrder {
	private Connection con;
	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();

	public DBOrder() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Order> getAllOrder(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}

	@Override
	public ArrayList<Order> findAllOrdersByDepartmentId(int departmentId, boolean retriveAssociation) {
		return miscWhere("departmentId=" + departmentId, retriveAssociation);
	}

	@Override
	public Order searchOrderId(int id, boolean retriveAssociation) {
		String wClause = "id like '%" + id + "%'";
		System.out.println("SearchC " + wClause);
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertOrder(Order order) throws Exception {
		int nextId = GetMax.getMaxId("Select max(id) from Orders");
		nextId = nextId + 1;
		System.out.println("next id = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Orders(id, date, productBarcode, productName, pricePerPiece, amount, price, received, departmentId)  VALUES('"
				+ nextId
				+ "','"
				+ order.createDate()
				+ "','"
				+ order.getProduct().getBarcode()
				+ "','"
				+ order.getProduct().getName()
				+ "','"
				+ order.getProduct().getOrderingPrice()
				+ "','"
				+ order.getAmount() 
				+ "','" 
				+ order.getPrice()
				+ "','" 
				+ order.isReceived()
				+ "','" 
				+ order.getDepartment().getId()
				+ "')";

		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("order is not inserted correct");
			throw new Exception("order is not inserted correct");
		}
		return nextId;
	}

	@Override
	public int endOrder(Order order) {
		Order orderObj = order;
		int rc = -1;

		String query = "UPDATE Orders SET " + "received ='" + orderObj.isReceived() + "' " + " WHERE id = '"
				+ orderObj.getId() + "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		} catch (Exception ex) {
			System.out.println("Update exception in Order db: " + ex);
		}
		return (rc);
	}

	public int deleteOrder(int id) {
		int rc = -1;

		String query = "DELETE FROM Orders WHERE id = '" + id + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in Order db: " + ex);
		}
		return (rc);
	}

	private ArrayList<Order> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Order> list = new ArrayList<Order>();

		String query = buildQuery(wClause);

		try { 
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Order order = new Order();
				order = buildOrder(results);
				list.add(order);
			}
			stmt.close();
//			 if (retrieveAssociation) { // The orderervisor and department is
//			 //to
//			// be
//			 // build as well
//			 for (Order order : list) {
//			 Order ordererEmp = singleWhere(
//			 " ssn = '" + ordererssn + "'", false);
//			 System.out.println("Supervisor is seleceted");
//			 // here the department has to be selected as well
//			 }
//			 }// end if

		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT * FROM Orders";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	private Order buildOrder(ResultSet results) {
		Order order = new Order();
		try {
			order.setId(results.getInt("id"));
			order.setDate(results.getString("date"));
			order.setProduct(productCtr.findByBarcode(results.getInt("productBarcode")));
			order.setProductName(productCtr.findByBarcode(results.getInt("productBarcode")).getName());
			order.setPricePerPiece(productCtr.findByBarcode(results.getInt("productBarcode")).getOrderingPrice());
			order.setAmount(results.getInt("amount"));
			order.setPrice(results.getDouble("price"));
			order.setReceived(results.getBoolean("received"));
			order.setDepartment(departmentCtr.findById(results.getInt("departmentId")));
		} catch (Exception e) {
			System.out.println("error in building the Orders object");
		}
		return order;
	}

	private Order singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Order order = new Order();

		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				order = buildOrder(results);
				stmt.close();

			} else {
				order = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return order;
	}

}