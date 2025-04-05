package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;
import business.SystemController;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class BookOverDueWindow extends JFrame {
    private JTable overdueTable;
    private DefaultTableModel tableModel;
    private SystemController systemController;

    public BookOverDueWindow() {
        systemController = new SystemController(); // Controller to fetch overdue books
        setTitle("Overdue Books");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table Setup
        String[] columns = {"Book Title", "Member ID", "Checkout Date", "Due Date"};
        tableModel = new DefaultTableModel(columns, 0);
        overdueTable = new JTable(tableModel);
        add(new JScrollPane(overdueTable), BorderLayout.CENTER);

        // Refresh Button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadOverdueBooks());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load overdue books initially
        loadOverdueBooks();
    }

    private void loadOverdueBooks() {
        tableModel.setRowCount(0); // Clear table

        HashMap<String, LibraryMember> members = systemController.getAllLibraryMembers(); // Fetch all members

        members.forEach((memberId, member) -> {
            CheckoutRecord checkoutRecord = member.getCheckoutRecord();
            if (checkoutRecord != null) {
                for (CheckoutEntry entry : checkoutRecord.getEntries()) {
                    if (entry.getDueDate().isBefore(LocalDate.now())) { // Check if overdue
                        tableModel.addRow(new Object[]{
                            memberId, // Member ID
                            entry.getBookCopy().getBook().getTitle(),
                            entry.getBookCopy().getCopyNum(),
                            entry.getCheckoutDate(),
                            entry.getDueDate()
                        });
                    }
                }
            }
        });

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No overdue books.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

