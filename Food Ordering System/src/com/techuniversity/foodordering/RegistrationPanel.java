package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationPanel extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JComboBox<String> userTypeComboBox;
    private JButton registerButton;

    public RegistrationPanel() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#FFBF00")); // Background color
        JLabel headerLabel = new JLabel("User Registration");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Panel for registration inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // User Type
        inputPanel.add(new JLabel("User Type:"));
        userTypeComboBox = new JComboBox<>(new String[]{"Customer", "Vendor"});
        inputPanel.add(userTypeComboBox);

        // Name field
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        // Email field
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        // Phone field
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        // Address field
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        add(inputPanel, BorderLayout.CENTER);

        // Register button
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterActionListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userType = (String) userTypeComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrationPanel.this, "All fields must be filled out.");
                return;
            }

            // Save user details to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                writer.write(String.format("%s,%s,%s,%s,%s%n", userType, name, email, phone, address));
                writer.flush();
                JOptionPane.showMessageDialog(RegistrationPanel.this, "User registered successfully!");
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                addressField.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(RegistrationPanel.this, "Error saving user details.");
            }
        }
    }
}
