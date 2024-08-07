package com.techuniversity.foodordering;

import javax.swing.*;
import java.awt.*;

public class RevenueDashboardPanel extends JPanel {
    public RevenueDashboardPanel() {
        setLayout(new BorderLayout());

        // Placeholder for revenue dashboard
        JTextArea revenueTextArea = new JTextArea();
        revenueTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(revenueTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Dummy data for revenue dashboard
        revenueTextArea.setText("Revenue Dashboard:\n\nTotal Revenue: $5000\n\nToday's Revenue: $200\n\nTotal Orders: 100");
    }
}
