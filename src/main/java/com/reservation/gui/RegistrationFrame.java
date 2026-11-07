package com.reservation.gui;

import com.reservation.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;
    private JButton backButton;

    private UserService userService = new UserService();

    public RegistrationFrame() {

        // Window settings
        setTitle("User Registration");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBackground(new Color(240, 245, 250));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Registration heading
        JLabel heading = new JLabel("CREATE YOUR ACCOUNT");

        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setForeground(new Color(30, 60, 100));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Registration form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));

        formPanel.setBackground(new Color(240, 245, 250));

        formPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(180, 30));

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(180, 30));

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setPreferredSize(new Dimension(180, 30));

        // Add components to form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        registerButton = new JButton("Register");

        registerButton.setBackground(new Color(40, 150, 100));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        backButton = new JButton("Back to Login");

        backButton.setBackground(new Color(30, 100, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Button panel
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 0)
        );

        buttonPanel.setBackground(new Color(240, 245, 250));

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to JFrame
        add(mainPanel);

        // Register button event
        registerButton.addActionListener(e -> registerUser());

        // Back button event
        backButton.addActionListener(e -> {
            dispose();

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    // Registration method
    private void registerUser() {

        String username = usernameField.getText();

        String password = new String(passwordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        String result = userService.registerUser(
                username,
                password,
                confirmPassword
        );

        if (result.equals("Registration successful!")) {

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}