package com.sofkau.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class Resource {
    @Id
    private String id;
    private String title;
    private String description;
    private String type;
    private String theme;
    private LocalDate lastBorrowingDate;
    private Boolean isAvailable;


    public Resource() {
        this.lastBorrowingDate = LocalDate.now();
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDate getLastBorrowingDate() {
        return lastBorrowingDate;
    }

    public void setLastBorrowingDate(LocalDate lastBorrowingDate) {
        this.lastBorrowingDate = lastBorrowingDate;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
