/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ffsga
 */
public class Reclamation {
    private int idreclamation;
    private int idu;
    private String description;
    private int idPub;
    private int idPod;
    private int idBlog;
    private String etat;

    public Reclamation() {
    }

    public Reclamation(int idreclamation, int idu, String description, int idPub, int idPod, int idBlog, String etat) {
        this.idreclamation = idreclamation;
        this.idu = idu;
        this.description = description;
        this.idPub = idPub;
        this.idPod = idPod;
        this.idBlog = idBlog;
        this.etat = etat;
    }

    public Reclamation(int idreclamation, int idu, String description, int idPub, String etat) {
        this.idreclamation = idreclamation;
        this.idu = idu;
        this.description = description;
        this.idPub = idPub;
        this.etat = etat;
    }

    public Reclamation(int idu, String description, int idPub, String etat) {
        this.idu = idu;
        this.description = description;
        this.idPub = idPub;
        this.etat = etat;
    }

    public int getIdreclamation() {
        return idreclamation;
    }

    public void setIdreclamation(int idreclamation) {
        this.idreclamation = idreclamation;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    public int getIdPod() {
        return idPod;
    }

    public void setIdPod(int idPod) {
        this.idPod = idPod;
    }

    public int getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(int idBlog) {
        this.idBlog = idBlog;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "idreclamation=" + idreclamation + ", idu=" + idu + ", description=" + description + ", idPub=" + idPub + ", idPod=" + idPod + ", idBlog=" + idBlog + ", etat=" + etat + '}';
    }

   
    
    
}
