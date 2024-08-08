package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class CheckOrderStatusPanel extends JPanel {
    public CheckOrderStatusPanel() {
        setLayout(new BorderLayout());

        // Placeholder for Check Order Status Panel
        JLabel placeholderLabel = new JLabel("Check Order Status Panel");
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);

        // You can add more components here for checking order status functionality
    }
}
