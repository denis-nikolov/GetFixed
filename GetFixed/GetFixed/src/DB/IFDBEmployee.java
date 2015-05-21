package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBEmployee {
	public ArrayList<Employee> findAllEmployees(boolean retriveAssociation);
	
	public ArrayList<Employee> findAllEmployeesByDepartmentId(int departmentId, boolean retriveAssociation);

	public Employee searchEmployeeByName(String name, boolean retriveAssociation);

	public Employee searchEmployeeById(int id, boolean retriveAssociation);

	public int insertEmployee(Employee emp) throws Exception;

	public int updateEmployee(Employee emp);

	public int deleteEmployee(int id);

}