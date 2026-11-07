package com.reservation.service;

import com.reservation.dao.UserDAO;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public String registerUser(
            String username,
            String password,
            String confirmPassword) {

        // Check empty fields
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {

            return "All fields are required!";
        }

        // Check username length
        if (username.trim().length() < 3) {
            return "Username must contain at least 3 characters!";
        }

        // Check password length
        if (password.length() < 6) {
            return "Password must contain at least 6 characters!";
        }

        // Check password confirmation
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }

        // Check whether username already exists
        if (userDAO.isUsernameExists(username.trim())) {
            return "Username already exists!";
        }

        // Register the user
        boolean registered = userDAO.registerUser(
                username.trim(),
                password
        );

        if (registered) {
            return "Registration successful!";
        }

        return "Registration failed. Please try again!";
    }
}