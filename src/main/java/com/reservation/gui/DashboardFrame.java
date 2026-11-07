package com.reservation.gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JButton bookButton;
    private JButton viewButton;
    private JButton cancelButton;
    private JButton logoutButton;

    private int userId;

    public DashboardFrame(String username, int userId) {

        this.userId = userId;

        // Window settings
        setTitle("Online Reservation System - Dashboard");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));

        mainPanel.setBackground(new Color(240, 245, 250));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Welcome heading
        JLabel welcomeLabel = new JLabel(
            "Welcome, " + username + "!",
            SwingConstants.CENTER
        );

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(30, 60, 100));

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(
            new GridLayout(4, 1, 10, 15)
        );

        buttonPanel.setBackground(new Color(240, 245, 250));

        // Book Ticket button
        bookButton = new JButton("Book Ticket");

        // View Reservation button
        viewButton = new JButton("View Reservation");

        // Cancel Ticket button
        cancelButton = new JButton("Cancel Ticket");

        // Logout button
        logoutButton = new JButton("Logout");

        // Common button styling
        styleButton(bookButton, new Color(40, 150, 100));
        styleButton(viewButton, new Color(30, 100, 180));
        styleButton(cancelButton, new Color(200, 80, 70));
        styleButton(logoutButton, new Color(100, 100, 100));

        // Add buttons
        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to JFrame
        add(mainPanel);

        // Book Ticket action
        bookButton.addActionListener(e -> {

            BookTicketFrame bookingFrame =
                    new BookTicketFrame(userId);

            bookingFrame.setVisible(true);
        });

        // View Reservation action
        viewButton.addActionListener(e -> {

            ViewAllReservationsFrame viewFrame =
                    new ViewAllReservationsFrame(userId);

            viewFrame.setVisible(true);
        });

        // Cancel Ticket action
        cancelButton.addActionListener(e -> {

            CancelReservationFrame cancelFrame =
                    new CancelReservationFrame(userId);

            cancelFrame.setVisible(true);
        });

        // Logout action
        logoutButton.addActionListener(e -> logout());
    }

    // Common button styling method
    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFont(new Font("Arial", Font.BOLD, 15));

        button.setFocusPainted(false);

        button.setPreferredSize(new Dimension(200, 40));
    }

    // Logout method
    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            dispose();

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        }
    }
}