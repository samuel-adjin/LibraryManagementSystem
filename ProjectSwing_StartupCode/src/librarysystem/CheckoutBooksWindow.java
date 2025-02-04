package librarysystem;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

class CheckoutBooksWindow extends JFrame {
    public CheckoutBooksWindow() {
        setTitle("Checkout Books");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Checkout Books Section");
        add(label);
    }
}
