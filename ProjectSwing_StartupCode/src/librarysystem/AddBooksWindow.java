package librarysystem;

import javax.swing.*;

import business.Author;
import business.Book;
import business.SystemController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddBooksWindow extends JFrame {
    private JTextField isbnField, titleField;
    private JComboBox<Integer> maxCheckoutDropdown;
    private JTextField firstNameField, lastNameField, phoneField, bioField;
    private JList<Author> authorList;
    private DefaultListModel<Author> authorListModel;
    private JButton addBookButton, addAuthorButton;
    private List<Author> authors;
    private HomeWindow homeWindow; // ✅ Reference to HomeWindow for table update

    public AddBooksWindow() {
        authors = new ArrayList<>();

        setTitle("Add Books");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 🟢 ISBN Field
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        isbnField = new JTextField(15);
        add(isbnField, gbc);

        // 🟢 Title Field
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(15);
        add(titleField, gbc);

        // 🟢 Max Checkout Length Dropdown
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Max Checkout Length (days):"), gbc);
        gbc.gridx = 1;
        maxCheckoutDropdown = new JComboBox<>(new Integer[]{7, 21});
        add(maxCheckoutDropdown, gbc);

        // ====================== AUTHOR INPUT FIELDS ==========================
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(new JLabel("Add Author:"), gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Bio:"), gbc);
        gbc.gridx = 1;
        bioField = new JTextField(15);
        add(bioField, gbc);

        // 🟢 Add Author Button
        addAuthorButton = new JButton("Add Author");
        gbc.gridy = 8; gbc.gridwidth = 2;
        add(addAuthorButton, gbc);

        // 🟢 Authors List
        gbc.gridy = 9; gbc.gridwidth = 2;
        add(new JLabel("Selected Authors:"), gbc);

        authorListModel = new DefaultListModel<>();
        authorList = new JList<>(authorListModel);
        authorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(authorList);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        gbc.gridy = 10;
        add(scrollPane, gbc);

        // 🟢 Add Book Button
        addBookButton = new JButton("Add Book");
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        add(addBookButton, gbc);

        // 🔹 Add Author Button Action
        addAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateAuthorFields()) {
                    return;
                }

                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String phone = phoneField.getText().trim();
                String bio = bioField.getText().trim();

                Author newAuthor = new Author(firstName, lastName, phone, null, bio);
                authors.add(newAuthor);
                authorListModel.addElement(newAuthor);

                // Clear input fields
                firstNameField.setText("");
                lastNameField.setText("");
                phoneField.setText("");
                bioField.setText("");
            }
        });

        // 🔹 Add Book Button Action
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateBookFields()) {
                    return;
                }

                String isbn = isbnField.getText().trim();
                String title = titleField.getText().trim();
                int maxCheckoutLength = (int) maxCheckoutDropdown.getSelectedItem();
                List<Author> selectedAuthors = new ArrayList<>(authors);

                SystemController systemController = new SystemController();

                // 🔍 Check if the book with the same ISBN already exists
                if (systemController.isBookExists(isbn)) {
                    JOptionPane.showMessageDialog(AddBooksWindow.this, "A book with this ISBN already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book newBook = new Book(isbn, title, maxCheckoutLength, selectedAuthors);
                systemController.addBook(newBook);

                JOptionPane.showMessageDialog(AddBooksWindow.this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // ✅ Refresh the HomeWindow table if available
                if (homeWindow != null) {
                    homeWindow.refreshTable();
                }

                dispose(); // Close window after success
            }
        });
    }

    // ✅ Set HomeWindow reference
    public void setHomeWindow(HomeWindow homeWindow) {
        this.homeWindow = homeWindow;
    }

    // ✅ Validates that all fields for a book are filled
    private boolean validateBookFields() {
        if (isbnField.getText().trim().isEmpty()) {
            showError("ISBN is required!");
            return false;
        }
        if (titleField.getText().trim().isEmpty()) {
            showError("Title is required!");
            return false;
        }
        if (authors.isEmpty()) {
            showError("At least one author must be added!");
            return false;
        }
        return true;
    }

    // ✅ Validates author input fields before adding
    private boolean validateAuthorFields() {
        if (firstNameField.getText().trim().isEmpty()) {
            showError("First name is required!");
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            showError("Last name is required!");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showError("Phone number is required!");
            return false;
        }
        if (bioField.getText().trim().isEmpty()) {
            showError("Bio is required!");
            return false;
        }
        return true;
    }

    // 📢 Helper method to show error messages
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
