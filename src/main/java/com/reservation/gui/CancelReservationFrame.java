package com.reservation.gui;

import com.reservation.service.ReservationService;

import javax.swing.*;
import java.awt.*;

public class CancelReservationFrame extends JFrame {

    private JTextField pnrField;
    private JButton cancelButton;
    private JButton backButton;

    private int userId;

    private ReservationService reservationService =
            new ReservationService();

    public CancelReservationFrame(int userId) {

        this.userId = userId;

        // Window settings
        setTitle("Cancel Reservation");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));

        mainPanel.setBackground(new Color(240, 245, 250));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // Heading
        JLabel heading = new JLabel(
                "CANCEL RESERVATION",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(30, 60, 100));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 15));

        formPanel.setBackground(new Color(240, 245, 250));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 20, 25, 20)
        );

        // PNR label
        JLabel pnrLabel = new JLabel("Enter PNR Number:");

        pnrLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // PNR input field
        pnrField = new JTextField();

        pnrField.setFont(new Font("Arial", Font.PLAIN, 14));

        pnrField.setPreferredSize(new Dimension(180, 30));

        formPanel.add(pnrLabel);
        formPanel.add(pnrField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        cancelButton = new JButton("Cancel Ticket");

        backButton = new JButton("Back");

        styleButton(cancelButton, new Color(200, 80, 70));

        styleButton(backButton, new Color(30, 100, 180));

        // Button panel
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        buttonPanel.setBackground(new Color(240, 245, 250));

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel
        add(mainPanel);

        // Button actions
        cancelButton.addActionListener(e -> cancelTicket());

        backButton.addActionListener(e -> dispose());
    }

    // Common button styling
    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Cancel reservation method
    private void cancelTicket() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR number!"
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean cancelled =
                reservationService.cancelReservation(pnr, userId);

        if (cancelled) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation cancelled successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            pnrField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found or you are not authorized to cancel it!",
                    "Cancellation Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}