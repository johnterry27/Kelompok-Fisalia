package com.mycompany.mavenproject3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalesReport extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JDateChooser startDateChooser, endDateChooser;
    private JComboBox<String> kodeProdukCombo;

    public SalesReport(Mavenproject3 mainApp) {
        setTitle("WK. Cuan | Laporan Penjualan");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblJudul = new JLabel("LAPORAN PENJUALAN", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panelFilter = new JPanel(new FlowLayout());
        panelFilter.setBackground(Color.LIGHT_GRAY);
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();

        kodeProdukCombo = new JComboBox<>();
        kodeProdukCombo.addItem("-- Semua --");
        for (Product p : mainApp.getProductList()) {
            kodeProdukCombo.addItem(p.getCode());
        }

        JButton btnFilter = new JButton("Filter");
        panelFilter.add(new JLabel("Tanggal Mulai"));
        panelFilter.add(startDateChooser);
        panelFilter.add(new JLabel("Tanggal Selesai"));
        panelFilter.add(endDateChooser);
        panelFilter.add(new JLabel("Kode Produk"));
        panelFilter.add(kodeProdukCombo);
        panelFilter.add(btnFilter);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(lblJudul, BorderLayout.NORTH);
        panelAtas.add(panelFilter, BorderLayout.CENTER);

        String[] kolom = {"Tanggal", "Kode Produk", "Nama Produk", "Harga Satuan", "Jumlah", "Total"};
        tableModel = new DefaultTableModel(null, kolom);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnFilter.addActionListener(e -> {
            tableModel.setRowCount(0);
            List<Sale> sales = mainApp.getSales();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            for (Sale s : sales) {
                boolean cocok = true;

                Date start = startDateChooser.getDate();
                Date end = endDateChooser.getDate();
                if (start != null && s.getDate().before(start)) cocok = false;
                if (end != null && s.getDate().after(end)) cocok = false;

                String kodeDipilih = (String) kodeProdukCombo.getSelectedItem();
                if (!"-- Semua --".equals(kodeDipilih) && !s.getProductCode().equals(kodeDipilih)) cocok = false;

                if (cocok) {
                    tableModel.addRow(new Object[]{
                        sdf.format(s.getDate()),
                        s.getProductCode(),
                        s.getProductName(),
                        s.getPrice(),
                        s.getQuantity(),
                        s.getTotal()
                    });
                }
            }
        });
    }
}
