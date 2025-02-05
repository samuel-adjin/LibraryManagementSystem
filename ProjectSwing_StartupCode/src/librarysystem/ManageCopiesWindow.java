package librarysystem;

import javax.swing.*;

import business.Book;
import business.SystemController;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ManageCopiesWindow extends JFrame {
    private JComboBox<String> bookDropdown;
    private JSpinner copiesSpinner;
    private JButton addButton;
    private JButton cancelButton;
    private SystemController systemController;
    private HomeWindow homeWindow;
    private Map<String, String> bookTitleToIsbnMap; // Maps book titles to ISBNs

    public ManageCopiesWindow(HomeWindow homeWindow) {
        this.homeWindow = homeWindow;
        systemController = new SystemController();
        bookTitleToIsbnMap = new HashMap<>();

        setTitle("Manage Copies");
        setSize(400, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Dropdown for book selection
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Select Book:"), gbc);
        gbc.gridx = 1;
        bookDropdown = new JComboBox<>();
        add(bookDropdown, gbc);

        // Spinner for adding copies
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Number of Copies:"), gbc);
        gbc.gridx = 1;
        copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        add(copiesSpinner, gbc);

        // Button to add copies
        addButton = new JButton("Add Copies");
        gbc.gridx = 1; gbc.gridy = 2;
        add(addButton, gbc);

        // Button to cancel
        cancelButton = new JButton("Cancel");
        gbc.gridx = 0; gbc.gridy = 2;
        add(cancelButton, gbc);

        // Add action listeners
        addButton.addActionListener(e -> addCopies());
        cancelButton.addActionListener(e -> dispose());

        // Populate dropdown
        populateBookDropdown();
    }

    /**
     * Handles the "Add Copies" button click.
     */
    private void addCopies() {
        String selectedBookTitle = (String) bookDropdown.getSelectedItem();
        if (selectedBookTitle == null || selectedBookTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a book.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isbn = bookTitleToIsbnMap.get(selectedBookTitle);
        int copiesToAdd = (int) copiesSpinner.getValue();

        try {
            systemController.addBookCopy(isbn, copiesToAdd);
            JOptionPane.showMessageDialog(this, copiesToAdd + " copies added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            homeWindow.refreshTable();
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Populates the book dropdown with book titles and maps them to their ISBNs.
     */
    private void populateBookDropdown() {
        var books = systemController.fetchAllBooks();
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books available.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Book book : books) {
            String title = book.getTitle();
            bookDropdown.addItem(title);
            bookTitleToIsbnMap.put(title, book.getIsbn());
        }
    }

    /**
     * Adds a "Manage Copies" button to the side menu.
     */
    public static void addManageCopiesButton(JPanel sideMenu, HomeWindow homeWindow) {
        JButton manageCopiesButton = new JButton("📖 Manage Copies");
        manageCopiesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageCopiesButton.setMaximumSize(new Dimension(160, 40));
        manageCopiesButton.setBackground(new Color(50, 50, 50));
        manageCopiesButton.setForeground(Color.WHITE);
        manageCopiesButton.setFocusPainted(false);
        manageCopiesButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        manageCopiesButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        manageCopiesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageCopiesButton.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageCopiesButton.setBackground(new Color(50, 50, 50));
            }
        });

        manageCopiesButton.addActionListener(e -> SwingUtilities.invokeLater(() -> new ManageCopiesWindow(homeWindow).setVisible(true)));
        sideMenu.add(manageCopiesButton);
    }
}
