package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;

    private List<Customer> customerList;

    public CustomerForm() {
        customerList = new ArrayList<>();

        setTitle("WK. Cuan | Data Pelanggan");
        setSize(750, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // ✅ Supaya form ini bisa ditutup tanpa menutup aplikasi utama
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TABEL
        tableModel = new DefaultTableModel(new String[]{"Nama", "No. HP", "Email", "Alamat"}, 0);
        customerTable = new JTable(tableModel);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);

        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        customerTable.setFillsViewportHeight(true);

        // FORM INPUT
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Nama:"));
        nameField = new JTextField(12);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("No. HP:"));
        phoneField = new JTextField(10);
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField(15);
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Alamat:"));
        addressField = new JTextField(20);
        inputPanel.add(addressField);

        // TOMBOL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Simpan");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");

        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // ACTIONS
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
            tableModel.addRow(new Object[]{name, phone, email, address});
            clearFields();
        });

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

                tableModel.setValueAt(name, row, 0);
                tableModel.setValueAt(phone, row, 1);
                tableModel.setValueAt(email, row, 2);
                tableModel.setValueAt(address, row, 3);

                Customer customer = customerList.get(row);
                customer.setName(name);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setAddress(address);

                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!");
            }
        });

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

        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int row = customerTable.getSelectedRow();
            if (row != -1) {
                nameField.setText(tableModel.getValueAt(row, 0).toString());
                phoneField.setText(tableModel.getValueAt(row, 1).toString());
                emailField.setText(tableModel.getValueAt(row, 2).toString());
                addressField.setText(tableModel.getValueAt(row, 3).toString());
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
