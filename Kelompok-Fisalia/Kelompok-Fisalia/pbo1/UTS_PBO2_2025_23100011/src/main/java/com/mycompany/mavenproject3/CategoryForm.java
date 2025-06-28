package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
        panel.add(new JLabel("Deskripsi"), gbc);
        gbc.gridx = 1;
        descField = new JTextField(20);
        panel.add(descField, gbc);

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

        model = new DefaultTableModel(new Object[]{"ID", "Kategori", "Deskripsi"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        loadCategoryData();

        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();

            if (!name.isEmpty() && !isCategoryExist(name)) {
                model.addRow(new Object[]{id, name, desc});
                if (mainApp != null) {
                    mainApp.addCategory(name);
                    mainApp.updateAllProductFormCategoryCombo(); // Penting
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                String category = model.getValueAt(selected, 1).toString();
                model.removeRow(selected);
                if (mainApp != null) {
                    mainApp.removeCategory(category);
                    mainApp.updateAllProductFormCategoryCombo(); // Penting
                }
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

    private boolean isCategoryExist(String name) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (name.equalsIgnoreCase(model.getValueAt(i, 1).toString())) {
                return true;
            }
        }
        return false;
    }

    private void loadCategoryData() {
        if (mainApp != null) {
            List<String> categories = mainApp.getCategoryList();
            model.setRowCount(0);
            int i = 1;
            for (String cat : categories) {
                model.addRow(new Object[]{"C" + i++, cat, ""});
            }
        }
    }
}
