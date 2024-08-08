package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class ManageItemsPanel extends JPanel {
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
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Form for item details
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Image:"), gbc);

        gbc.gridx = 1;
        imageLabel = new JLabel("");
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        formPanel.add(imageLabel, gbc);

        JButton browseButton = new JButton("Browse");
        browseButton.setBackground(new Color(100, 150, 255)); // Blue background
        browseButton.setForeground(Color.WHITE); // White text
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
        gbc.gridx = 2;
        formPanel.add(browseButton, gbc);

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
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex >= 0) {
                Item selectedItem = items.get(selectedRowIndex);
                nameField.setText(selectedItem.getName());
                descriptionField.setText(selectedItem.getDescription());
                priceField.setText(String.valueOf(selectedItem.getPrice()));
                imagePath = selectedItem.getImagePath();
                try {
                    BufferedImage img = ImageIO.read(new File(selectedItem.getImagePath()));
                    ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(imageIcon);
                } catch (IOException ioException) {
                    imageLabel.setIcon(null); // Clear the label if image cannot be loaded
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for CRUD operations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Create Item button
        JButton createButton = new JButton("Create Item");
        createButton.setBackground(new Color(34, 139, 34)); // Green background
        createButton.setForeground(Color.WHITE); // White text
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
        JButton updateItemButton = new JButton("Update Item");
        updateItemButton.setBackground(new Color(255, 140, 0)); // Orange background
        updateItemButton.setForeground(Color.WHITE); // White text
        updateItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRowIndex >= 0) {
                    Item selectedItem = items.get(selectedRowIndex);
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price;
                    try {
                        price = Double.parseDouble(priceField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid price format.");
                        return;
                    }
                    selectedItem.setName(name);
                    selectedItem.setDescription(description);
                    selectedItem.setPrice(price);
                    // Only update the image path if a new image is selected
                    if (imagePath != null && !imagePath.equals(selectedItem.getImagePath())) {
                        selectedItem.setImagePath(imagePath);
                    }
                    updateTable();
                    FileUtil.saveItems(items);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to update.");
                }
            }
        });
        buttonPanel.add(updateItemButton);

        // Delete Item button
        JButton deleteButton = new JButton("Delete Item");
        deleteButton.setBackground(new Color(255, 69, 69)); // Red background
        deleteButton.setForeground(Color.WHITE); // White text
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
        tableModel.setRowCount(0);
        for (Item item : items) {
            tableModel.addRow(new Object[]{item.getName(), item.getDescription(), item.getPrice(), item.getImagePath()});
        }
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        imageLabel.setIcon(null);
        imagePath = null;
        selectedRowIndex = -1;
        table.clearSelection();
    }
}

