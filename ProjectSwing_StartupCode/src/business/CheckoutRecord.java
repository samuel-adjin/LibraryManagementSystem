package business;

import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord {
    private List<CheckoutEntry> entries;

    public CheckoutRecord() { this.entries = new ArrayList<>(); }

    public void addEntry(CheckoutEntry entry) { entries.add(entry); }

    public List<CheckoutEntry> getEntries() { return entries; }

	@Override
	public String toString() {
		return "CheckoutRecord [entries=" + entries + ", getEntries()=" + getEntries() + "]";
	}
    
    
}
