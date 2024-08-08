package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class ProvideReviewPanel extends JPanel {
    public ProvideReviewPanel() {
        setLayout(new BorderLayout());

        // Placeholder for Provide a Review Panel
        JLabel placeholderLabel = new JLabel("Provide a Review Panel");
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);

        // You can add more components here for providing a review functionality
    }
}

