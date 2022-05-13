/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author Abirn
 */
public class User {
    
    private int idU;
    private String nom;
    private String prenom;
    private int numtel;
    private String email;
    private String adresse;
    private String password;
    private Date datenaissance;
   

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int idU, String nom, String prenom,  int numtel, String email,String adresse, String password, Date datenaissance) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
        this.datenaissance = datenaissance;
       
    }

    public User(String nom, String prenom, int numtel, String email,String adresse, String password, Date datenaissance) {
        this.nom = nom;
        this.prenom = prenom;
        
        this.numtel = numtel;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
        this.datenaissance = datenaissance;

    }

  


    public User(int idU, String nom, String prenom,  int numtel, String email,String adresse,  String password) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
       
    }

    public User(String nom, String prenom, int numtel, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.password = password;
    }

    public User(String nom, String prenom, int numtel, String email, String password, Date datenaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.password = password;
        this.datenaissance = datenaissance;
    }



    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

 
    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
     public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }


    @Override
    public String toString() {
        return "User{" + "idU=" + idU + ", nom=" + nom + ", prenom=" + prenom +  ", numtel=" + numtel + ", email=" + email + ", password=" + password + ", datenaissance=" + datenaissance +  '}';
    }

    
    
}
