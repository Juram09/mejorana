package models;

import java.util.Date;

public class Document {
    private String id;
    private Date date;
    private Date dateOut;
    private String name;
    private String img;
    private String description;
    private boolean active;

    public Document(String id, Date date, Date dateOut,String name, String img, String description, boolean active) {
        this.id = id;
        this.date = date;
        this.dateOut=dateOut;
        this.name = name;
        this.img = img;
        this.description=description;
        this.active=active;
    }

    public Document(Document document) {
        this.id = document.getId();
        this.date = document.getDate();
        this.dateOut=document.getDateOut();
        this.name = document.getName();
        this.img = document.getImg();
        this.description=document.getDescription();
        this.active=document.isActive();
    }

    public Document() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
