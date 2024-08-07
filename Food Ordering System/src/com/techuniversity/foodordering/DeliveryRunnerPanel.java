package com.techuniversity.foodordering;

import javax.swing.*;

public class DeliveryRunnerPanel extends JFrame {
    public DeliveryRunnerPanel() {
        setTitle("Delivery Runner Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome Delivery Runner!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
