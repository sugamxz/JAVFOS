package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProvideReviewPanel extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextArea reviewTextArea;
    private JSpinner ratingSpinner;
    private JButton submitButton;

    public ProvideReviewPanel() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#FFBF00")); // Background color
        JLabel headerLabel = new JLabel("Provide Your Review");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Panel for review inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name field
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        // Email field
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        // Review text area
        inputPanel.add(new JLabel("Review:"));
        reviewTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);
        inputPanel.add(scrollPane);

        // Rating spinner
        inputPanel.add(new JLabel("Rating (1-5):"));
        ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        inputPanel.add(ratingSpinner);

        add(inputPanel, BorderLayout.CENTER);

        // Submit button
        submitButton = new JButton("Submit Review");
        submitButton.addActionListener(new SubmitReviewActionListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class SubmitReviewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String review = reviewTextArea.getText().trim();
            int rating = (int) ratingSpinner.getValue();

            if (name.isEmpty() || email.isEmpty() || review.isEmpty()) {
                JOptionPane.showMessageDialog(ProvideReviewPanel.this, "All fields must be filled out.");
                return;
            }

            // Save review to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("reviews.txt", true))) {
                writer.write(String.format("%s,%s,%d,%s%n", name, email, rating, review));
                writer.flush();
                JOptionPane.showMessageDialog(ProvideReviewPanel.this, "Review submitted successfully!");
                nameField.setText("");
                emailField.setText("");
                reviewTextArea.setText("");
                ratingSpinner.setValue(1);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ProvideReviewPanel.this, "Error saving review.");
            }
        }
    }
}
