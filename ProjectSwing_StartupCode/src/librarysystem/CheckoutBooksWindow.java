

package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;
import business.SystemController;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CheckoutBooksWindow extends JFrame {
    private JTextField memberIdField;
    private JTextField isbnField;
    private JButton checkoutButton;
    private JTable checkoutTable;
    private DefaultTableModel tableModel;
    private SystemController systemController;

    public CheckoutBooksWindow() {
        systemController = new SystemController();
        setTitle("Checkout Books");
        setSize(700, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField();
        inputPanel.add(memberIdField);

        inputPanel.add(new JLabel("Book ISBN:"));
        isbnField = new JTextField();
        inputPanel.add(isbnField);

        checkoutButton = new JButton("Checkout");
        inputPanel.add(checkoutButton);
        checkoutButton.addActionListener(e -> checkoutBook());

        add(inputPanel, BorderLayout.NORTH);

        // Table to display checkout records
        String[] columns = {"Member ID", "Book Title", "Copy Number", "Checkout Date", "Due Date"};
        tableModel = new DefaultTableModel(columns, 0);
        checkoutTable = new JTable(tableModel);
        add(new JScrollPane(checkoutTable), BorderLayout.CENTER);

        // Load all checkout records when the window opens
        updateCheckoutTable();
    }

    private void checkoutBook() {
        String memberId = memberIdField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (memberId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Member ID and ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String result = systemController.checkoutBook(memberId, isbn);
//            JOptionPane.showMessageDialog(this, result, "Success", "Book checked out successfully");
            JOptionPane.showMessageDialog(this, "Book Checked Out Successfully");
            // Refresh the entire table to reflect the new checkout
            updateCheckoutTable();

        } catch (IllegalArgumentException ex) {
        	  JOptionPane.showMessageDialog(this, "No copies available");
        }
    }

    private void updateCheckoutTable() {
        tableModel.setRowCount(0); // Clear the table

        // Get all members
        HashMap<String, LibraryMember> members = systemController.getAllLibraryMembers();

        // Iterate over each member's checkout record and add entries to the table
        members.forEach((memberId, member) -> {
            CheckoutRecord checkoutRecord = member.getCheckoutRecord();
            if (checkoutRecord != null) {
                for (CheckoutEntry entry : checkoutRecord.getEntries()) {
                    tableModel.addRow(new Object[]{
                        memberId,
                        entry.getBookCopy().getBook().getTitle(),
                        entry.getBookCopy().getCopyNum(),
                        entry.getCheckoutDate(),
                        entry.getDueDate()
                    });
                }
            }
        });
    }

   
}

