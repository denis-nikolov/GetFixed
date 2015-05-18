package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrOrder {

	CtrProduct productCtr = new CtrProduct();

	public CtrOrder() {

	}

	public ArrayList<Order> findAllOrders() {
		IFDBOrder dbOrder = new DBOrder();
		ArrayList<Order> allOrder = new ArrayList<Order>();
		allOrder = dbOrder.getAllOrder(false);
		return allOrder;
	}

	public Order findById(int id) {
		IFDBOrder dbOrder = new DBOrder();
		return dbOrder.searchOrderId(id, true);
	}

	public int insertOrder(int productBarcode, String productName, double pricePerPiece, int amount, double price) {
		Order order = new Order();
		order.setProduct(productCtr.findByBarcode(productBarcode));
		order.setProductName(productCtr.findByBarcode(productBarcode).getName());
		order.setPricePerPiece(productCtr.findByBarcode(productBarcode).getOrderingPrice());
		order.setAmount(amount);
		order.setPrice(price);

		try {
			DBConnection.startTransaction();
			DBOrder dbOrder = new DBOrder();
			int id = dbOrder.insertOrder(order);
			DBConnection.commitTransaction();
			return id;
		} catch (Exception e) {
			//DBConnection.rollbackTransaction();
			//throw new Exception("Order not inserted");
			int returning=1;
			return returning;
		}
	}

	public void deleteOrder(int id) throws Exception {

		try {
			DBConnection.startTransaction();
			DBOrder dbOrder = new DBOrder();
			dbOrder.deleteOrder(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Order not deleted");
		}
	}
}
