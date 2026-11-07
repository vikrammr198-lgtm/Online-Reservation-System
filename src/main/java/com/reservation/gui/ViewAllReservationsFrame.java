package com.reservation.gui;

import com.reservation.model.Reservation;
import com.reservation.service.ReservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
        setSize(1050, 450);
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

        // Enable individual cell selection
        reservationTable.setCellSelectionEnabled(true);

        reservationTable.setRowSelectionAllowed(true);
        reservationTable.setColumnSelectionAllowed(true);

        reservationTable.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        reservationTable.getColumnModel().getSelectionModel()
                .setSelectionMode(
                        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
                );

        // Row selection styling
        reservationTable.setSelectionBackground(
                new Color(200, 220, 245)
        );

        reservationTable.setSelectionForeground(Color.BLACK);

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
        reservationTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        reservationTable.getColumnModel().getColumn(0)
                .setPreferredWidth(160);

        reservationTable.getColumnModel().getColumn(1)
                .setPreferredWidth(180);

        reservationTable.getColumnModel().getColumn(2)
                .setPreferredWidth(130);

        reservationTable.getColumnModel().getColumn(3)
                .setPreferredWidth(90);

        reservationTable.getColumnModel().getColumn(4)
                .setPreferredWidth(140);

        reservationTable.getColumnModel().getColumn(5)
                .setPreferredWidth(130);

        reservationTable.getColumnModel().getColumn(6)
                .setPreferredWidth(130);

        // Table header styling
        reservationTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        reservationTable.getTableHeader().setBackground(
                new Color(30, 100, 180)
        );

        reservationTable.getTableHeader().setForeground(Color.WHITE);

        // Enable Ctrl + C copying
        enableCopyFunction();

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

    // Enable copying selected table cells using Ctrl + C
    private void enableCopyFunction() {

        KeyStroke copyKey = KeyStroke.getKeyStroke(
                KeyEvent.VK_C,
                InputEvent.CTRL_DOWN_MASK
        );

        reservationTable.getInputMap(
                JComponent.WHEN_FOCUSED
        ).put(copyKey, "copySelectedCells");

        reservationTable.getActionMap().put(
                "copySelectedCells",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        copySelectedCells();
                    }
                }
        );
    }

    // Copy selected cells to clipboard
    private void copySelectedCells() {

        int[] selectedRows = reservationTable.getSelectedRows();

        int[] selectedColumns =
                reservationTable.getSelectedColumns();

        if (selectedRows.length == 0
                || selectedColumns.length == 0) {

            return;
        }

        StringBuilder copiedData = new StringBuilder();

        for (int rowIndex = 0;
             rowIndex < selectedRows.length;
             rowIndex++) {

            int row = selectedRows[rowIndex];

            for (int columnIndex = 0;
                 columnIndex < selectedColumns.length;
                 columnIndex++) {

                int column = selectedColumns[columnIndex];

                if (reservationTable.isCellSelected(row, column)) {

                    Object value =
                            reservationTable.getValueAt(row, column);

                    if (value != null) {
                        copiedData.append(value);
                    }
                }

                if (columnIndex < selectedColumns.length - 1) {
                    copiedData.append("\t");
                }
            }

            if (rowIndex < selectedRows.length - 1) {
                copiedData.append("\n");
            }
        }

        if (!copiedData.isEmpty()) {

            StringSelection selection =
                    new StringSelection(copiedData.toString());

            Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .setContents(selection, null);
        }
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