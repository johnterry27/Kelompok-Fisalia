package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class SalesReport extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JDateChooser startDateChooser, endDateChooser;
    private JComboBox<String> kodeProdukCombo;
    private List<Product> products;

    public SalesReport(Mavenproject3 mainApp) {
        this.products = mainApp.getProductList();

        setTitle("WK. Cuan | Laporan Penjualan");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Panel Judul ===
        JLabel lblJudul = new JLabel("LAPORAN PENJUALAN", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // === Panel Filter ===
        JPanel panelFilter = new JPanel(new FlowLayout());
        panelFilter.setBackground(Color.LIGHT_GRAY);

        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();

        kodeProdukCombo = new JComboBox<>();
        kodeProdukCombo.addItem("-- Semua --");
        for (Product p : products) {
            kodeProdukCombo.addItem(p.getCode());  // gunakan getCode untuk kode produk
        }

        JButton btnFilter = new JButton("Filter");

        panelFilter.add(new JLabel("Tanggal Mulai"));
        panelFilter.add(startDateChooser);
        panelFilter.add(new JLabel("Tanggal Selesai"));
        panelFilter.add(endDateChooser);
        panelFilter.add(new JLabel("Kode Produk"));
        panelFilter.add(kodeProdukCombo);
        panelFilter.add(btnFilter);

        // === Panel Atas ===
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(lblJudul, BorderLayout.NORTH);
        panelAtas.add(panelFilter, BorderLayout.CENTER);

        // === Tabel ===
        String[] kolom = {"Tanggal", "Kode Produk", "Nama Produk", "Harga Satuan", "Jumlah", "Total"};
        tableModel = new DefaultTableModel(null, kolom);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Dummy Data Saat Klik Filter
        btnFilter.addActionListener(e -> {
            tableModel.setRowCount(0); // bersihkan isi tabel

        });
    }

    public SalesReport() {
        //TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // Ubah main sesuai dengan inisialisasi yang sesuai
        SwingUtilities.invokeLater(() -> {
            Mavenproject3 app = new Mavenproject3(); // pastikan ada konstruktor dan produk disiapkan
            new SalesReport(app).setVisible(true);
        });
    }
}
