package com.mycompany.mycart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int pId;

    @Column(name = "p_name")
    private String pName;

    @Column(name = "p_description", length = 3000)
    private String pDesc;

    @Column(name = "p_photo")
    private String pPhoto;

    @Column(name = "p_price")
    private double pPrice;

    @Column(name = "p_discount")
    private int pDiscount;

    @ManyToOne
    @JoinColumn(name = "category_id") // Specify the foreign key column
    private Category category;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(String pName, String pDesc, String pPhoto, double pPrice, int pDiscount, Category category) {
        this.pName = pName;
        this.pDesc = pDesc;
        this.pPhoto = pPhoto;
        this.pPrice = pPrice;
        this.pDiscount = pDiscount;
        this.category = category;
    }

    // Getters and setters
    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPDesc() {
        return pDesc;
    }

    public void setPDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getPPhoto() {
        return pPhoto;
    }

    public void setPPhoto(String pPhoto) {
        this.pPhoto = pPhoto;
    }

    public double getPPrice() {
        return pPrice;
    }

    public void setPPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public int getPDiscount() {
        return pDiscount;
    }

    public void setPDiscount(int pDiscount) {
        this.pDiscount = pDiscount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString method
    @Override
    public String toString() {
        return "Product{" +
                "pId=" + pId +
                ", pName='" + pName + '\'' +
                ", pDesc='" + pDesc + '\'' +
                ", pPhoto='" + pPhoto + '\'' +
                ", pPrice=" + pPrice +
                ", pDiscount=" + pDiscount +
                ", category=" + category +
                '}';
    }
}
