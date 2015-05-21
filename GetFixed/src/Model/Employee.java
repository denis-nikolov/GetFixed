package Model;

public class Employee{
	private int id;
	private String name;
	private String surname;
	private String address;
	private String telephone;
	private String email;
	private String password;
	private Department department;
	
	public Employee(){
		
	}

	public Employee(int id, String name, String surname, String address,
			String telephone, String email, String password,
			Department department){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
		this.department = department;
	}
	
	public int getId(){
	    return id;
	}

	public void setId(int id){
	    this.id = id;
	}

	public String getName(){
	    return name;
	}

	public void setName(String name){
	    this.name = name;
	}

	public String getSurname(){
	    return surname;
	}

	public void setSurname(String surname){
	    this.surname = surname;
	}

	public String getAddress(){
	    return address;
	}

	public void setAddress(String address){
	    this.address = address;
	}

	public String getTelephone(){
	    return telephone;
	}

	public void setTelephone(String telephone){
	    this.telephone = telephone;
	}

	public String getEmail(){
	    return email;
	}

	public void setEmail(String email){
	    this.email = email;
	}

	public String getPassword(){
	    return password;
	}

	public void setPassword(String password){
	    this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
