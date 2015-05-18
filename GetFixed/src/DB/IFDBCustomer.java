package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBCustomer {

	public ArrayList<Customer> getAllCustomer(boolean retriveAssociation);

	public Customer searchCustomerByName(String name, boolean retriveAssociation);

	public Customer searchCustomerById(int id, boolean retriveAssociation);

	public int insertCustomer(Customer cust) throws Exception;

	public int updateCustomer(Customer cust);

	public int deleteCustomer(int id);
}