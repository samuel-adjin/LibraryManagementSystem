

package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import business.SystemController;
import java.awt.*;

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
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
        String[] columns = {"Book Title", "Copy Number", "Checkout Date", "Due Date"};
        tableModel = new DefaultTableModel(columns, 0);
        checkoutTable = new JTable(tableModel);
        add(new JScrollPane(checkoutTable), BorderLayout.CENTER);
    }

    private void checkoutBook() {
        String memberId = memberIdField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (memberId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Member ID and ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            systemController.checkoutBook(memberId, isbn);
            JOptionPane.showMessageDialog(this, "Checkout successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateCheckoutTable(memberId);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCheckoutTable(String memberId) {
        tableModel.setRowCount(0); // Clear table
        systemController.getCheckoutRecords(memberId).forEach(record -> {
            tableModel.addRow(new Object[]{
                record.getBookCopy().getBook().getTitle(),
                record.getBookCopy().getBook().getNumCopies(),
                record.getCheckoutDate(),
                record.getDueDate()
            });
        });
    }
}
