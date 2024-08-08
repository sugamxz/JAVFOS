package com.techuniversity.foodordering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRunnerPanel extends JFrame {

    private DefaultTableModel orderTableModel;
    private List<Order> orders;
    private static final String FILE_PATH = "orders.txt";

    public DeliveryRunnerPanel() {
        setTitle("Delivery Runner Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        orders = new ArrayList<>();
        JTabbedPane tabbedPane = new JTabbedPane();

        // Order Display Panel
        JPanel orderPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Order ID", "Customer", "Date", "Status", "Action"};
        orderTableModel = new DefaultTableModel(columnNames, 0);
        JTable orderTable = new JTable(orderTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        orderTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        orderTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);
        loadOrdersFromFile(FILE_PATH);
        tabbedPane.addTab("Orders", orderPanel);

        // Order History Panel
        OrderHistoryPanel orderHistoryPanel = new OrderHistoryPanel(orders);
        tabbedPane.addTab("Order History", orderHistoryPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private void loadOrdersFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Order order = new Order(parts[0], parts[1], parts[2], parts[3]);
                    orders.add(order);
                    orderTableModel.addRow(new Object[]{order.getOrderId(), order.getCustomer(), order.getDate(), order.getStatus(), "Accept"});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrdersToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Order order : orders) {
                writer.write(order.getOrderId() + "," + order.getCustomer() + "," + order.getDate() + "," + order.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = ((JTable) button.getParent().getParent()).getSelectedRow();
                String orderId = orderTableModel.getValueAt(row, 0).toString();
                Order selectedOrder = orders.stream()
                        .filter(order -> order.getOrderId().equals(orderId))
                        .findFirst()
                        .orElse(null);

                if ("Accept".equals(label)) {
                    if (selectedOrder != null) {
                        selectedOrder.setStatus("Accepted");
                        orderTableModel.setValueAt("Accepted", row, 3);
                    }
                } else {
                    if (selectedOrder != null) {
                        selectedOrder.setStatus("Declined");
                        orderTableModel.setValueAt("Declined", row, 3);
                    }
                }
                saveOrdersToFile(FILE_PATH);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeliveryRunnerPanel());
    }
}
