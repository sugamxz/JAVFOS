package com.techuniversity.foodordering;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class OrderHistoryPanel extends JPanel {
    public OrderHistoryPanel(List<Order> orders) {
        setLayout(new BorderLayout());

        JTextArea orderHistoryTextArea = new JTextArea();
        orderHistoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderHistoryTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Display order history from the provided list
        StringBuilder orderHistory = new StringBuilder("Order History:\n\n");
        for (Order order : orders) {
            orderHistory.append("OrderID: ").append(order.getOrderId()).append("\n")
                    .append("Customer: ").append(order.getCustomer()).append("\n")
                    .append("Date: ").append(order.getDate()).append("\n")
                    .append("Status: ").append(order.getStatus()).append("\n\n");
        }
        orderHistoryTextArea.setText(orderHistory.toString());
    }
}
