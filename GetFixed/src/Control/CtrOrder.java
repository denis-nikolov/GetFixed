package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrOrder {

	CtrProduct productCtr = new CtrProduct();
	CtrDepartment departmentCtr = new CtrDepartment();
	CtrFunctionality functionalityCtr = new CtrFunctionality();

	public CtrOrder() {

	}

	public ArrayList<Order> findAllOrders() {
		IFDBOrder dbOrder = new DBOrder();
		ArrayList<Order> allOrder = new ArrayList<Order>();
		allOrder = dbOrder.getAllOrder(false);
		return allOrder;
	}

	public ArrayList<Order> findAllOrdersByDepartmentId(int departmentId) {
		IFDBOrder dbOrder = new DBOrder();
		ArrayList<Order> allOrder = new ArrayList<Order>();
		allOrder = dbOrder.findAllOrdersByDepartmentId(departmentId, false);
		return allOrder;
	}

	public Order findById(int id) {
		IFDBOrder dbOrder = new DBOrder();
		return dbOrder.searchOrderId(id, true);
	}

	public int insertOrder(int productBarcode, int amount, double price, int departmentId) throws Exception {
		Order order = new Order();
		order.setProduct(productCtr.findByBarcode(productBarcode));
		order.setProductName(productCtr.findByBarcode(productBarcode).getName());
		order.setPricePerPiece(productCtr.findByBarcode(productBarcode).getOrderingPrice());
		order.setAmount(amount);
		order.setPrice(price);
		order.setDepartment(departmentCtr.findById(departmentId));

		try {
			DBConnection.startTransaction();
			DBOrder dbOrder = new DBOrder();
			productCtr.orderProductCalculation(productBarcode, amount, departmentId);
			int id = dbOrder.insertOrder(order);
			DBConnection.commitTransaction();
			return id;
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Order not inserted");
		}
	}

	public int endOrder(int id) {
		IFDBOrder dbOrder = new DBOrder();
		Order order = findById(id);

		productCtr.receiveProductCalculation(order.getProduct().getBarcode(), order.getAmount(), order.getDepartment().getId());

		order.setReceived(true);
		return dbOrder.endOrder(order);
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

	public ArrayList<Object[]> addAllOrders() {
		ArrayList<Order> orderList = findAllOrders();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Order order : orderList) {
			object = new Object[] { order.getId(), order.getDate(), order.getProduct().getBarcode(),
					order.getProduct().getName(), order.getProduct().getOrderingPrice(), order.getAmount(),
					order.getPrice(), order.isReceived(), order.getDepartment().getName() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addOrderById(int id) {
		Order order = findById(id);
		Object[] object = null;
		object = new Object[] { order.getId(), order.getDate(), order.getProduct().getBarcode(),
				order.getProduct().getName(), order.getProduct().getOrderingPrice(), order.getAmount(),
				order.getPrice(), order.isReceived(), order.getDepartment().getName() };
		return object;
	}

	public ArrayList<Object[]> addAllUnrecievedOrders() {
		ArrayList<Order> orderList = findAllOrders();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Order order : orderList) {
			if (!order.isReceived()) {
				object = new Object[] { order.getId(), order.getDate(), order.getProduct().getBarcode(),
						order.getProduct().getName(), order.getProduct().getOrderingPrice(), order.getAmount(),
						order.getPrice(), order.isReceived(), order.getDepartment().getName() };
				objectArray.add(object);
			}
		}
		return objectArray;
	}

	public ArrayList<Object[]> addAllOrdersForDepartment(int departmentId) {
		ArrayList<Order> orderList = findAllOrdersByDepartmentId(departmentId);
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Order order : orderList) {
			object = new Object[] { order.getId(), order.getDate(), order.getProduct().getBarcode(),
					order.getProduct().getName(), order.getProduct().getOrderingPrice(), order.getAmount(),
					order.getPrice(), order.isReceived(), order.getDepartment().getName() };
			objectArray.add(object);
		}
		return objectArray;
	}
}
