package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckoutEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewBook(Book book);
	public void addBookCopy(String book, int copyNum);
	public String checkoutBook(String memberId, String isbn);
	public  HashMap<String,CheckoutEntry> readBooksCheckoutMap();
}
