package com.example.Models;

import java.sql.Timestamp;
public class Reader {
    private int id;
    private String name;
    private int bookId;
    private String identityCard;
    private Timestamp startDay;
    private Timestamp endDay;
    private boolean status;

    public Reader(int id, String name, int bookId, String identityCard, Timestamp startDay, Timestamp endDay, boolean status) {
        this.id = id;
        this.name = name;
        this.bookId = bookId;
        this.identityCard = identityCard;
        this.startDay = startDay;
        this.endDay = endDay;
        this.status = status;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Timestamp getStartDay() {
        return startDay;
    }

    public void setStartDay(Timestamp startDay) {
        this.startDay = startDay;
    }

    public Timestamp getEndDay() {
        return endDay;
    }

    public void setEndDay(Timestamp endDay) {
        this.endDay = endDay;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
