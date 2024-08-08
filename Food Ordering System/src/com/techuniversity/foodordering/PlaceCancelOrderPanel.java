package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class PlaceCancelOrderPanel extends JPanel {
    public PlaceCancelOrderPanel() {
        setLayout(new BorderLayout());

        // Placeholder for Place/Cancel Order Panel
        JLabel placeholderLabel = new JLabel("Place/Cancel Order Panel");
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);

        // You can add more components here for place/cancel order functionality
    }
}
