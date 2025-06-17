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
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private Mavenproject3 mainApp;
    private List<Product> products;

    public ProductForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.products = mainApp.getProductList(); 

        setTitle("WK. Cuan | Stok Barang");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === TABEL ===
        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData(products);
        JScrollPane scrollPane = new JScrollPane(drinkTable);
        add(scrollPane, BorderLayout.CENTER); // Tabel di tengah

        // === FORM INPUT ===
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Kode:"));
        codeField = new JTextField(7);
        inputPanel.add(codeField);

        inputPanel.add(new JLabel("Nama:"));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("Harga:"));
        priceField = new JTextField(7);
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Stok:"));
        stockField = new JTextField(5);
        inputPanel.add(stockField);

        // === TOMBOL-TOMBOL ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Simpan");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Gabung form dan tombol ke bawah
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);

        // === ACTION LISTENERS ===
        saveButton.addActionListener(e -> {
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();

            if (code.isEmpty() || name.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                tableModel.addRow(new Object[]{code, name, category, price, stock});
                products.add(new Product(0, code, name, category, price, stock));
                mainApp.setBannerText(getAllProductNames());

                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                String code = codeField.getText();
                String name = nameField.getText();
                String category = (String) categoryField.getSelectedItem();
                String priceText = priceField.getText();
                String stockText = stockField.getText();

                try {
                    double price = Double.parseDouble(priceText);
                    int stock = Integer.parseInt(stockText);

                    tableModel.setValueAt(code, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(category, selectedRow, 2);
                    tableModel.setValueAt(price, selectedRow, 3);
                    tableModel.setValueAt(stock, selectedRow, 4);

                    mainApp.setBannerText(getAllProductNames());
                    clearFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                products.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                mainApp.setBannerText(getAllProductNames());
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            }
        });

        drinkTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                codeField.setText(drinkTable.getValueAt(selectedRow, 0).toString());
                nameField.setText(drinkTable.getValueAt(selectedRow, 1).toString());
                categoryField.setSelectedItem(drinkTable.getValueAt(selectedRow, 2).toString());
                priceField.setText(drinkTable.getValueAt(selectedRow, 3).toString());
                stockField.setText(drinkTable.getValueAt(selectedRow, 4).toString());
            }
        });
    }

    private String getAllProductNames() {
        StringBuilder sb = new StringBuilder("Menu yang tersedia: ");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            sb.append(tableModel.getValueAt(i, 1)); // Nama Produk
            if (i < tableModel.getRowCount() - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    private void clearFields() {
        codeField.setText("");
        nameField.setText("");
        categoryField.setSelectedIndex(0);
        priceField.setText("");
        stockField.setText("");
    }

    private void loadProductData(List<Product> productList) {
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
        }
    }
}
