package business;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
	private static final long serialVersionUID = 3665880920647848288L;
	private String firstName;
	private String lastName;
	private String telephone;
	private Address address;
	
	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, lastName, telephone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(telephone, other.telephone);
	}
	public Person(String f, String l, String t, Address a) {
		firstName = f;
		lastName = l;
		telephone = t;
		address = a;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public Address getAddress() {
		return address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
