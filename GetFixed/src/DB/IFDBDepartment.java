package DB;

import Model.*;

import java.util.ArrayList;

public interface IFDBDepartment {

	public ArrayList<Department> getAllDepartment(boolean retriveAssociation);

	public Department searchDepartmentByLocation(String location, boolean retriveAssociation);

	public Department searchDepartmentById(int id, boolean retriveAssociation);

	public int insertDepartment(Department dep) throws Exception;

	public int updateDepartment(Department dep);

	public int deleteDepartment(int id);
}
