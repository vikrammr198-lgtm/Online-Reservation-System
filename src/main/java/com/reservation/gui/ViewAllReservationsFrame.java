package com.reservation.gui;

import com.reservation.model.Reservation;
import com.reservation.service.ReservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ViewAllReservationsFrame extends JFrame {

    private JTable reservationTable;
    private DefaultTableModel tableModel;

    private int userId;

    private ReservationService reservationService =
            new ReservationService();

    public ViewAllReservationsFrame(int userId) {

        this.userId = userId;

        // Window settings
        setTitle("My Reservations");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));

        mainPanel.setBackground(new Color(240, 245, 250));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Heading
        JLabel heading = new JLabel(
            "MY RESERVATIONS",
            SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(30, 60, 100));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Table columns
        String[] columns = {
            "PNR",
            "Passenger Name",
            "Train Number",
            "Class",
            "Journey Date",
            "Source",
            "Destination"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Reservation table
        reservationTable = new JTable(tableModel);

        reservationTable.setAutoCreateRowSorter(true);
        reservationTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reservationTable.setRowHeight(30);

        // Row selection styling
        reservationTable.setSelectionBackground(
            new Color(200, 220, 245)
        );

        reservationTable.setSelectionForeground(Color.BLACK);

        reservationTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        // Center-align table content
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < reservationTable.getColumnCount(); i++) {

            reservationTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // Adjust column widths
        reservationTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        reservationTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        reservationTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        reservationTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        reservationTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        reservationTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        reservationTable.getColumnModel().getColumn(6).setPreferredWidth(120);

        // Table header styling
        reservationTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 14)
        );

        reservationTable.getTableHeader().setBackground(
            new Color(30, 100, 180)
        );

        reservationTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(reservationTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        styleButton(refreshButton, new Color(40, 150, 100));
        styleButton(backButton, new Color(30, 100, 180));

        // Button panel
        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        bottomPanel.setBackground(new Color(240, 245, 250));

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add main panel
        add(mainPanel);

        // Button actions
        backButton.addActionListener(e -> dispose());

        refreshButton.addActionListener(e -> loadReservations());

        loadReservations();
    }

    // Common button styling
    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Load reservations
    private void loadReservations() {

        List<Reservation> reservations =
                reservationService.getReservationsByUserId(userId);

        tableModel.setRowCount(0);

        for (Reservation reservation : reservations) {

            Object[] row = {
                reservation.getPnr(),
                reservation.getPassengerName(),
                reservation.getTrainNumber(),
                reservation.getClassType(),
                reservation.getJourneyDate(),
                reservation.getSourceStation(),
                reservation.getDestinationStation()
            };

            tableModel.addRow(row);
        }

        if (reservations.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "You have no reservations!"
            );
        }
    }
}