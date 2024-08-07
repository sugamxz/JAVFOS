package com.techuniversity.foodordering;

import javax.swing.*;

public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Panel"); //ok
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome Administrator!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
