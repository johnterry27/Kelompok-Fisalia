package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.table.TableColumnModel;

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, phoneField, emailField, addressField;
    private JButton saveButton, editButton, deleteButton;

    private List<Customer> customerList;

    public CustomerForm() {
        customerList = new ArrayList<>();

        setTitle("WK. Cuan | Data Pelanggan");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // === FORM INPUT ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(220, 220, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Nama:", "No. HP:", "Email:", "Alamat:"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(25);
            formPanel.add(fields[i], gbc);
        }

        nameField = fields[0];
        phoneField = fields[1];
        emailField = fields[2];
        addressField = fields[3];

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");

        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // === TABEL ===
        tableModel = new DefaultTableModel(new String[]{"Nama", "No. HP", "Email", "Alamat"}, 0);
        customerTable = new JTable(tableModel);

        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        setColumnWidths(4, 200);

        // === AKSI TOMBOL ===
        saveButton.addActionListener(e -> saveCustomer());
        editButton.addActionListener(e -> editCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());

        customerTable.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void setColumnWidths(int columnCount, int width) {
        TableColumnModel columnModel = customerTable.getColumnModel();
        for (int i = 0; i < columnCount; i++) {
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }

    private void saveCustomer() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "No. HP harus angka!");
            return;
        }

        Customer customer = new Customer(name, phone, email, address);
        customerList.add(customer);
        tableModel.addRow(new Object[]{name, phone, email, address});
        clearFields();
    }

    private void editCustomer() {
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

            if (!phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "No. HP harus angka!");
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
    }

    private void deleteCustomer() {
        int row = customerTable.getSelectedRow();
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                customerList.remove(row);
                tableModel.removeRow(row);
                clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
        }
    }

    private void fillFormFromTable() {
        int row = customerTable.getSelectedRow();
        if (row != -1) {
            nameField.setText(tableModel.getValueAt(row, 0).toString());
            phoneField.setText(tableModel.getValueAt(row, 1).toString());
            emailField.setText(tableModel.getValueAt(row, 2).toString());
            addressField.setText(tableModel.getValueAt(row, 3).toString());
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        customerTable.clearSelection();
    }
}
