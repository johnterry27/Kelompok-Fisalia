package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JButton addSellButton;
    private JButton addCustomerButton;
    private JButton kelolaKategoriButton;
    private JButton laporanPenjualanButton;
    private List<Product> productList = new ArrayList<>();

    public Mavenproject3() {
        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tambahkan beberapa produk untuk banner
        productList.add(new Product(1, "P001", "Americano", "Coffee", 10000, 10));
        productList.add(new Product(2, "P002", "Pandan Latte", "Coffee", 20000, 10));
        productList.add(new Product(3, "P003", "Aren Latte", "Coffee", 15000, 10));
        productList.add(new Product(4, "P004", "Matcha Frappucino", "Tea", 28000, 10));
        productList.add(new Product(5, "P005", "Jus Apel", "Juice", 17000, 10));

        this.text = getBannerTextFromProducts();
        this.x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Panel tombol bawah
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        addSellButton = new JButton("Penjualan");
        addCustomerButton = new JButton("Kelola Customer");
        kelolaKategoriButton = new JButton("Kelola Kategori");
        laporanPenjualanButton = new JButton("Laporan Penjualan");

        bottomPanel.add(addProductButton);
        bottomPanel.add(addSellButton);
        bottomPanel.add(addCustomerButton);
        bottomPanel.add(kelolaKategoriButton);
        bottomPanel.add(laporanPenjualanButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Aksi tombol
        addProductButton.addActionListener(e -> new ProductForm(this).setVisible(true));
        addSellButton.addActionListener(e -> new SellingForm(this).setVisible(true));
        addCustomerButton.addActionListener(e -> new CustomerForm().setVisible(true));
        kelolaKategoriButton.addActionListener(e -> new CategoryForm().setVisible(true));
        laporanPenjualanButton.addActionListener(e -> new SalesReport(this).setVisible(true));

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    // Panel banner berjalan
    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    public void setBannerText(String newText) {
        this.text = newText;
        this.x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
    }

    public String getBannerTextFromProducts() {
        StringBuilder sb = new StringBuilder("Menu yang tersedia: ");
        for (int i = 0; i < productList.size(); i++) {
            sb.append(productList.get(i).getName());
            if (i < productList.size() - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public void refreshBanner() {
        setBannerText(getBannerTextFromProducts());
    }

    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Mavenproject3();
    }
}
