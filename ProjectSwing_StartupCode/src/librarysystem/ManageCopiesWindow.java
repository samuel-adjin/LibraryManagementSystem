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
        populateBookDropdown(); // Populate dropdown with book titles and ISBNs
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
        addButton.addActionListener(e -> addCopies()); // Call addCopies method
        cancelButton.addActionListener(e -> dispose()); // Close the window
    }

    /**
     * Handles the "Add Copies" button click.
     */
    private void addCopies() {
        String selectedBookTitle = (String) bookDropdown.getSelectedItem();
        String isbn = bookTitleToIsbnMap.get(selectedBookTitle); // Get ISBN from the map
        int copiesToAdd = (int) copiesSpinner.getValue();

        if (isbn == null || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid book.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            systemController.addBookCopy(isbn, copiesToAdd); // Call the addBookCopy method
            JOptionPane.showMessageDialog(this, copiesToAdd + " copies added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            homeWindow.refreshTable(); // Update HomeWindow table
            dispose(); // Close the window
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Populates the book dropdown with book titles and maps them to their ISBNs.
     */
    private void populateBookDropdown() {
        var bookTitles = systemController.getAllBookTitles();
        Map<String, Book> booksMap = systemController.fetchAllBooks().stream()
                .collect(HashMap::new, (map, book) -> map.put(book.getIsbn(), book), HashMap::putAll);

        for (Map.Entry<String, Book> entry : booksMap.entrySet()) {
            String isbn = entry.getKey();
            String title = entry.getValue().getTitle();
            bookDropdown.addItem(title);
            bookTitleToIsbnMap.put(title, isbn); // Map title to ISBN
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

        manageCopiesButton.addActionListener(e -> new ManageCopiesWindow(homeWindow).setVisible(true));
        sideMenu.add(manageCopiesButton);
    }
}