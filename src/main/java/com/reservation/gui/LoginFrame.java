package com.reservation.gui;

import javax.swing.*;
import java.awt.*;

import com.reservation.dao.UserDAO;

public class LoginFrame extends JFrame {

    // Declare GUI components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private UserDAO userDAO = new UserDAO();

    // Constructor
    public LoginFrame() {

        // Window settings
        setTitle("Online Reservation System");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create panel with GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBackground(new Color(240, 245, 250));
        panel.setBorder(
        BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        // Username components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(180, 30));

        // Password components
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(180, 30));

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 100, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(40, 150, 100));
        registerButton.setForeground(Color.WHITE);  
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(new JLabel(""));
        panel.add(buttonPanel);

        JLabel heading = new JLabel("ONLINE RESERVATION SYSTEM");

        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setForeground(new Color(30, 60, 100));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);
        // Add panel to JFrame
        add(mainPanel);

        // Button click event
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
    RegistrationFrame registrationFrame = new RegistrationFrame();
    registrationFrame.setVisible(true);
    dispose();
});
    }

    // Login method
    private void login() {

    String username = usernameField.getText();

    String password = new String(
        passwordField.getPassword()
    );

    if (username.isEmpty() || password.isEmpty()) {

        JOptionPane.showMessageDialog(
            this,
            "Please enter username and password!"
        );

        return;
    }

    boolean isValid = userDAO.validateLogin(username, password);

    if (isValid) {

    int userId = userDAO.getUserId(username);

    if (userId == -1) {

        JOptionPane.showMessageDialog(
            this,
            "Unable to retrieve user information!"
        );

        return;
    }

    JOptionPane.showMessageDialog(
        this,
        "Login Successful!"
    );

    dispose();

    DashboardFrame dashboard =
            new DashboardFrame(username, userId);

    dashboard.setVisible(true);
}
 else {

        JOptionPane.showMessageDialog(
            this,
            "Invalid Username or Password!"
        );
    }
}
}