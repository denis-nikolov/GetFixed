package Control;

import Model.*;
import DB.*;

import java.util.ArrayList;

public class CtrDepartment {
	public CtrDepartment() {

	}

	public ArrayList<Department> findAllDepartments() {
		IFDBDepartment dbDepartment = new DBDepartment();
		ArrayList<Department> allDepartment = new ArrayList<Department>();
		allDepartment = dbDepartment.getAllDepartment(false);
		return allDepartment;
	}

	public Department findByName(String name) {
		IFDBDepartment dbDepartment = new DBDepartment();
		return dbDepartment.searchDepartmentByLocation(name, true);
	}

	public Department findById(int id) {
		IFDBDepartment dbDepartment = new DBDepartment();
		return dbDepartment.searchDepartmentById(id, true);
	}

	public int updateDepartment(int id, String name, String address, String location,
			String telephone, String email) {
		IFDBDepartment dbDepartment = new DBDepartment();
		Department department = new Department();

		department.setId(id);
		department.setName(name);
		department.setAddress(address);
		department.setLocation(location);
		department.setTelephone(telephone);
		department.setEmail(email);
		return dbDepartment.updateDepartment(department);

	}

	public void insertDepartment(String name, String address, String location,
			String telephone, String email) throws Exception {
		Department department = new Department();
		department.setName(name);
		department.setAddress(address);
		department.setLocation(location);
		department.setTelephone(telephone);
		department.setEmail(email);

		try {
			DBConnection.startTransaction();
			DBDepartment dbDepartment = new DBDepartment();
			dbDepartment.insertDepartment(department);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Department not inserted");
		}
	}

	public void deleteDepartment(int id) throws Exception {

		try {
			DBConnection.startTransaction();
			DBDepartment dbDepartment = new DBDepartment();
			dbDepartment.deleteDepartment(id);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
			throw new Exception("Department not deleted");
		}
	}

        public Object[] addDepartmentById(int id) {
		Department department = findById(id);
		Object[] object = null;
		object = new Object[] { department.getId(), department.getName(),
				 department.getAddress(), department.getLocation(),
				department.getTelephone(), department.getEmail()};

		return object;
	}

	public Object[] addDepartmentByName(String name) {
		Department department = findByName(name);
		Object[] object = null;
		object = new Object[] { department.getId(), department.getName(),
				 department.getAddress(),department.getLocation(),
				department.getTelephone(), department.getEmail()
				 };

		return object;
	}

	
	public ArrayList<Object[]> addAllDepartments() {
		ArrayList<Department> departmentList = findAllDepartments();
		Object[] object = null;
		ArrayList<Object[]> objectArray = new ArrayList<Object[]>();
		for (Department department : departmentList) {
			object = new Object[] { department.getId(), department.getName(),
					 department.getAddress(),department.getLocation(),
					department.getTelephone(), department.getEmail()};
			objectArray.add(object);
		}
		return objectArray;
	}

}
