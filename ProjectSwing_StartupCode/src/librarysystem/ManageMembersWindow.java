package librarysystem;

import javax.swing.*;
import business.Address;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageMembersWindow extends JFrame {
    private JTextField idField, fnameField, lnameField, phoneField;
    private JTextField streetField, cityField, stateField, zipField;
    private JComboBox<Auth> roleDropdown;

    public ManageMembersWindow() {
        setTitle("Add Library Member");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 🟢 Member ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Member ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        add(idField, gbc);

        // 🟢 First Name
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        fnameField = new JTextField(15);
        add(fnameField, gbc);

        // 🟢 Last Name
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lnameField = new JTextField(15);
        add(lnameField, gbc);

        // 🟢 Phone Number
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        // 🟢 Address Section
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Street:"), gbc);
        gbc.gridx = 1;
        streetField = new JTextField(15);
        add(streetField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField(15);
        add(cityField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("State:"), gbc);
        gbc.gridx = 1;
        stateField = new JTextField(15);
        add(stateField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Zip Code:"), gbc);
        gbc.gridx = 1;
        zipField = new JTextField(15);
        add(zipField, gbc);

        // 🟢 Role Selection
        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleDropdown = new JComboBox<>(Auth.values()); // Dropdown for LIBRARIAN, ADMIN, BOTH
        add(roleDropdown, gbc);

        // 🟢 Add Member Button
        JButton addButton = new JButton("Add Member");
        gbc.gridx = 1; gbc.gridy = 9;
        add(addButton, gbc);

        // 🟢 Button Action to Save Member
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateFields()) {
                    return;
                }

                String id = idField.getText().trim();
                String fname = fnameField.getText().trim();
                String lname = lnameField.getText().trim();
                String phone = phoneField.getText().trim();
                String street = streetField.getText().trim();
                String city = cityField.getText().trim();
                String state = stateField.getText().trim();
                String zip = zipField.getText().trim();
                Auth role = (Auth) roleDropdown.getSelectedItem();

                SystemController systemController = new SystemController();

                // 🔍 Check if member already exists
                if (systemController.isMemberExists(id)) {
                    JOptionPane.showMessageDialog(ManageMembersWindow.this, "Member ID already exists. Unable to add!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Address address = new Address(street, city, state, zip);
                LibraryMember newMember = new LibraryMember(id, fname, lname, phone, address);
                systemController.addLibraryMember(newMember);

                JOptionPane.showMessageDialog(ManageMembersWindow.this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close window after success
            }
        });
    }

    // ✅ Validates that all fields are filled
    private boolean validateFields() {
        if (idField.getText().trim().isEmpty()) {
            showError("Member ID is required!");
            return false;
        }
        if (fnameField.getText().trim().isEmpty()) {
            showError("First Name is required!");
            return false;
        }
        if (lnameField.getText().trim().isEmpty()) {
            showError("Last Name is required!");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showError("Phone Number is required!");
            return false;
        }
        if (streetField.getText().trim().isEmpty()) {
            showError("Street is required!");
            return false;
        }
        if (cityField.getText().trim().isEmpty()) {
            showError("City is required!");
            return false;
        }
        if (stateField.getText().trim().isEmpty()) {
            showError("State is required!");
            return false;
        }
        if (zipField.getText().trim().isEmpty()) {
            showError("Zip Code is required!");
            return false;
        }
        return true;
    }

    // 📢 Helper method to show error messages
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
