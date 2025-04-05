package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS,BOOKCHECKOUT;
	}
//	"C:\Users\faraday\Desktop\MPP\project\ProjectSwing_StartupCode\ProjectSwing_StartupCode\src\dataaccess\storage\USERS"
//	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
//			+ "\\src\\dataaccess\\storage";
	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
			+ "\\ProjectSwing_StartupCodesrc\\dataacces\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	//implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);	
	}
	
	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String bookId = book.getIsbn();
		books.put(bookId, book);
		saveToStorage(StorageType.BOOKS, books);	
	}
	
	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
	
		
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}
 
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath("C:/Users/faraday/Desktop/MPP/project/ProjectSwing_StartupCode/ProjectSwing_StartupCode/src/dataaccess/storage", type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath("C:/Users/faraday/Desktop/MPP/project/ProjectSwing_StartupCode/ProjectSwing_StartupCode/src/dataaccess/storage", type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	
	final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}



	@Override
	public void addBookCopy(String isbn, int copyNum) {
		// TODO Auto-generated method stub
		 HashMap<String, Book> books = this.readBooksMap();	
		 if(books.containsKey(isbn)) {
			 Book foundBook = books.get(isbn);
			  foundBook.addCopy(copyNum);
			  saveToStorage(StorageType.BOOKS, books);
		 } 
	}

	@Override
	public String checkoutBook(String memberId, String isbn) {
	    HashMap<String, LibraryMember> mems = readMemberMap();
	    HashMap<String, Book> books = this.readBooksMap();
	    
	   
	    if (!mems.containsKey(memberId)) {
	        return "Error: Member ID not found.";
	    }
	    if (!books.containsKey(isbn)) {
	        return "Error: Book not found.";
	    }

	    Book foundBook = books.get(isbn);
	    LibraryMember libraryMember = mems.get(memberId);
	    BookCopy[] bookCopies = foundBook.getCopies();
	    System.out.println("hghghghhg.");

	    System.out.println(Arrays.toString(bookCopies));

	    Optional<BookCopy> availableCopy = Arrays.stream(bookCopies).filter(BookCopy::isAvailable ).findFirst();
	    if (!availableCopy.isPresent()) {
	        throw new IllegalArgumentException();
	    }
	   
	    int checkoutDays = availableCopy.get().getBook().getMaxCheckoutLength();
	    CheckoutEntry entry = new CheckoutEntry(availableCopy.get(), checkoutDays);

	    // Ensure checkoutRecord is not null
	    if( libraryMember.getCheckoutRecord() == null) {
	    	 libraryMember.setCheckoutRecord(new CheckoutRecord());
	    }
		    libraryMember.getCheckoutRecord().addEntry(entry);
		    saveToStorage(StorageType.MEMBERS, mems);
		    HashMap<String, CheckoutEntry> entries = new HashMap<>();
		    entries.put(isbn, entry);
		    saveToStorage(StorageType.BOOKCHECKOUT, entries);
		  
		    
	    availableCopy.get().changeAvailability();
	    saveToStorage(StorageType.BOOKS, books);
	    
	    System.out.println(" System.out.println(availableCopy);");
		   System.out.println(availableCopy);
	    return "Checked out: " + foundBook.getTitle() + " (Copy #" + availableCopy.get().getCopyNum() + ") until " + entry.getDueDate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, CheckoutEntry> readBooksCheckoutMap() {
		// TODO Auto-generated method stub
		return (HashMap<String, CheckoutEntry>)readFromStorage(StorageType.BOOKCHECKOUT);
	}

	
}
