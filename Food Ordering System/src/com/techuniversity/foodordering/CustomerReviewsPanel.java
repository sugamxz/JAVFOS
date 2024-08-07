package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class CustomerReviewsPanel extends JPanel {
    public CustomerReviewsPanel() {
        setLayout(new BorderLayout());

        // Placeholder for customer reviews
        JTextArea reviewsTextArea = new JTextArea();
        reviewsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reviewsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Dummy data for customer reviews
        reviewsTextArea.setText("Customer Reviews:\n\nJohn Doe: Great service and food!\n\nJane Smith: The delivery was lat.");
    }
}
