package com.example.Models;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Borrow {
    private int borrowId;
    private String customerId;
    private String bookId;
    private LocalDate startDay;
    private LocalDate endDay;
    private boolean status;


    public Borrow(int borrowId, String customerId, String bookId, LocalDate startDay, LocalDate endDay, boolean status) {
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
