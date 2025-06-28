package com.mycompany.mavenproject3;

public class Customer {
    private static int idCounter = 1; // ID awal
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    // Constructor dengan ID otomatis
    public Customer(String name, String phone, String email, String address) {
        this.id = idCounter++; // Auto increment
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters
    public int getId() { return id; }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
