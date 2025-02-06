package com.example.newmoney;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CreateAccount {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField(20);
        JButton createAccountButton = new JButton("Create Account");

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordField);
        frame.add(phoneNumberLabel);
        frame.add(phoneNumberField);
        frame.add(createAccountButton);

        createAccountButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String phoneNumber = phoneNumberField.getText();
            if (password.equals(confirmPassword)) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newmoney", "root", "");
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, phone_number) VALUES (?, ?, ?)");
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setString(3, phoneNumber);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error creating account");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
