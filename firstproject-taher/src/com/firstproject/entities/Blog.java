package com.firstproject.entities;

import java.util.Date;

public class Blog {

    private int id;
    private String description;
    private Date dateDePublication;
    private String image;
    private String categorie;

    public Blog() {
    }

    public Blog(int id, String description, Date dateDePublication, String image, String categorie) {
        this.id = id;
        this.description = description;
        this.dateDePublication = dateDePublication;
        this.image = image;
        this.categorie = categorie;
    }

    public Blog(String description, Date dateDePublication, String image, String categorie) {
        this.description = description;
        this.dateDePublication = dateDePublication;
        this.image = image;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDePublication() {
        return dateDePublication;
    }

    public void setDateDePublication(Date dateDePublication) {
        this.dateDePublication = dateDePublication;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    @Override
    public String toString() {
        return "Blog : " +
                "id=" + id
                + ", Description=" + description
                + ", DateDePublication=" + dateDePublication
                + ", Image=" + image
                + ", Categorie=" + categorie
                ;
    }


}