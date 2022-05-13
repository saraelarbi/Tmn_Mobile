package com.firstproject.entities;

import java.util.Date;

public class Produit {
    private int idProduit,stock;
    private String nom,type,image;
    private float prix;

    public Produit() {
    }

    
    public Produit(int idProduit,  String nom, String type, String image, float prix,int stock) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.prix = prix;
        this.stock = stock;

    }

    public Produit( String nom, String type, String image, float prix,int stock) {
        this.stock = stock;
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.prix = prix;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "idProduit=" + idProduit + ", stock=" + stock + ", nom=" + nom + ", type=" + type + ", image=" + image + ", prix=" + prix + '}';
    }

   

    
}
