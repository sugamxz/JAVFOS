package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class ReorderPanel extends JPanel {
    public ReorderPanel() {
        setLayout(new BorderLayout());

        // Placeholder for Reorder Using Order History Panel
        JLabel placeholderLabel = new JLabel("Reorder Using Order History Panel");
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);

        // You can add more components here for reorder functionality
    }
}
