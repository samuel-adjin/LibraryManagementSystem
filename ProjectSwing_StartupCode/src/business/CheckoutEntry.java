package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable {
	private static final long serialVersionUID = -2826193306799714013L;
	private BookCopy bookCopy;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    public CheckoutEntry(BookCopy bookCopy, int daysAllowed) {
        this.bookCopy = bookCopy;
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(daysAllowed);
    }

    public BookCopy getBookCopy() { return bookCopy; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }


	@Override
	public String toString() {
		return "CheckoutEntry [bookCopy=" + bookCopy + ", checkoutDate=" + checkoutDate + ", dueDate=" + dueDate
				+ ", getBookCopy()=" + getBookCopy() + ", getCheckoutDate()=" + getCheckoutDate() + ", getDueDate()="
				+ getDueDate() + "]";
	}
	
	
	
}
