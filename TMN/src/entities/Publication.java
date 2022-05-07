/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author ffsga
 */
public class Publication {
    private int idPub;
    private Date date_Pub;
    private String titre_Pub;
    private String desc_Pub;
    private String source_Pub;
    private String categorie_Pub;
    private String image_Pub;

    public Publication() {
    }

    public Publication(int idPub, Date date_Pub, String titre_Pub, String desc_Pub, String source_Pub, String categorie_Pub, String image_Pub) {
        this.idPub = idPub;
        this.date_Pub = date_Pub;
        this.titre_Pub = titre_Pub;
        this.desc_Pub = desc_Pub;
        this.source_Pub = source_Pub;
        this.categorie_Pub = categorie_Pub;
        this.image_Pub = image_Pub;
    }

    public Publication(Date date_Pub, String titre_Pub, String desc_Pub, String source_Pub, String categorie_Pub, String image_Pub) {
        this.date_Pub = date_Pub;
        this.titre_Pub = titre_Pub;
        this.desc_Pub = desc_Pub;
        this.source_Pub = source_Pub;
        this.categorie_Pub = categorie_Pub;
        this.image_Pub = image_Pub;
    }

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    public Date getDate_Pub() {
        return date_Pub;
    }

    public void setDate_Pub(Date date_Pub) {
        this.date_Pub = date_Pub;
    }

    public String getTitre_Pub() {
        return titre_Pub;
    }

    public void setTitre_Pub(String titre_Pub) {
        this.titre_Pub = titre_Pub;
    }

    public String getDesc_Pub() {
        return desc_Pub;
    }

    public void setDesc_Pub(String desc_Pub) {
        this.desc_Pub = desc_Pub;
    }

    public String getSource_Pub() {
        return source_Pub;
    }

    public void setSource_Pub(String source_Pub) {
        this.source_Pub = source_Pub;
    }

    public String getCategorie_Pub() {
        return categorie_Pub;
    }

    public void setCategorie_Pub(String categorie_Pub) {
        this.categorie_Pub = categorie_Pub;
    }

    public String getImage_Pub() {
        return image_Pub;
    }

    public void setImage_Pub(String image_Pub) {
        this.image_Pub = image_Pub;
    }

    @Override
    public String toString() {
        return "Publication{" + "idPub=" + idPub + ", date_Pub=" + date_Pub + ", titre_Pub=" + titre_Pub + ", desc_Pub=" + desc_Pub + ", source_Pub=" + source_Pub + ", categorie_Pub=" + categorie_Pub + ", image_Pub=" + image_Pub + '}';
    }
    
    
}
