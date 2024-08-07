package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class VendorPanel extends JFrame {
    public VendorPanel() {
        setTitle("Vendor Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Manage Items", new ManageItemsPanel());
        tabbedPane.add("Order Management", new OrderManagementPanel());
        tabbedPane.add("Order History", new OrderHistoryPanel());
        tabbedPane.add("Customer Reviews", new CustomerReviewsPanel());
        tabbedPane.add("Revenue Dashboard", new RevenueDashboardPanel());
        add(tabbedPane);
    }

    class ManageItemsPanel extends JPanel {
        private JTextField nameField, descriptionField, priceField;
        private JLabel imageLabel;
        private String imagePath;
        private JTable table;
        private DefaultTableModel tableModel;
        private List<Item> items;
        private int selectedRowIndex = -1; // Track the selected row index

        public ManageItemsPanel() {
            items = FileUtil.loadItems();

            setLayout(new BorderLayout());

            // Form for item details
            JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            formPanel.add(new JLabel("Name:"));
            nameField = new JTextField();
            formPanel.add(nameField);
            formPanel.add(new JLabel("Description:"));
            descriptionField = new JTextField();
            formPanel.add(descriptionField);
            formPanel.add(new JLabel("Price:"));
            priceField = new JTextField();
            formPanel.add(priceField);
            formPanel.add(new JLabel("Image:"));
            imageLabel = new JLabel("No image selected");
            imageLabel.setPreferredSize(new Dimension(100, 100)); // Set size for the image display
            formPanel.add(imageLabel);
            JButton browseButton = new JButton("Browse");
            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        imagePath = selectedFile.getAbsolutePath();
                        try {
                            BufferedImage img = ImageIO.read(selectedFile);
                            ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                            imageLabel.setIcon(imageIcon);
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(null, "Error loading image.");
                        }
                    }
                }
            });
            formPanel.add(browseButton);

            add(formPanel, BorderLayout.NORTH);

            // Table for displaying items
            tableModel = new DefaultTableModel(new String[]{"Name", "Description", "Price", "Image"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table cells non-editable
                }
            };
            table = new JTable(tableModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getSelectionModel().addListSelectionListener(e -> {
                selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex >= 0) {
                    Item selectedItem = items.get(selectedRowIndex);
                    nameField.setText(selectedItem.getName());
                    descriptionField.setText(selectedItem.getDescription());
                    priceField.setText(String.valueOf(selectedItem.getPrice()));
                    try {
                        BufferedImage img = ImageIO.read(new File(selectedItem.getImagePath()));
                        ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(imageIcon);
                    } catch (IOException ioException) {
                        // Handle exception if image cannot be read
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            // Load items into the table
            updateTable();

            // Buttons for CRUD operations
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            // Create Item button
            JButton createButton = new JButton("Create Item");
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price;
                    try {
                        price = Double.parseDouble(priceField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format.");
                        return;
                    }
                    if (name.isEmpty() || description.isEmpty() || imagePath == null) {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields and select an image.");
                        return;
                    }
                    Item item = new Item(name, description, price, imagePath);
                    items.add(item);
                    updateTable();
                    FileUtil.saveItems(items);
                    clearFields();
                }
            });
            buttonPanel.add(createButton);

            // Update Item button
            JButton updateButton = new JButton("Update Item");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedRowIndex >= 0) {
                        Item selectedItem = items.get(selectedRowIndex);
                        double price;
                        try {
                            price = Double.parseDouble(priceField.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid price format.");
                            return;
                        }
                        selectedItem.setPrice(price);
                        updateTable();
                        FileUtil.saveItems(items);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select an item to update.");
                    }
                }
            });
            buttonPanel.add(updateButton);

            // Delete Item button
            JButton deleteButton = new JButton("Delete Item");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedRowIndex >= 0) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            items.remove(selectedRowIndex);
                            updateTable();
                            FileUtil.saveItems(items);
                            clearFields();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select an item to delete.");
                    }
                }
            });
            buttonPanel.add(deleteButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void updateTable() {
            tableModel.setRowCount(0); // Clear existing rows
            for (Item item : items) {
                ImageIcon imageIcon = null;
                try {
                    BufferedImage img = ImageIO.read(new File(item.getImagePath()));
                    imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    // Handle exception if image cannot be read
                }
                tableModel.addRow(new Object[]{item.getName(), item.getDescription(), item.getPrice(), imageIcon});
            }
        }

        private void clearFields() {
            nameField.setText("");
            descriptionField.setText("");
            priceField.setText("");
            imageLabel.setIcon(null); // Clear the image
            imagePath = null;
            selectedRowIndex = -1; // Clear the selected row index
        }
    }

    class OrderManagementPanel extends JPanel {
        private JTable orderTable;
        private DefaultTableModel orderTableModel;

        public OrderManagementPanel() {
            setLayout(new BorderLayout());

            // Table for displaying orders
            orderTableModel = new DefaultTableModel(new String[]{"Order ID", "Customer", "Status", "Amount"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 2; // Only the status column is editable
                }
            };
            orderTable = new JTable(orderTableModel);
            orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(orderTable);
            add(scrollPane, BorderLayout.CENTER);

            // Buttons for order actions
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            // Accept Order button
            JButton acceptOrderButton = new JButton("Accept Order");
            acceptOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = orderTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String orderId = (String) orderTableModel.getValueAt(selectedRow, 0);
                        // Implement order acceptance logic
                        JOptionPane.showMessageDialog(null, "Order " + orderId + " accepted.");
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
                        String orderId = (String) orderTableModel.getValueAt(selectedRow, 0);
                        // Implement order cancellation logic
                        JOptionPane.showMessageDialog(null, "Order " + orderId + " canceled.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select an order to cancel.");
                    }
                }
            });
            buttonPanel.add(cancelOrderButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        // Method to add sample orders (for demonstration purposes)
        private void loadSampleOrders() {
            // Sample data
            orderTableModel.addRow(new Object[]{"1", "Alice", "Pending", "$25.00"});
            orderTableModel.addRow(new Object[]{"2", "Bob", "Accepted", "$30.00"});
        }
    }

    class OrderHistoryPanel extends JPanel {
        public OrderHistoryPanel() {
            setLayout(new BorderLayout());

            // Table for displaying order history
            String[] columnNames = {"Order ID", "Customer", "Date", "Total Amount"};
            Object[][] data = {}; // Placeholder for order history data

            JTable historyTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(historyTable);
            add(scrollPane, BorderLayout.CENTER);
        }
    }

    class CustomerReviewsPanel extends JPanel {
        public CustomerReviewsPanel() {
            setLayout(new BorderLayout());

            // Table for displaying customer reviews
            String[] columnNames = {"Customer", "Review", "Rating"};
            Object[][] data = {}; // Placeholder for customer reviews data

            JTable reviewsTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(reviewsTable);
            add(scrollPane, BorderLayout.CENTER);
        }
    }

    class RevenueDashboardPanel extends JPanel {
        public RevenueDashboardPanel() {
            setLayout(new BorderLayout());

            // Sample revenue data
            String[] columnNames = {"Month", "Revenue"};
            Object[][] data = {
                {"January", "$1000"},
                {"February", "$1500"},
                {"March", "$1200"},
                {"April", "$1300"}
            };

            JTable revenueTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(revenueTable);
            add(scrollPane, BorderLayout.CENTER);
        }
    }

    // Dummy Item class for demonstration
    class Item {
        private String name;
        private String description;
        private double price;
        private String imagePath;

        public Item(String name, String description, double price, String imagePath) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imagePath = imagePath;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getImagePath() { return imagePath; }
        public void setPrice(double price) { this.price = price; }
    }

    // Utility class for file operations
    static class FileUtil {
        private static final String FILE_PATH = "items.dat";

        public static List<Item> loadItems() {
            // Load items from file (dummy implementation)
            return new ArrayList<>();
        }

        public static void saveItems(List<Item> items) {
            // Save items to file (dummy implementation)
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VendorPanel().setVisible(true);
        });
    }
}
