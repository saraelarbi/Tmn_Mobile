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
    private int idReclamation;
    private int idU;
    private String description;
    private int idPub;
    private int idPod;
    private int idBlog;
    private String etat;

    public Reclamation() {
    }

    public Reclamation(int idReclamation, int idU, String description, int idPub, int idPod, int idBlog, String etat) {
        this.idReclamation = idReclamation;
        this.idU = idU;
        this.description = description;
        this.idPub = idPub;
        this.idPod = idPod;
        this.idBlog = idBlog;
        this.etat = etat;
    }

    public Reclamation(int idU, String description, int idPub, int idPod, int idBlog, String etat) {
        this.idU = idU;
        this.description = description;
        this.idPub = idPub;
        this.idPod = idPod;
        this.idBlog = idBlog;
        this.etat = etat;
    }

    public Reclamation(int idReclamation, int idU, String description, int idPub, String etat) {
        this.idReclamation = idReclamation;
        this.idU = idU;
        this.description = description;
        this.idPub = idPub;
        this.etat = etat;
    }

    public Reclamation(int idU, String description, int idPub, String etat) {
        this.idU = idU;
        this.description = description;
        this.idPub = idPub;
        this.etat = etat;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
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
        return "Reclamation{" + "idReclamation=" + idReclamation + ", idU=" + idU + ", description=" + description + ", idPub=" + idPub + ", idPod=" + idPod + ", idBlog=" + idBlog + ", etat=" + etat + '}';
    }
    
    
}
