package com.jnu.booklibrary.data;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private int coverResourceId;
    private String author;
    private String press;
    private String year;
    private String rank;
    private String isbn;

    public Book(String name, int id, String author, String press, String year, String rank, String isbn) {

        this.title = name;
        this.coverResourceId = id;
        this.author = author;
        this.press = press;
        this.year = year;
        this.rank = rank;
        this.isbn = isbn;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setCoverResourceId(int coverResourceId) {

        this.coverResourceId = coverResourceId;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public void setPress(String press) {

        this.press = press;
    }

    public void setYear(String year) {

        this.year = year;
    }

    public void setRank(String rank) {

        this.rank = rank;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public String getTitle() {

        return title;
    }

    public int getCoverResourceId() {

        return coverResourceId;
    }

    public String getAuthor() {

        return author;
    }

    public String getPress() {

        return press;
    }

    public String getYear() {

        return year;
    }

    public String getRank() {

        return rank;
    }

    public String getIsbn() {

        return isbn;
    }
}
