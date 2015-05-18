package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrCustomer {
	public CtrCustomer() {

	}

	public ArrayList<Customer> findAllCustomers() {
		IFDBCustomer dbCustomer = new DBCustomer();
		ArrayList<Customer> allCustomer = new ArrayList<Customer>();
		allCustomer = dbCustomer.getAllCustomer(false);
		return allCustomer;
	}

	public Customer findByName(String name) {
		IFDBCustomer dbCustomer = new DBCustomer();
		return dbCustomer.searchCustomerByName(name, true);
	}

	public Customer findById(int id) {
		IFDBCustomer dbCustomer = new DBCustomer();
		return dbCustomer.searchCustomerById(id, true);
	}

	public int updateCustomer(int id, String name, String surname,
			String telephone, String email, int discount) {
		IFDBCustomer dbCustomer = new DBCustomer();
		Customer customer = new Customer();

		customer.setId(id);
		customer.setName(name);
		customer.setSurname(surname);
		customer.setTelephone(telephone);
		customer.setEmail(email);
		customer.setDiscount(discount);
		return dbCustomer.updateCustomer(customer);

	}

	public void insertCustomer(String name, String surname, 
			String telephone, String email, int discount) throws Exception {
		Customer customer = new Customer();
		customer.setName(name);
		customer.setSurname(surname);
		customer.setTelephone(telephone);
		customer.setEmail(email);
		customer.setDiscount(discount);

		try {
			DBConnection.startTransaction();
			DBCustomer dbCustomer = new DBCustomer();
			dbCustomer.insertCustomer(customer);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Customer not inserted");
		}
	}

	public void deleteCustomer(int id) throws Exception {

		try {
			DBConnection.startTransaction();
			DBCustomer dbCustomer = new DBCustomer();
			dbCustomer.deleteCustomer(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Customer not deleted");
		}
	}
	
	public ArrayList<Object[]> addAllCustomers() {
		ArrayList<Customer> customerList = findAllCustomers();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Customer customer : customerList) {
			object = new Object[] { 
					customer.getId(), 
					customer.getName(),
					customer.getSurname(),
					customer.getTelephone(), 
					customer.getEmail(),
					customer.getDiscount() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addCustomerById(int id) {
		Customer customer = findById(id);
		Object[] object = null;
		object = new Object[] { 
				customer.getId(), 
				customer.getName(),
				customer.getSurname(),
				customer.getTelephone(), 
				customer.getEmail(),
				customer.getDiscount()  };

		return object;
	}

	public Object[] addCustomerByName(String name) {
		Customer customer = findByName(name);
		Object[] object = null;
		object = new Object[] {  
				customer.getId(), 
				customer.getName(),
				customer.getSurname(),
				customer.getTelephone(), 
				customer.getEmail(),
				customer.getDiscount()  };

		return object;
	}

}
