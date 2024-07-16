package com.example.bank.model;

import java.time.LocalDate;

/**
 * The Report class represents a financial report generated for a specified period.
 */
public class Report {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;

    // Constructors, getters, and setters
    public Report() {}

    public Report(String title, LocalDate startDate, LocalDate endDate, String content) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
