package business;

import java.io.Serializable;
import java.util.Objects;

/* Immutable */
final public class Address implements Serializable {
	
	@Override
	public int hashCode() {
		return Objects.hash(city, state, street, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
	}
	private static final long serialVersionUID = -891229800414574888L;
	private String street;
	private String city;
	private String state;
	private String zip;
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	@Override
	public String toString() {
		return "(" + street + ", " + city + ", " + zip + ")";
		
	}
}
