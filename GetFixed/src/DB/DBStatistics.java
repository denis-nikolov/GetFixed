package DB;

import java.sql.*;
import java.util.*;

public class DBStatistics {
	private Connection con;

	public DBStatistics() {
		con = DBConnection.getInstance().getDBcon();
	}

	public Map<Integer, Integer> getTopProducts() {
		ResultSet rs;

		Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery("SELECT TOP 5  barcode, sum(amount) as number   FROM [dbo].[PartSale]   where barcode<5000  group by barcode order by number DESC; ");

			while (rs.next()) {
				myMap.put(rs.getInt("number"), rs.getInt("barcode"));
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return myMap;
	}

	public Map<Integer, Integer> getTopServices() {
		ResultSet rs;

		Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery("SELECT TOP 5  barcode, sum(amount) as number   FROM [dbo].[PartSale]   where barcode>5000  group by barcode order by number DESC;");

			while (rs.next()) {
				myMap.put(rs.getInt("number"), rs.getInt("barcode"));
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return myMap;
	}

        public Map<Integer, Integer> getDepartmentSales() {
		ResultSet rs;

		Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery("select departmentId as id, sum(totalPrice) as number from sale group by departmentId;");

			while (rs.next()) {
				myMap.put(rs.getInt("number"), rs.getInt("id"));
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return myMap;
	}

}
