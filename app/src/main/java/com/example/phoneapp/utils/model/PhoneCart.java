package com.example.phoneapp.utils.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_table")
public class PhoneCart{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String phoneName, phoneBrandName;
    private int phoneImage;
    private double phonePrice;

    private int quantity;
    private double totalItemPrice;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneBrandName() {
        return phoneBrandName;
    }

    public void setPhoneBrandName(String phoneBrandName) {
        this.phoneBrandName = phoneBrandName;
    }

    public int getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(int phoneImage) {
        this.phoneImage = phoneImage;
    }

    public double getPhonePrice() {
        return phonePrice;
    }

    public void setPhonePrice(double phonePrice) {
        this.phonePrice = phonePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }
}