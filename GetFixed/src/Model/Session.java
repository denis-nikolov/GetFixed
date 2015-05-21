package Model;

public class Session {
	private int id;
	private boolean loggedIn;
	private Employee employee;
	private int accessLevel;
	private boolean active;

	public Session() {
		
	}
	
	public Session(int id, Employee employee){
		this.employee = employee;
		this.id = id;
		this.active = true;
        this.loggedIn = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
