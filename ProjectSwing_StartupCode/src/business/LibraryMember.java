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
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	
	  public CheckoutRecord getCheckoutRecord() {
	        return checkoutRecord;
	    }
	  
	  
//	@Override
//	public String toString() {
//		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
//				", " + getTelephone() + " " + getAddress();
//	}
	  
	  

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}


	private static final long serialVersionUID = -2226197306790714013L;

	@Override
	public String toString() {
		return "LibraryMember [memberId=" + memberId + ", checkoutRecord=" + checkoutRecord + ", getMemberId()="
				+ getMemberId() + ", getCheckoutRecord()=" + getCheckoutRecord() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getTelephone()=" + getTelephone() + ", getAddress()="
				+ getAddress() + "]";
	}
}
