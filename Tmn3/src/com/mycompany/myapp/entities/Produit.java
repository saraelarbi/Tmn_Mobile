/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author 21693
 */
public class Produit {
    private int idProduit,stock;
    private String nom,type,image;
    private float prix;

    public Produit() {
    }

    
    public Produit(int idProduit, int stock, String nom, String type, String image, float prix) {
        this.idProduit = idProduit;
        this.stock = stock;
        this.nom = nom;
        this.type = type;
        this.image = image;
        this.prix = prix;
    }

    public Produit(int stock, String nom, String type, String image, float prix) {
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
