package com.techuniversity.foodordering;

import javax.swing.*;

public class CustomerPanel extends JFrame {
    public CustomerPanel() {
        setTitle("Customer Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("View Menu", new ViewMenuPanel());
        tabbedPane.add("Read Customer Review", new ReadCustomerReviewPanel());
        tabbedPane.add("Place/Cancel Order", new PlaceCancelOrderPanel());
        tabbedPane.add("Check Order Status", new CheckOrderStatusPanel());
        tabbedPane.add("Check Order History", new CheckOrderHistoryPanel());
        tabbedPane.add("Check Transaction History", new CheckTransactionHistoryPanel());
        tabbedPane.add("Provide a Review", new ProvideReviewPanel());
        tabbedPane.add("Reorder Using Order History", new ReorderPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerPanel().setVisible(true));
    }
}
