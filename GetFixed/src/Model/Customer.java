package Model;

public class Customer{
	private int id;
	private String name;
	private String surname;
	private String address;
	private String telephone;
	private String email;
	private int discount;
	
	public Customer(){
		
	}

	public Customer(int id, String name, String surname, String address, String telephone, String email, int discount){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.discount = discount;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}
