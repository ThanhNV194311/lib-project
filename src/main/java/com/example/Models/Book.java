package com.example.Models;


public class Book {
    private int id;
    private String name;
    private int amount;
    private String createDay;

    private String author;
    private String category;

    public Book(int id, String name, String author, String category, String createDay, int amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.createDay = createDay;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    


}
