package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewMenuPanel extends JPanel {
    private JTable menuTable;
    private DefaultTableModel menuTableModel;

    public ViewMenuPanel() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#FFBF00")); // Background color
        JLabel headerLabel = new JLabel("Menu Items");
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
                String[] parts = line.split("\\|");
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
}
