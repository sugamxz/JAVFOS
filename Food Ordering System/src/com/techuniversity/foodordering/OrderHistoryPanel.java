package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class OrderHistoryPanel extends JPanel {
    public OrderHistoryPanel() {
        setLayout(new BorderLayout());

        // Placeholder for order history details
        JTextArea orderHistoryTextArea = new JTextArea();
        orderHistoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderHistoryTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Dummy data for order history
        orderHistoryTextArea.setText("Order History:\n\nOrderID: 001\nCustomer: John Doe\nStatus: Delivered\nAmount: $25.00\n\n" +
                "OrderID: 002\nCustomer: Jane Smith\nStatus: Cancelled\nAmount: $15.00\n");
    }
}
