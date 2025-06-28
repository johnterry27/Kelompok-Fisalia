package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton saveButton, editButton, deleteButton;

    private List<Customer> customerList;

    public CustomerForm() {
        customerList = new ArrayList<>();

        setTitle("WK. Cuan | Data Pelanggan");
        setSize(750, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TABEL
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "No. HP", "Email", "Alamat"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setRowHeight(22); // tinggi baris isi
        JTableHeader header = customerTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 24)); // tinggi header
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        customerTable.setFillsViewportHeight(true);

        // FORM INPUT PAKAI GRIDBAGLAYOUT
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3); // jarak antar komponen
        gbc.anchor = GridBagConstraints.EAST;

        // Nama
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // No. HP
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("No. HP:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        phoneField = new JTextField(20);
        formPanel.add(phoneField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Alamat
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        addressField = new JTextField(20);
        formPanel.add(addressField, gbc);

        // PANEL TOMBOL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Simpan");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");

        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Gabungkan input + tombol
        JPanel inputSection = new JPanel(new BorderLayout());
        inputSection.add(formPanel, BorderLayout.CENTER);
        inputSection.add(buttonPanel, BorderLayout.SOUTH);
        add(inputSection, BorderLayout.NORTH);

        // AKSI SIMPAN
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            Customer customer = new Customer(name, phone, email, address);
            customerList.add(customer);
            tableModel.addRow(new Object[]{customer.getId(), name, phone, email, address});
            clearFields();
        });

        // AKSI EDIT
        editButton.addActionListener(e -> {
            int row = customerTable.getSelectedRow();
            if (row != -1) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String address = addressField.getText().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    return;
                }

                Customer customer = customerList.get(row);
                customer.setName(name);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setAddress(address);

                tableModel.setValueAt(name, row, 1);
                tableModel.setValueAt(phone, row, 2);
                tableModel.setValueAt(email, row, 3);
                tableModel.setValueAt(address, row, 4);

                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!");
            }
        });

        // AKSI HAPUS
        deleteButton.addActionListener(e -> {
            int row = customerTable.getSelectedRow();
            if (row != -1) {
                customerList.remove(row);
                tableModel.removeRow(row);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            }
        });

        // SELEKSI TABEL
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int row = customerTable.getSelectedRow();
            if (row != -1) {
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                phoneField.setText(tableModel.getValueAt(row, 2).toString());
                emailField.setText(tableModel.getValueAt(row, 3).toString());
                addressField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}
