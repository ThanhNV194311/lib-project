package com.example.Models;

import java.sql.Timestamp;
public class Borrow {
    private int borrowId;
    private int customerId;
    private int bookId;
    private Timestamp startDay;
    private Timestamp endDay;
    private boolean status;



    public Borrow(int borrowId, int customerId, int bookId, Timestamp startDay, Timestamp endDay, boolean status) {
        this.borrowId = borrowId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.startDay = startDay;
        this.endDay = endDay;
        this.status = status;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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
