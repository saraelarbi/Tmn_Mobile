/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author bolba
 */
public class Publicite {
    private int id_pub;
    private int id_typepub;
    private Date date_creation;
    private String domaine;
    private String description;
    private String lettre_motivation;
    private String image;
 

  


    public Publicite() {
    }

    public Publicite(int id_pub, Date date_creation, String domaine, String description, String lettre_motivation, int id_typepub, String image) {
        this.id_pub = id_pub;
        this.date_creation = date_creation;
        this.domaine = domaine;
        this.description = description;
        this.lettre_motivation = lettre_motivation;
        this.id_typepub = id_typepub;
        this.image = image;
    }

    public Publicite(Date date_creation, String domaine, String description, String lettre_motivation, int id_typepub, String image) {
        this.date_creation = date_creation;
        this.domaine = domaine;
        this.description = description;
        this.lettre_motivation = lettre_motivation;
        this.id_typepub = id_typepub;
        this.image = image;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLettre_motivation() {
        return lettre_motivation;
    }

    public void setLettre_motivation(String lettre_motivation) {
        this.lettre_motivation = lettre_motivation;
    }

    public int getId_typepub() {
        return id_typepub;
    }

    public void setId_typepub(int id_typepub) {
        this.id_typepub = id_typepub;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Publicite{" + "id_pub=" + id_pub + ", date_creation=" + date_creation + ", domaine=" + domaine + ", description=" + description + ", lettre_motivation=" + lettre_motivation + ", id_typepub=" + id_typepub + ", image=" + image + '}';
    }
    
}
