package com.example.newmoney;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Dashboard {
    private String username;

    public Dashboard(String username) {
        this.username = username;
    }

    public void loadSims() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newmoney", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sims");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Loading sim: " + rs.getString("sim_id") + " - " + rs.getString("sim_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading sims");
        }
    }

    public void sendMoney(String recipientNumber, double amount) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newmoney", "root", "");
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
            stmt.setDouble(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("INSERT INTO transactions (username, recipient_number, amount) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, recipientNumber);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Money sent successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error sending money");
        }
    }

    public void addMoney(double amount, String recipientNumber) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newmoney", "root", "");
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
            stmt.setDouble(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("INSERT INTO transactions (username, recipient_number, amount) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, recipientNumber);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Money added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding money");
        }
    }

    public void transferMoneyToBank(double amount) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newmoney", "root", "");
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
            stmt.setDouble(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("INSERT INTO transactions (username, amount) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Money transferred to bank successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error transferring money to bank");
        }
    }

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog(null, "Enter your username");
        (new Dashboard(username)).setVisible(true);
    }
}
