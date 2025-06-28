package com.mycompany.mavenproject3;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class MembershipForm extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton registerButton;


    private List<Customer> customerList;


    public MembershipForm() {
        setTitle("WK. Cuan | Form Membership");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;


        // Label dan TextField Nama
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nama:"), gbc);


        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);


        // Nomor Telepon
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nomor Telepon:"), gbc);


        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);


        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);


        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);


        // Alamat
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Alamat:"), gbc);


        gbc.gridx = 1;
        addressField = new JTextField(20);
        add(addressField, gbc);


        // Tombol Daftar
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Daftar");
        add(registerButton, gbc);


        registerButton.addActionListener(this::handleRegister);
    }


    private void handleRegister(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();


        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
        } else {
            JOptionPane.showMessageDialog(this, "Pendaftaran berhasil!");
            clearFields();
        }
    }


    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MembershipForm().setVisible(true));
    }
}
