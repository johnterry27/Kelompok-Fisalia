package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField, nameField, priceField, stockField;
    private JComboBox<String> categoryField;
    private JButton saveButton, editButton, deleteButton;
    private Mavenproject3 mainApp;
    private List<Product> products;

    public ProductForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.products = mainApp.getProductList();

        setTitle("WK. Cuan | Stok Barang");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData(products);
        add(new JScrollPane(drinkTable), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codeField = new JTextField(7);
        nameField = new JTextField(10);
        categoryField = new JComboBox<>(mainApp.getCategoryList().toArray(new String[0]));
        priceField = new JTextField(7);
        stockField = new JTextField(5);

        inputPanel.add(new JLabel("Kode:")); inputPanel.add(codeField);
        inputPanel.add(new JLabel("Nama:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Kategori:")); inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Harga:")); inputPanel.add(priceField);
        inputPanel.add(new JLabel("Stok:")); inputPanel.add(stockField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Simpan");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();

            if (code.isEmpty() || name.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                tableModel.addRow(new Object[]{code, name, category, price, stock});
                products.add(new Product(0, code, name, category, price, stock));
                mainApp.refreshBanner();
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!");
            }
        });

        editButton.addActionListener(e -> {
            int row = drinkTable.getSelectedRow();
            if (row != -1) {
                try {
                    String code = codeField.getText();
                    String name = nameField.getText();
                    String category = (String) categoryField.getSelectedItem();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    tableModel.setValueAt(code, row, 0);
                    tableModel.setValueAt(name, row, 1);
                    tableModel.setValueAt(category, row, 2);
                    tableModel.setValueAt(price, row, 3);
                    tableModel.setValueAt(stock, row, 4);
                    mainApp.refreshBanner();
                    clearFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int row = drinkTable.getSelectedRow();
            if (row != -1) {
                products.remove(row);
                tableModel.removeRow(row);
                mainApp.refreshBanner();
                clearFields();
            }
        });

        drinkTable.getSelectionModel().addListSelectionListener(e -> {
            int row = drinkTable.getSelectedRow();
            if (row != -1) {
                codeField.setText(drinkTable.getValueAt(row, 0).toString());
                nameField.setText(drinkTable.getValueAt(row, 1).toString());
                categoryField.setSelectedItem(drinkTable.getValueAt(row, 2).toString());
                priceField.setText(drinkTable.getValueAt(row, 3).toString());
                stockField.setText(drinkTable.getValueAt(row, 4).toString());
            }
        });
    }

    private void clearFields() {
        codeField.setText("");
        nameField.setText("");
        categoryField.setSelectedIndex(0);
        priceField.setText("");
        stockField.setText("");
    }

    private void loadProductData(List<Product> productList) {
        for (Product p : productList) {
            tableModel.addRow(new Object[]{p.getCode(), p.getName(), p.getCategory(), p.getPrice(), p.getStock()});
        }
    }

    public void updateCategoryCombo() {
        categoryField.removeAllItems();
        for (String cat : mainApp.getCategoryList()) {
            categoryField.addItem(cat);
        }
    }
}
