package DB;
import Model.*;

import java.util.ArrayList;

public interface IFDBOrder {

	public ArrayList<Order> getAllOrder(boolean retriveAssociation);
	
	public ArrayList<Order> findAllOrdersByDepartmentId(int departmentId, boolean retriveAssociation);

    public Order searchOrderId(int id, boolean retriveAssociation);

    public int insertOrder(Order order) throws Exception;
    
    public int endOrder(Order order);

    public int deleteOrder(int id);
}