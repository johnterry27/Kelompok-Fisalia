package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CategoryForm extends JFrame {
    private JTextField idField, nameField, descField;
    private JButton addButton, editButton, deleteButton;
    private JTable categoryTable;
    private DefaultTableModel tableModel;

    private java.util.List<Category> categories = new ArrayList<>();

    public CategoryForm() {
        setTitle("WK. STI Chill");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID");
        JLabel nameLabel = new JLabel("Nama");
        JLabel descLabel = new JLabel("Deskripsi");
        idField = new JTextField(20);
        nameField = new JTextField(20);
        descField = new JTextField(20);
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(descLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(descField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Nama", "Deskripsi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Event listeners
        addButton.addActionListener(e -> addCategory());
        editButton.addActionListener(e -> editCategory());
        deleteButton.addActionListener(e -> deleteCategory());

        categoryTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                descField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });

        setVisible(true);
    }

    private void addCategory() {
        String id = idField.getText();
        String name = nameField.getText();
        String desc = descField.getText();
        if (!id.isEmpty() && !name.isEmpty()) {
            Category category = new Category(id, name, desc);
            categories.add(category);
            tableModel.addRow(new Object[]{id, name, desc});
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "ID dan Nama harus diisi!");
        }
    }

    private void editCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = idField.getText();
            String name = nameField.getText();
            String desc = descField.getText();
            tableModel.setValueAt(id, selectedRow, 0);
            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(desc, selectedRow, 2);

            categories.get(selectedRow).setId(id);
            categories.get(selectedRow).setName(name);
            categories.get(selectedRow).setDescription(desc);

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
        }
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            categories.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CategoryForm::new);
    }
}
