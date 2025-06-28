package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SellingForm extends JFrame {
    private JComboBox<String> productField;
    private JTextField stockField, priceField, qtyField;
    private JSpinner dateSpinner;
    private JButton processButton;
    private List<Product> products;
    private Mavenproject3 mainApp;

    public SellingForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.products = mainApp.getProductList();

        setTitle("WK. Cuan | Jual Barang");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sellPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Produk
        gbc.gridx = 0; gbc.gridy = 0;
        sellPanel.add(new JLabel("Barang:"), gbc);

        productField = new JComboBox<>();
        for (Product p : products) productField.addItem(p.getName());
        gbc.gridx = 1;
        sellPanel.add(productField, gbc);

        // Stok
        gbc.gridx = 0; gbc.gridy = 1;
        sellPanel.add(new JLabel("Stok Tersedia:"), gbc);
        stockField = new JTextField(10); stockField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(stockField, gbc);

        // Harga
        gbc.gridx = 0; gbc.gridy = 2;
        sellPanel.add(new JLabel("Harga Jual:"), gbc);
        priceField = new JTextField(10); priceField.setEditable(false);
        gbc.gridx = 1;
        sellPanel.add(priceField, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 3;
        sellPanel.add(new JLabel("Qty:"), gbc);
        qtyField = new JTextField(10);
        gbc.gridx = 1;
        sellPanel.add(qtyField, gbc);

        // Tanggal
        gbc.gridx = 0; gbc.gridy = 4;
        sellPanel.add(new JLabel("Tanggal:"), gbc);
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy"));
        gbc.gridx = 1;
        sellPanel.add(dateSpinner, gbc);

        // Tombol
        processButton = new JButton("Proses");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        sellPanel.add(processButton, gbc);

        add(sellPanel);

        productField.addActionListener(e -> updateFields());

        processButton.addActionListener(e -> {
            int idx = productField.getSelectedIndex();
            Product selectedProduct = products.get(idx);
            try {
                int qty = Integer.parseInt(qtyField.getText());
                if (qty <= 0 || qty > selectedProduct.getStock()) {
                    JOptionPane.showMessageDialog(this, "Qty tidak valid!");
                    return;
                }
                selectedProduct.setStock(selectedProduct.getStock() - qty);
                Date selectedDate = (Date) dateSpinner.getValue();

                // Simpan ke daftar penjualan
                Sale sale = new Sale(selectedDate, selectedProduct.getCode(),
                        selectedProduct.getName(), selectedProduct.getPrice(), qty);
                mainApp.addSale(sale);

                JOptionPane.showMessageDialog(this, "Transaksi berhasil!");
                updateFields();
                qtyField.setText("");
                mainApp.refreshBanner();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Qty harus angka.");
            }
        });

        if (!products.isEmpty()) {
            productField.setSelectedIndex(0);
            updateFields();
        }
    }

    private void updateFields() {
        int idx = productField.getSelectedIndex();
        if (idx != -1) {
            Product p = products.get(idx);
            stockField.setText(String.valueOf(p.getStock()));
            priceField.setText(String.valueOf(p.getPrice()));
        }
    }
}
