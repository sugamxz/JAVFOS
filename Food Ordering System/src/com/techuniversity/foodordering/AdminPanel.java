package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add a welcome tab
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome Administrator!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        tabbedPane.add("Welcome", welcomePanel);

        // Add the registration panel
        RegistrationPanel registrationPanel = new RegistrationPanel();
        tabbedPane.add("Register User", registrationPanel);

        // Add other admin functionality tabs here if needed
        // Example: tabbedPane.add("Other Functionality", new OtherFunctionalityPanel());

        // Add the tabbed pane to the frame
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel().setVisible(true));
    }
}
