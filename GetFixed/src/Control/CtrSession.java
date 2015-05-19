package Control;

import Model.*;

public class CtrSession {
	private CtrEmployee ctrEmployee;
	private Employee employee;

	public CtrSession() {
		ctrEmployee = new CtrEmployee();
	}

	public int login(String name, String password) {
		setEmployee(ctrEmployee.findByName(name));
		if ((getEmployee() != null) && (getEmployee().getPassword().equals(password))) {
			return getEmployee().getDepartment().getId();
		} else {
			return -1;
		}
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
