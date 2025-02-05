package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	@Override
	public List<Book> fetchAllBooks() {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		List<Book> books = new ArrayList<>();
		for (Entry<String, Book> entry : da.readBooksMap().entrySet()) {
            Book value = entry.getValue();
            books.add(value);
        }
		return books;
	}



	@Override
	public void addLibraryMember(LibraryMember member) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}
	
	@Override
	 public boolean isMemberExists(String memberId) {
	        DataAccess da = new DataAccessFacade();
	        HashMap<String, LibraryMember> members = da.readMemberMap();
	        return members.containsKey(memberId); // Returns true if the ID already exists
	    }
	@Override
	public boolean isBookExists(String isbn) {
		// TODO Auto-generated method stub
		 DataAccess da = new DataAccessFacade();
	     HashMap<String, Book> books = da.readBooksMap();
	    return books.containsKey(isbn);
	}
	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
		
	}
	@Override
	public void addBookCopy(String isbn, int copyNum) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.addBookCopy(isbn, copyNum);
		
	}
	@Override
	public List<Book> getAllBookTitles() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<CheckoutEntry> getCheckoutRecords(String memberId) {
		DataAccess da = new DataAccessFacade();
	    HashMap<String, LibraryMember> members = da.readMemberMap();
	    if (!members.containsKey(memberId)) {
	    	System.out.println("In here");
	        return Collections.emptyList(); 
	    }
	    System.out.println(members.get(memberId).getCheckoutRecord().getEntries());
	    return members.get(memberId).getCheckoutRecord().getEntries();
	}
	@Override
	public String checkoutBook(String memberId, String isbn) {
		// TODO Auto-generated method stub
		DataAccess da = new DataAccessFacade();
		da.checkoutBook(memberId, isbn);
		return "Ok";
	}
	
	
}
