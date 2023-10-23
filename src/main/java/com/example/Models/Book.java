package com.example.Models;
import java.sql.Timestamp;
public class Book { private int id;
    private String name;
    private int categoryId;
    private int amount;
    private String image;
    private Timestamp createDay;

    public Book(int id, String name, int categoryId, int amount, String image, Timestamp createDay) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.image = image;
        this.createDay = createDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Timestamp createDay) {
        this.createDay = createDay;
    }
}
