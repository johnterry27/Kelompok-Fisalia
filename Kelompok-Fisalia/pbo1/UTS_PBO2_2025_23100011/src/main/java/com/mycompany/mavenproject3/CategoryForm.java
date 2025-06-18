package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CategoryForm extends JFrame {
    private JTextField idField, nameField, descField;
    private JTable table;
    private DefaultTableModel model;
    private Mavenproject3 mainApp;

    public CategoryForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        setTitle("Kelola Kategori");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel Form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(20);
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Kategori"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Nama"), gbc);
        gbc.gridx = 1;
        descField = new JTextField(20);
        panel.add(descField, gbc);

        // Tombol
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 3;
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tambah");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Hapus");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        panel.add(btnPanel, gbc);

        // Tabel
        model = new DefaultTableModel(new Object[]{"ID", "Kategori", "Nama"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Event tombol (sementara untuk percobaan)
        addBtn.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String desc = descField.getText();
            model.addRow(new Object[]{id, name, desc});
            if (mainApp != null) mainApp.addCategory(name); // sinkron ke main app
        });

        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                String category = model.getValueAt(selected, 1).toString();
                model.removeRow(selected);
                if (mainApp != null) mainApp.removeCategory(category);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                descField.setText(model.getValueAt(row, 2).toString());
            }
        });

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
