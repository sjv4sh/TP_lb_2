package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")

public class Book {
    private String title; // Название
    private String author; // Автор
    private String rday; // Дата выхода
    private String dpopular; // Степень популяризации
    private String typeofissue; // Вид выпуска
    public Integer id = null; //идентификатор

    public Book() {};

    public Book (String title, String author, String rday, String dpopular, String typeofissue) {
        this.setTitle(title);
        this.setAuthor(author);
        this.setRday(rday);
        this.setDpopular(dpopular);
        this.setTypeofissue(typeofissue);
    }

    @Override
    public String toString() {
        return  String.format(this.getTitle(), this.getAuthor(), this.getRday(), this.getDpopular(), this.getTypeofissue());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRday() {
        return rday;
    }

    public void setRday(String rday) {
        this.rday = rday;
    }

    public String getDpopular() {
        return dpopular;
    }

    public void setDpopular(String dpopular) {
        this.dpopular = dpopular;
    }

    public String getTypeofissue() {
        return typeofissue;
    }

    public void setTypeofissue(String typeofissue) {
        this.typeofissue = typeofissue;
    }

    public String getDescription() {
        return "";
    }
}
