package com.techuniversity.foodordering;

import javax.swing.*;

public class DeliveryRunnerPanel extends JFrame {
    
    public DeliveryRunnerPanel() {
        setTitle("Delivery Runner Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Create tabs
        JPanel tasksDisplayPanel = new JPanel();
        tasksDisplayPanel.add(new JLabel("Tasks Display Panel"));
        tabbedPane.addTab("Tasks", tasksDisplayPanel);

        JPanel taskManagementPanel = new JPanel();
        taskManagementPanel.add(new JLabel("Task Management Panel"));
        tabbedPane.addTab("Manage Tasks", taskManagementPanel);

        JPanel taskHistoryPanel = new JPanel();
        taskHistoryPanel.add(new JLabel("Task History Panel"));
        tabbedPane.addTab("Task History", taskHistoryPanel);

        JPanel customerReviewsPanel = new JPanel();
        customerReviewsPanel.add(new JLabel("Customer Reviews Panel"));
        tabbedPane.addTab("Customer Reviews", customerReviewsPanel);

        JPanel revenueDashboardPanel = new JPanel();
        revenueDashboardPanel.add(new JLabel("Revenue Dashboard Panel"));
        tabbedPane.addTab("Revenue Dashboard", revenueDashboardPanel);

        add(tabbedPane);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ensure the GUI creation is done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new DeliveryRunnerPanel());
    }
}
