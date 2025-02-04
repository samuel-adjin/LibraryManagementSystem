package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import business.Book;
import business.SystemController;
import dataaccess.Auth;

import java.awt.*;
import java.util.List;

public class HomeWindow extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public HomeWindow() {
        // Set up main frame
        setTitle("Library Management System");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🟢 Create side menu (Modern Dark UI)
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setPreferredSize(new Dimension(180, 0));
        sideMenu.setBackground(new Color(30, 30, 30));

        // Sidebar Buttons
        JButton homeButton = createSidebarButton("🏠 Home");
        JButton memberButton = createSidebarButton("👥 Manage Members");
        JButton addButton = createSidebarButton("📚 Add Books");
        JButton manageButton = createSidebarButton("📚 Manage Copies");
        JButton checkoutButton = createSidebarButton("✔️ Checkout");
        JButton logoutButton = createSidebarButton("🚪 Logout");

        // Only administrators can manage members
        if (SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH) {
            sideMenu.add(memberButton);
        }

        // Only administrators can add books
        if (SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH) {
            sideMenu.add(addButton);
        }
        
        if (SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH) {
            sideMenu.add(manageButton);
        }

        // Only librarians can checkout books
        if (SystemController.currentAuth == Auth.LIBRARIAN || SystemController.currentAuth == Auth.BOTH) {
            sideMenu.add(checkoutButton);
        }

        sideMenu.add(logoutButton);

        // 🟢 Modern Main Content Panel
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🟢 Create Modern Table
        String[] columns = {"ISBN", "Title", "Availability", "Copies"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);

        // Load initial data
        refreshTable();

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        // 🟢 Button Actions
        memberButton.addActionListener(e -> new ManageMembersWindow().setVisible(true));

        addButton.addActionListener(e -> {
            AddBooksWindow addBooksWindow = new AddBooksWindow();
            addBooksWindow.setHomeWindow(this); // ✅ Pass reference to update table
            addBooksWindow.setVisible(true);
        });
        
        manageButton.addActionListener(e -> {
            ManageCopiesWindow manageCopiesWindow = new ManageCopiesWindow(this);
//            manageCopiesWindow.setHomeWindow(this); // ✅ Pass reference to update table
            manageCopiesWindow.setVisible(true);
        });

        checkoutButton.addActionListener(e -> new CheckoutBooksWindow().setVisible(true));

        logoutButton.addActionListener(e -> {
            LoginWindow.INSTANCE.setVisible(true);
            dispose(); // Close Home Window
        });

        // 🟢 Add Components to Frame
        add(sideMenu, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    // ✅ Refresh the table when a book is added
    public void refreshTable() {
        model.setRowCount(0); // Clear old data
        SystemController sys = new SystemController();
        List<Book> books = sys.fetchAllBooks();

        for (Book book : books) {
            model.addRow(new Object[]{book.getIsbn(), book.getTitle(), book.isAvailable(), book.getCopies().length});
        }
    }

    // 🔹 Modern Sidebar Button
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 40));
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
        });

        return button;
    }

    // 🔹 Modern Table Styling
    private void styleTable(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(200, 230, 201)); // Light Green
        table.setSelectionForeground(Color.BLACK);

        // Zebra Stripes Effect
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                }
                return c;
            }
        });

        // Modern Table Header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.WHITE);
    }
}
