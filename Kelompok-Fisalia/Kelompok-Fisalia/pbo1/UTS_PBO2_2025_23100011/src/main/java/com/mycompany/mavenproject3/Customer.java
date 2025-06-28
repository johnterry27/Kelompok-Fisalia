package com.mycompany.mavenproject3;

public class Customer {
    private static int counter = 1;  // Untuk auto increment ID
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Customer(String name, String phone, String email, String address) {
        this.id = counter++; // Set ID otomatis
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // ✅ Getter untuk ID
    public int getId() {
        return id;
    }

    // Getter dan Setter untuk data lain
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
