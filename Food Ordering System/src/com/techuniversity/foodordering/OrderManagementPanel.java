package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderManagementPanel extends JPanel {
    private DefaultTableModel orderTableModel;

    public OrderManagementPanel() {
        setLayout(new BorderLayout());

        // Table for managing orders
        String[] columnNames = {"Order ID", "Customer Name", "Status", "Amount"};
        Object[][] data = {
            {"001", "John Doe", "Pending", "$25.00"},
            {"002", "Jane Smith", "Pending", "$15.00"}
        };
        orderTableModel = new DefaultTableModel(data, columnNames);
        JTable orderTable = new JTable(orderTableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Buttons for order management
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Accept Order button
        JButton acceptOrderButton = new JButton("Accept Order");
        acceptOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Update the status of the selected order to "Accepted"
                    orderTableModel.setValueAt("Accepted", selectedRow, 2);
                    // You can add code to save this update to a file or database
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an order to accept.");
                }
            }
        });
        buttonPanel.add(acceptOrderButton);

        // Cancel Order button
        JButton cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Update the status of the selected order to "Cancelled"
                    orderTableModel.setValueAt("Cancelled", selectedRow, 2);
                    // You can add code to save this update to a file or database
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an order to cancel.");
                }
            }
        });
        buttonPanel.add(cancelOrderButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
