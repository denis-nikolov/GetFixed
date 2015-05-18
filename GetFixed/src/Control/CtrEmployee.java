package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrEmployee {
	CtrDepartment departmentCtr = new CtrDepartment();

	/** Creates a new instance of CtrEmployee */
	public CtrEmployee() {

	}

	public ArrayList<Employee> findAllEmployees() {
		IFDBEmployee dbEmployee = new DBEmployee();
		ArrayList<Employee> allEmployee = new ArrayList<Employee>();
		allEmployee = dbEmployee.findAllEmployees(false);
		return allEmployee;
	}

	public ArrayList<Employee> findAllEmployeesByDepartmentId(int departmentId) {
		IFDBEmployee dbEmployee = new DBEmployee();
		ArrayList<Employee> allEmployee = new ArrayList<Employee>();
		allEmployee = dbEmployee.findAllEmployeesByDepartmentId(departmentId, false);
		return allEmployee;
	}

	public Employee findByName(String name) {
		IFDBEmployee dbEmployee = new DBEmployee();
		return dbEmployee.searchEmployeeByName(name, true);
	}

	public Employee findById(int id) {
		IFDBEmployee dbEmployee = new DBEmployee();
		return dbEmployee.searchEmployeeById(id, true);
	}

	public int updateEmployee(int id, String name, String surname, String address,
			String telephone, String email, String password, String department){
		int departmentId = 0;
		if(department.equals("Aalborg")){
			departmentId = 1;
		} else if(department.equals("Aarhus")){
			departmentId = 2;
		} else if(department.equals("Odense")){
			departmentId = 3;
		} else if(department.equals("Copenhagen")){
			departmentId = 4;
		}
		IFDBEmployee dbEmployee = new DBEmployee();
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setSurname(surname);
		employee.setAddress(address);
		employee.setTelephone(telephone);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setDepartment(departmentCtr.findById(departmentId));
		return dbEmployee.updateEmployee(employee);

	}

	public void insertEmployee(String name, String surname, String address,
			String telephone, String email, String password, int departmentId)
			throws Exception {
		Employee employee = new Employee();
		employee.setName(name);
		employee.setSurname(surname);
		employee.setAddress(address);
		employee.setTelephone(telephone);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setDepartment(departmentCtr.findById(departmentId));
		try {
			DBConnection.startTransaction();
			DBEmployee dbEmployee = new DBEmployee();
			dbEmployee.insertEmployee(employee);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Employee not inserted");
		}
	}

	public void deleteEmployee(int id) throws Exception {

		try {
			DBConnection.startTransaction();
			DBEmployee dbEmployee = new DBEmployee();
			dbEmployee.deleteEmployee(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Employee not deleted");
		}
	}

	public ArrayList<Object[]> addAllEmployeesForDepartment(int id) {
		ArrayList<Employee> employeeList = findAllEmployeesByDepartmentId(id);
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Employee employee : employeeList) {
			object = new Object[] { employee.getId(), employee.getName(),
					employee.getSurname(), employee.getAddress(),
					employee.getTelephone(), employee.getEmail(),
					employee.getPassword(),
					employee.getDepartment().getLocation() };
			objectArray.add(object);
		}
		return objectArray;

	}

	public ArrayList<Object[]> addAllEmployees() {
		ArrayList<Employee> employeeList = findAllEmployees();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Employee employee : employeeList) {
			object = new Object[] { employee.getId(), employee.getName(),
					employee.getSurname(), employee.getAddress(),
					employee.getTelephone(), employee.getEmail(),
					employee.getPassword(),
					employee.getDepartment().getLocation() };
			objectArray.add(object);
		}
		return objectArray;
	}

	public Object[] addEmployeeById(int id) {
		Employee employee = findById(id);
		Object[] object = null;
		object = new Object[] { employee.getId(), employee.getName(),
				employee.getSurname(), employee.getAddress(),
				employee.getTelephone(), employee.getEmail(),
				employee.getPassword(), employee.getDepartment().getLocation() };

		return object;
	}

	public Object[] addEmployeeByName(String name) {
		Employee employee = findByName(name);
		Object[] object = null;
		object = new Object[] { employee.getId(), employee.getName(),
				employee.getSurname(), employee.getAddress(),
				employee.getTelephone(), employee.getEmail(),
				employee.getPassword(), employee.getDepartment().getLocation() };

		return object;
	}
}
