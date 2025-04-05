package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckoutRecord implements Serializable {
    private List<CheckoutEntry> entries;
	private static final long serialVersionUID =1L;

    public CheckoutRecord() { this.entries = new ArrayList<>(); }

    public void addEntry(CheckoutEntry entry) { 
    	System.out.println("adding entries");
    	System.out.println(entry);
    	entries.add(entry); 
    	System.out.println(entries);
    	}

    public List<CheckoutEntry> getEntries() { return entries; }


	@Override
	public String toString() {
		return "CheckoutRecord [entries=" + entries + ", getEntries()=" + getEntries() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(entries);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckoutRecord other = (CheckoutRecord) obj;
		return Objects.equals(entries, other.entries);
	}
	
	
    
    
}
