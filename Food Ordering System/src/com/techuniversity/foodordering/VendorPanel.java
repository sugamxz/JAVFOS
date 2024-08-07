package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class VendorPanel extends JFrame {
    public VendorPanel() {
        setTitle("Vendor Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Manage Items", new ManageItemsPanel());
        tabbedPane.add("Order Management", new OrderManagementPanel());
        tabbedPane.add("Order History", new OrderHistoryPanel());
        tabbedPane.add("Customer Reviews", new CustomerReviewsPanel());
        tabbedPane.add("Revenue Dashboard", new RevenueDashboardPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Redirect to login screen
                dispose(); // Close the VendorPanel
                new LoginScreen().setVisible(true); // Open the LoginScreen
            }
        });
        add(logoutButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendorPanel().setVisible(true));
    }
}
