package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceCancelOrderPanel extends JPanel {
    private JTable menuTable;
    private DefaultTableModel menuTableModel;
    private JButton placeOrderButton;
    private JButton cancelOrderButton;
    private double itemPrice;
    private String selectedItem;

    public PlaceCancelOrderPanel() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#FFBF00")); // Background color
        JLabel headerLabel = new JLabel("Place/Cancel Order");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Table for displaying menu items
        menuTableModel = new DefaultTableModel(new String[]{"Item", "Description", "Price", "Image"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return Icon.class;
                }
                return super.getColumnClass(column);
            }
        };
        menuTable = new JTable(menuTableModel);
        menuTable.setRowHeight(100); // Set row height to accommodate image size
        menuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        menuTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Set preferred width for image column

        // Use a custom renderer for the image column
        menuTable.getColumnModel().getColumn(3).setCellRenderer(new ImageCellRenderer());

        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        loadMenu();

        // Create buttons
        placeOrderButton = new JButton("Place Order");
        cancelOrderButton = new JButton("Cancel Order");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(cancelOrderButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        placeOrderButton.addActionListener(new PlaceOrderActionListener());
        cancelOrderButton.addActionListener(new CancelOrderActionListener());
    }

    private void loadMenu() {
        List<MenuItem> menuItems = readMenuItemsFromFile("items.txt");
        for (MenuItem item : menuItems) {
            ImageIcon icon = new ImageIcon(item.getImagePath());
            // Resize the image if necessary
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            menuTableModel.addRow(new Object[]{item.getName(), item.getDescription(), item.getPrice(), icon});
        }
    }

    private List<MenuItem> readMenuItemsFromFile(String fileName) {
        List<MenuItem> menuItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    menuItems.add(new MenuItem(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading menu file.");
        }
        return menuItems;
    }

    // Custom table cell renderer for displaying images
    class ImageCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Icon) {
                JLabel label = new JLabel((Icon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class MenuItem {
        private String name;
        private String description;
        private String price;
        private String imagePath;

        public MenuItem(String name, String description, String price, String imagePath) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imagePath = imagePath;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getPrice() { return price; }
        public String getImagePath() { return imagePath; }
    }

    private class PlaceOrderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Please select an item from the menu.");
                return;
            }

            selectedItem = (String) menuTable.getValueAt(selectedRow, 0);
            itemPrice = Double.parseDouble((String) menuTable.getValueAt(selectedRow, 2));

            // Show input dialog for quantity
            String quantityStr = JOptionPane.showInputDialog("Enter quantity for " + selectedItem + ":");
            if (quantityStr == null || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Quantity cannot be empty.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Quantity must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Invalid quantity.");
                return;
            }

            double totalPrice = itemPrice * quantity;

            // Show confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                    PlaceCancelOrderPanel.this,
                    "Do you want to order this item?\nTotal Price: Rs. " + totalPrice,
                    "Order Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                // Proceed with order details
                String[] options = {"Dine-In", "Takeaway", "Delivery"};
                String choice = (String) JOptionPane.showInputDialog(
                        PlaceCancelOrderPanel.this,
                        "Select delivery option:",
                        "Delivery Option",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == null) {
                    return; // User cancelled
                }

                if (choice.equals("Delivery")) {
                    totalPrice += 50; // Delivery charge
                }

                // Collect customer details
                String name = JOptionPane.showInputDialog("Enter your name:");
                String email = JOptionPane.showInputDialog("Enter your email:");
                String address = JOptionPane.showInputDialog("Enter your address:");
                String phone = JOptionPane.showInputDialog("Enter your phone number:");

                // Save order to file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%d,%.2f%n",
                            name,
                            email,
                            address,
                            phone,
                            selectedItem,
                            quantity,
                            totalPrice
                    ));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Error saving order.");
                }

                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Order placed successfully!");
            }
        }
    }

    private class CancelOrderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> orders = readOrdersFromFile("orders.txt");

            if (orders.isEmpty()) {
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "No orders to cancel.");
                return;
            }

            String selectedOrder = (String) JOptionPane.showInputDialog(
                    PlaceCancelOrderPanel.this,
                    "Select the order to cancel:",
                    "Cancel Order",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    orders.toArray(),
                    orders.get(0)
            );

            if (selectedOrder == null) {
                return; // User cancelled
            }

            // Remove selected order from file
            try {
                removeOrderFromFile("orders.txt", selectedOrder);
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Order cancelled successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(PlaceCancelOrderPanel.this, "Error canceling order.");
            }
        }
    }

    private List<String> readOrdersFromFile(String fileName) {
        List<String> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private void removeOrderFromFile(String fileName, String orderToRemove) throws IOException {
        File inputFile = new File(fileName);
        File tempFile = new File("temp_" + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(orderToRemove)) {
                    writer.write(line + System.lineSeparator());
                }
            }
        }

        if (!inputFile.delete()) {
            throw new IOException("Could not delete the original file.");
        }
        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Could not rename the temporary file.");
        }
    }
}
