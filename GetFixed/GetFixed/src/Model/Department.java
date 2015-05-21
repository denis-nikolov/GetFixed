package Model;

public class Department {
	private int id;
	private String name;
	private String address;
	private String location;
	private String telephone;
	private String email;
	
	public Department(){
		
	}

	public Department(int id, String name, String address, String location, String telephone,
			String email) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.location = location;
		this.telephone = telephone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
