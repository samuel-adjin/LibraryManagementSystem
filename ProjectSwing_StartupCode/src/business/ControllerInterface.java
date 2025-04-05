package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<Book> fetchAllBooks();
	public void addLibraryMember(LibraryMember member);
	public boolean isMemberExists(String memberId);
	public boolean isBookExists(String isbn);
	public void addBook(Book book);
	public void addBookCopy(String isbn, int copyNum);
	public List<Book>getAllBookTitles();
	public String checkoutBook(String memberId, String isbn);
	public List<CheckoutEntry> getCheckoutRecords(String memberId);
	public List<CheckoutEntry> allBooksCheckout();
	public HashMap<String, LibraryMember>  getAllLibraryMembers();
}
