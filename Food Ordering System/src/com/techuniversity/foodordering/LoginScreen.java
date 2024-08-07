package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton registerButton;

    public LoginScreen() {
        setTitle("University Food Ordering System - Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Welcome to University Food Ordering System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        String[] roles = {"Vendor", "Customer", "Delivery Runner", "Administrator"};
        roleComboBox = new JComboBox<>(roles);
        formPanel.add(roleComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(60, 179, 113));
        loginButton.setForeground(Color.WHITE);
        formPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(255, 165, 0));
        registerButton.setForeground(Color.WHITE);
        formPanel.add(registerButton, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] password = passwordField.getPassword();
                String role = (String) roleComboBox.getSelectedItem();
                if (validateLogin(email, new String(password), role)) {
                    openPanel(role);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please register first.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationScreen();
            }
        });
    }

    private boolean validateLogin(String email, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    String registeredEmail = details[0];
                    String registeredPassword = details[1];
                    String registeredRole = details[2];
                    if (registeredEmail.equals(email) && registeredPassword.equals(password) && registeredRole.equals(role)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void openPanel(String role) {
        JFrame panelFrame;
        switch (role) {
            case "Vendor":
                panelFrame = new VendorPanel();
                break;
            case "Customer":
                panelFrame = new CustomerPanel();
                break;
            case "Delivery Runner":
                panelFrame = new DeliveryRunnerPanel();
                break;
            case "Administrator":
                panelFrame = new AdminPanel();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
        panelFrame.setVisible(true);
        this.dispose(); // Close the login screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}

class RegistrationScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;

    public RegistrationScreen() {
        setTitle("University Food Ordering System - Register");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        String[] roles = {"Vendor", "Customer", "Delivery Runner", "Administrator"};
        roleComboBox = new JComboBox<>(roles);
        formPanel.add(roleComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        formPanel.add(registerButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] password = passwordField.getPassword();
                String role = (String) roleComboBox.getSelectedItem();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                    writer.write(email + "," + new String(password) + "," + role);
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Registered successfully!");
                    dispose(); // Close the registration window
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
