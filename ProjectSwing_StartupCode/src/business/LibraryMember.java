package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;	
		this.checkoutRecord = new CheckoutRecord();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	
	  public CheckoutRecord getCheckoutRecord() {
	        // Add null check and lazy initialization
	        if (checkoutRecord == null) {
	            checkoutRecord = new CheckoutRecord();
	        }
	        return checkoutRecord;
	    }
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
